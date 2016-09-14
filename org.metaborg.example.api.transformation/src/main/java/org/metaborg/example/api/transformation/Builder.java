package org.metaborg.example.api.transformation;

import java.io.IOException;

import org.apache.commons.vfs2.FileObject;
import org.metaborg.core.MetaborgException;
import org.metaborg.core.action.EndNamedGoal;
import org.metaborg.core.build.BuildInput;
import org.metaborg.core.build.BuildInputBuilder;
import org.metaborg.core.build.IBuildOutput;
import org.metaborg.core.language.ILanguageImpl;
import org.metaborg.core.processing.CancellationToken;
import org.metaborg.core.processing.NullProgressReporter;
import org.metaborg.core.project.IProject;
import org.metaborg.core.transform.TransformException;
import org.metaborg.spoofax.core.Spoofax;
import org.metaborg.spoofax.core.resource.SpoofaxIgnoresSelector;
import org.metaborg.spoofax.core.unit.ISpoofaxAnalyzeUnit;
import org.metaborg.spoofax.core.unit.ISpoofaxAnalyzeUnitUpdate;
import org.metaborg.spoofax.core.unit.ISpoofaxParseUnit;
import org.metaborg.spoofax.core.unit.ISpoofaxTransformUnit;
import org.spoofax.interpreter.terms.IStrategoInt;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class Builder {

	final private Spoofax spoofax;
	final private ILanguageImpl implementation;

	public Builder(Spoofax spoofax, String languageResource) throws MetaborgException {
		this.spoofax = spoofax;
		this.implementation = SpoofaxUtil.getImplementation(spoofax, languageResource);
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
		
		IBuildOutput<ISpoofaxParseUnit, ISpoofaxAnalyzeUnit, ISpoofaxAnalyzeUnitUpdate, ISpoofaxTransformUnit<?>> output = spoofax.builder
				.build(input, new NullProgressReporter(), new CancellationToken());

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
		
		IBuildOutput<ISpoofaxParseUnit, ISpoofaxAnalyzeUnit, ISpoofaxAnalyzeUnitUpdate, ISpoofaxTransformUnit<?>> output = spoofax.builder
				.build(input, new NullProgressReporter(), new CancellationToken());

		if (!output.success())
			throw new TransformException("Coul not evaluate");

		IStrategoTerm result = output.transformResults().iterator().next().ast();
		
		if (result instanceof IStrategoInt)
			return ((IStrategoInt) result).intValue();
		else
			throw new TransformException("Result is not an integer");
	}
}
