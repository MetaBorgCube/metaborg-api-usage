package org.metaborg.example.api.parsing.parser;

import java.io.IOException;

import org.apache.commons.vfs2.FileObject;
import org.metaborg.core.MetaborgException;
import org.metaborg.core.language.ILanguageImpl;
import org.metaborg.core.syntax.ParseException;
import org.metaborg.example.api.parsing.SpoofaxUtil;
import org.metaborg.example.api.parsing.syntax.Root;
import org.metaborg.spoofax.core.Spoofax;
import org.metaborg.spoofax.core.unit.ISpoofaxInputUnit;
import org.metaborg.spoofax.core.unit.ISpoofaxParseUnit;

public class Parser {

	final private Spoofax spoofax;
	final private ILanguageImpl implementation;

	public Parser(Spoofax spoofax, String languageResource) throws MetaborgException {
		this.spoofax = spoofax;
		this.implementation = SpoofaxUtil.getImplementation(spoofax, languageResource);
	}

	public Root parse(String content) throws MetaborgException {
		ISpoofaxInputUnit input = spoofax.unitService.inputUnit(null, content, implementation, null);
		ISpoofaxParseUnit parsed = spoofax.syntaxService.parse(input);
		if (!parsed.valid())
			throw new ParseException(input, "Could not parse");
		
		return new Factory().createRoot(parsed.ast());	
	}
	
	public Root parseFile(String path)
			throws MetaborgException, IOException {
		FileObject file = spoofax.resourceService.resolve(path);
		String content = spoofax.sourceTextService.text(file);
		ISpoofaxInputUnit input = spoofax.unitService.inputUnit(file, content, implementation, null);
		ISpoofaxParseUnit parsed = spoofax.syntaxService.parse(input);
		if (!parsed.valid())
			throw new ParseException(input, "Could not parse");
		
		return new Factory().createRoot(parsed.ast());
	}
}
