package org.metaborg.example.api.analysis.parser;

import java.io.File;
import java.io.IOException;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;
import org.metaborg.core.MetaborgException;
import org.metaborg.core.analysis.AnalysisException;
import org.metaborg.core.context.IContext;
import org.metaborg.core.language.ILanguageImpl;
import org.metaborg.core.syntax.ParseException;
import org.metaborg.example.api.analysis.SpoofaxUtil;
import org.metaborg.example.api.analysis.syntax.Root;
import org.metaborg.spoofax.core.Spoofax;
import org.metaborg.spoofax.core.unit.ISpoofaxAnalyzeUnit;
import org.metaborg.spoofax.core.unit.ISpoofaxInputUnit;
import org.metaborg.spoofax.core.unit.ISpoofaxParseUnit;
import org.metaborg.util.concurrent.IClosableLock;

public class Parser {

	final private Spoofax spoofax;
	final private ILanguageImpl implementation;

	public Parser(Spoofax spoofax, String languageResource) throws MetaborgException {
		this.spoofax = spoofax;
		FileObject location = spoofax.resourceService.resolve(languageResource);
		this.implementation = spoofax.languageDiscoveryService.languageFromArchive(location);
	}

	public Root parse(String content) throws MetaborgException, IOException {
		
		FileObject file = VFS.getManager().toFileObject(File.createTempFile("temp", "example"));

		ISpoofaxInputUnit input = spoofax.unitService.inputUnit(file, content, implementation, null);
		ISpoofaxParseUnit parsed = spoofax.syntaxService.parse(input);
		if (!parsed.valid())
			throw new ParseException(input, "Could not parse");
		
		IContext context = SpoofaxUtil.getContext(spoofax, implementation, file);
		try(IClosableLock lock = context.write()) {
			ISpoofaxAnalyzeUnit analyzed = spoofax.analysisService.analyze(parsed, context).result();
			if (!analyzed.valid())
				throw new AnalysisException(context, "Could not analyse");
			return new Factory().createRoot(analyzed.ast());
		}	
	}
	
	public Root parseFile(String path)
			throws MetaborgException, IOException {
		FileObject file = spoofax.resourceService.resolve(path);
		String content = spoofax.sourceTextService.text(file);
		ISpoofaxInputUnit input = spoofax.unitService.inputUnit(file, content, implementation, null);
		ISpoofaxParseUnit parsed = spoofax.syntaxService.parse(input);
		if (!parsed.valid())
			throw new ParseException(input, "Could not parse");
		
		IContext context = SpoofaxUtil.getContext(spoofax, implementation, file);
		try(IClosableLock lock = context.write()) {
			ISpoofaxAnalyzeUnit analyzed = spoofax.analysisService.analyze(parsed, context).result();
			if (!analyzed.valid())
				throw new AnalysisException(context, "Could not analyse");
			return new Factory().createRoot(analyzed.ast());
		}
	}
}
