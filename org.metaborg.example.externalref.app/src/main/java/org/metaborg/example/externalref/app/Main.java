package org.metaborg.example.externalref.app;


import java.io.IOException;

import org.metaborg.core.MetaborgException;
import org.metaborg.example.externalref.app.parser.Parser;
import org.metaborg.example.externalref.app.syntax.Root;
import org.metaborg.spoofax.core.Spoofax;
import org.metaborg.util.log.ILogger;
import org.metaborg.util.log.LoggerUtils;

public class Main {

	public static void main(String[] args) {
		
		String languageResource = args[0];
		
		try(Spoofax spoofax = new Spoofax()) {
		
			Parser parser = new Parser(spoofax, languageResource);
			// initialize everything by parsing/analysing a dummy expression
			Root root = parser.parse("42");

			ILogger logger = LoggerUtils.logger(Main.class);
			
			logger.info("start parsing");
			root = parser.parse("x + y + 42");
			logger.info(root.toString());
			root = parser.parse("z || \"42\"");
			logger.info(root.toString());
			
		} catch (MetaborgException | IOException e) {
			e.printStackTrace();
		}
	}

}
