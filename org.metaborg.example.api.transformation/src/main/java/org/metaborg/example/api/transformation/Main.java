package org.metaborg.example.api.transformation;


import java.io.IOException;

import org.metaborg.core.MetaborgException;
import org.metaborg.spoofax.core.Spoofax;
import org.metaborg.util.log.ILogger;
import org.metaborg.util.log.LoggerUtils;

public class Main {

	public static void main(String[] args) {
		
		String languageResource = args[0];
		String filePath = args[1];
		
		try(Spoofax spoofax = new Spoofax()) {
		
			Builder builder = new Builder(spoofax, languageResource);
			
			ILogger logger = LoggerUtils.logger(Main.class);
			
			logger.info("start file evaluation");
			logger.info(Integer.toString(builder.evaluateFile(filePath)));
			
			logger.info("start project evaluation");
			logger.info(builder.evaluateProject(filePath));
			
			logger.info("start string evaluation");
			logger.info(Integer.toString(builder.evaluate("let x = 21 in x + let x = 7 in x * 3 end end")));
		} catch (MetaborgException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
