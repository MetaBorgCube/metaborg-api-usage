package org.metaborg.example.api.analysis;


import java.io.IOException;

import org.metaborg.core.MetaborgException;
import org.metaborg.example.api.analysis.frontend.Frontend;
import org.metaborg.example.api.analysis.syntax.Root;
import org.metaborg.spoofax.core.Spoofax;
import org.metaborg.util.log.ILogger;
import org.metaborg.util.log.LoggerUtils;

public class Main {

	public static void main(String[] args) {
		
		String languageResource = args[0];
		String filePath = args[1];
		
		try(Spoofax spoofax = new Spoofax()) {
		
			Frontend parser = new Frontend(spoofax, languageResource);
			// initialize everything by parsing/analysing a dummy expression
			Root root = parser.analyze("42");

			ILogger logger = LoggerUtils.logger(Main.class);
			
			logger.info("start parsing");
			root = parser.analyze("let x = 21 in x + let x = 7 in x * 3 end end");
			logger.info(root.toString());
			root = parser.analyzeFile(filePath);
			logger.info(root.toString());
			
		} catch (MetaborgException | IOException e) {
			e.printStackTrace();
		}
	}

}
