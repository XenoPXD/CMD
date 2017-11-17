package impl;

import javax.swing.text.BadLocationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import bean.Action;
import gest.JavascriptGest;

public class JavascriptMain {

	private static Options options = null;
	
	private static Options configParameters() {

	    final Option codeOption = Option.builder("code") 
	            .longOpt("code") 
	            .desc("Code evaluate")
	            .hasArg(true) 
	            .argName("code") 
	            .required(false) 
	            .numberOfArgs(1)
	            .build();

	    final Option fileOption = Option.builder("file") 
	            .longOpt("file") 
	            .desc("file")
	            .hasArg(true) 
	            .argName("file") 
	            .required(false) 
	            .numberOfArgs(Option.UNLIMITED_VALUES)
	            .build();
	    
	    final Options options = new Options();

	    options.addOption(codeOption);
	    options.addOption(fileOption);


	    return options;
	}
	
	public static void main(String[] args) throws BadLocationException, Exception {
		
		options = configParameters();

		CommandLineParser parser = new DefaultParser();
		final CommandLine line = parser.parse(options, args);
		executeAction(line);
	}
	
	private static void executeAction(final CommandLine line) throws Exception {
		
		if (line.hasOption("h")) {
			help();
		}
		
		// Valorisation de l'action
		Action action = new Action();
		
		if (line.hasOption("code")) {
			action.setValue(line.getOptionValue("code"));
		}
		
		if (line.hasOption("file")) {
			String[] values = line.getOptionValues("file");
			action.setFile(values);
		}

		
		// Action
		if (line.hasOption("file")) {
			JavascriptGest.file(action);
		} else if (line.hasOption("code")) {
			JavascriptGest.code(action);
		}
		
	}
	
	
	
	private static void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Eqx", options);
		System.exit(0);
	}
}
