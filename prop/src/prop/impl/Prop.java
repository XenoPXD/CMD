package prop.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.BadLocationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import prop.bean.Action;
import prop.gest.PropertiesGest;

public class Prop {

	private static Options options = null;
	private static long startTime = System.currentTimeMillis();
	private static String PATH_PURGE;
	private static final String dir = System.getProperty("user.dir");
	private static List<String> listeLines = new ArrayList<String>();
	
	private static Options configParameters() {

	    final Option fileOption = Option.builder("f") 
	    		.longOpt("file") 
	            .desc("File") 
	            .hasArg(true) 
	            .argName("file") 
	            .required(true) 
	            .build();
	    
	    final Option helpOption = Option.builder("h") 
	            .longOpt("help")
	            .desc("Help") 
	            .hasArg(false) 
	            .argName("help")
	            .required(false) 
	            .build();
	    
	    final Option createOption = Option.builder("s") 
	            .longOpt("set") 
	            .desc("Set") 
	            .hasArg(true) 
	            .argName("set") 
	            .required(false) 
	            .numberOfArgs(Option.UNLIMITED_VALUES)
	            .build();
	    
	    final Option readOption = Option.builder("r") 
	            .longOpt("read")
	            .desc("Read value of properties file.") 
	            .hasArg(false) 
	            .argName("read")
	            .required(false) 
	            .numberOfArgs(1)
	            .build();
	    
	    final Option deleteOption = Option.builder("d") 
	            .longOpt("delete")
	            .desc("Delete") 
	            .hasArg(false) 
	            .argName("delete")
	            .required(false) 
	            .numberOfArgs(Option.UNLIMITED_VALUES)
	            .build();
	    
	    final Options options = new Options();

	    options.addOption(helpOption);
	    options.addOption(fileOption);
	    options.addOption(readOption);
	    options.addOption(deleteOption);
	    options.addOption(createOption);
	    
	    return options;
	}
	
	public static void main(String[] args) throws BadLocationException, Exception {
		
		PATH_PURGE = new File(Prop.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
		CommandLine line = null;
		try {
			options = configParameters();
	
			CommandLineParser parser = new DefaultParser();
			line = parser.parse(options, args);
		} catch (Exception e) {
			e.printStackTrace();
			help();
		}
		
		executeAction(line);
		
	}
	
	private static void executeAction(final CommandLine line) throws BadLocationException, Exception {
		
		if (line.hasOption("h")) {
			help();
		}
		
		// Valorisation de l'action
		Action action = new Action();
		
		if (line.hasOption("key")) {
			action.setKey(line.getOptionValue("key"));
		}
		
		if (line.hasOption("comment")) {
			action.setComment(line.getOptionValue("comment"));
		}
		
		if (line.hasOption("file")) {
			action.setFile(line.getOptionValue("file"));
		}
		
		action.setSet(line.hasOption("set"));
		if (line.hasOption("set")) {
			String[] values = line.getOptionValues("set");
			action.setKey(values[0]);
			action.setValue(values[1]);
			if (values[2] != null) {
				action.setComment(values[2]);
			}
		}
		
		if (line.hasOption("read")) {
			action.setRead(line.hasOption("read"));
			String[] values = line.getOptionValues("read");
			action.setKey(values[0]);
		}
		
		if (line.hasOption("delete")) {
			action.setDelete(line.hasOption("delete"));
			String[] values = line.getOptionValues("delete");
			if (values != null) {
				if (values.length >= 1) {
					action.setKey(values[0]);
				} else {
					
				}
				if (values.length >= 2) {
					action.setComment(values[1]);
				}
			}
		}
		
		// Action
		PropertiesGest.crud(new File(action.getFile()) , action);
		
	}

	
	private static void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Prop", options);
		System.exit(0);
	}
}
