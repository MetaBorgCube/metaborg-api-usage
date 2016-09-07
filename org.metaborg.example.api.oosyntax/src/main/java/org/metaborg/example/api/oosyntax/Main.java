package org.metaborg.example.api.oosyntax;


import java.io.IOException;

import org.metaborg.core.MetaborgException;
import org.metaborg.example.api.oosyntax.parser.Parser;
import org.metaborg.example.api.oosyntax.syntax.Root;
import org.metaborg.spoofax.core.Spoofax;
import org.metaborg.util.log.ILogger;
import org.metaborg.util.log.LoggerUtils;

public class Main {

	public static void main(String[] args) {
		
		String languageResource = args[0];
		String filePath = args[1];
		
		try(Spoofax spoofax = new Spoofax()) {
		
			Parser parser = new Parser(spoofax, languageResource);
			// initialize everything by parsing/analysing a dummy expression
			Root root = parser.parse("42");

			ILogger logger = LoggerUtils.logger(Main.class);
			
			logger.info("start parsing");
			root = parser.parse("let x = 21 in x + let x = 7 in x * 3 end end");
			logger.info(root.toString());
			root = parser.parseFile(filePath);
			logger.info(root.toString());
			
		} catch (MetaborgException | IOException e) {
			e.printStackTrace();
		}
	}

}
