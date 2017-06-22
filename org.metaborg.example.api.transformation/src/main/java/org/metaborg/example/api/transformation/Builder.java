package org.metaborg.example.api.transformation;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;
import org.metaborg.core.MetaborgException;
import org.metaborg.core.action.EndNamedGoal;
import org.metaborg.core.analysis.AnalysisException;
import org.metaborg.core.build.BuildInput;
import org.metaborg.core.build.BuildInputBuilder;
import org.metaborg.core.context.IContext;
import org.metaborg.core.language.ILanguageImpl;
import org.metaborg.core.project.IProject;
import org.metaborg.core.syntax.ParseException;
import org.metaborg.core.transform.TransformException;
import org.metaborg.spoofax.core.Spoofax;
import org.metaborg.spoofax.core.build.ISpoofaxBuildOutput;
import org.metaborg.spoofax.core.resource.SpoofaxIgnoresSelector;
import org.metaborg.spoofax.core.unit.ISpoofaxAnalyzeUnit;
import org.metaborg.spoofax.core.unit.ISpoofaxInputUnit;
import org.metaborg.spoofax.core.unit.ISpoofaxParseUnit;
import org.metaborg.spoofax.core.unit.ISpoofaxTransformUnit;
import org.metaborg.util.concurrent.IClosableLock;
import org.spoofax.interpreter.terms.IStrategoInt;
import org.spoofax.interpreter.terms.IStrategoTerm;

import com.google.common.io.Files;

public class Builder {

	final private Spoofax spoofax;
	final private ILanguageImpl implementation;

	public Builder(Spoofax spoofax, String languageResource) throws MetaborgException {
		this.spoofax = spoofax;
		FileObject location = spoofax.resourceService.resolve(languageResource);
		this.implementation = spoofax.languageDiscoveryService.languageFromArchive(location);
	}

	public String evaluateProject(String path) throws MetaborgException, IOException, InterruptedException {

		FileObject file = spoofax.resourceService.resolve(path).getParent();
		IProject project = SpoofaxUtil.getProject(spoofax, file);

		BuildInput input = new BuildInputBuilder(project)
				.withDefaultIncludePaths(true)
				.withSourcesFromDefaultSourceLocations(true)
				.withSelector(new SpoofaxIgnoresSelector())
				.addLanguage(implementation)
				.addTransformGoal(new EndNamedGoal("Evaluate"))
				.build(spoofax.dependencyService, spoofax.languagePathService);
		
		ISpoofaxBuildOutput output = spoofax.builder.build(input);

		if (!output.success())
			throw new TransformException("Coul not evaluate");

		String results = "";
		for (ISpoofaxTransformUnit<?> transform : output.transformResults()) {
			IStrategoTerm result = transform.ast();
			if (result instanceof IStrategoInt)
				results = results + ((IStrategoInt) result).intValue() + " ";
			else
				throw new TransformException("Result is not an integer");
		}
		
		return results;
	}

	public int evaluateFile(String path) throws MetaborgException, IOException, InterruptedException {

		FileObject file = spoofax.resourceService.resolve(path);
		IProject project = SpoofaxUtil.getProject(spoofax, file.getParent());

		BuildInput input = new BuildInputBuilder(project)
				.withSelector(new SpoofaxIgnoresSelector())
				.addLanguage(implementation)
				.addSource(file)
				.addTransformGoal(new EndNamedGoal("Evaluate"))
				.build(spoofax.dependencyService, spoofax.languagePathService);

		ISpoofaxBuildOutput output = spoofax.builder.build(input);

		if (!output.success())
			throw new TransformException("Coul not evaluate");

		IStrategoTerm result = output.transformResults().iterator().next().ast();
		
		if (result instanceof IStrategoInt)
			return ((IStrategoInt) result).intValue();
		else
			throw new TransformException("Result is not an integer");
	}
	
	public int evaluate(String content) throws MetaborgException, IOException, InterruptedException {
		FileObject file = VFS.getManager().toFileObject(File.createTempFile("temp", "example", Files.createTempDir()));
		ISpoofaxInputUnit input = spoofax.unitService.inputUnit(file, content, implementation, null);
		ISpoofaxParseUnit parsed = spoofax.syntaxService.parse(input);
		if (!parsed.valid())
			throw new ParseException(input, "Could not parse");

		IContext context = SpoofaxUtil.getContext(spoofax, implementation, file.getParent());
		ISpoofaxAnalyzeUnit analyzed;
		try (IClosableLock lock = context.write()) {
			analyzed = spoofax.analysisService.analyze(parsed, context).result();
			if (!analyzed.valid())
				throw new AnalysisException(context, "Could not analyse");
		}
		Collection<ISpoofaxTransformUnit<ISpoofaxAnalyzeUnit>> transformUnits;
		try(IClosableLock lock = context.read()) {
			transformUnits = spoofax.transformService.transform(analyzed, context, new EndNamedGoal("Evaluate"));
		}

		IStrategoTerm result = transformUnits.iterator().next().ast();
		
		if (result instanceof IStrategoInt)
			return ((IStrategoInt) result).intValue();
		else
			throw new TransformException("Result is not an integer");
	}
}
