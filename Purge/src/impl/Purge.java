package impl;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.BadLocationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import bean.Action;
import gest.PurgeGest;

public class Purge {

	public static String PATH_PURGE = "";
	private static List<Action> listeActions = new ArrayList<Action>();
	protected static Options options = null;
	
	private static Options configParameters() {

	    final Option subOption = Option.builder("s") 
	            .longOpt("sub") //
	            .desc("Sub directory") 
	            .hasArg(false) 
	            .argName("sub")
	            .required(false) 
	            .build();

	    final Option pathOption = Option.builder("p") 
	            .longOpt("path") 
	            .desc("Path") 
	            .hasArg(true) 
	            .argName("path") 
	            .required(true) 
	            .numberOfArgs(1)
	            .build();

	    final Option fileOption = Option.builder("f") 
	    		.longOpt("file") 
	            .desc("File pattern") 
	            .hasArg(true) 
	            .numberOfArgs(1)
	            .argName("file") 
	            .required(false) 
	            .build();

	    final Option clearOption = Option.builder("c") 
	            .longOpt("clear")
	            .desc("Clear") 
	            .hasArg(false) 
	            .argName("clear")
	            .required(false) 
	            .build();
	    
	    final Option helpOption = Option.builder("h") 
	            .longOpt("help")
	            .desc("Help") 
	            .hasArg(false) 
	            .argName("help")
	            .required(false) 
	            .build();
	    
	    final Options options = new Options();

	    options.addOption(helpOption);
	    options.addOption(subOption);
	    options.addOption(pathOption);
	    options.addOption(fileOption);
	    options.addOption(clearOption);

	    return options;
	}
	
	
	public static void main(String[] args) throws BadLocationException, Exception {
		
//		for (int i = 0; i < args.length; i++) {
//			System.out.println(args[i]);
//		}
		
		System.out.println("");
		
		options = configParameters();
		
		try {
		
			final CommandLineParser parser = new DefaultParser();
			final CommandLine line = parser.parse(options, args);
	
			if (line.hasOption("h")) {
				help();
			}
			
	        PATH_PURGE = new File(
					Purge.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())
							.getParent();
			//System.out.println("PATH_PURGE : " + PATH_PURGE);
			//JdomGest.load(PATH_PURGE + "\\purge.xml");
			
			Action action = new Action();
			
			if (line.hasOption("p")) {
				action.setPath(line.getOptionValue("p"));
			} else {
				help();
			}
	
			
			action.setFile("*");
			if (line.hasOption("f")) {
				action.setFile(line.getOptionValue("f"));
			}
			
			action.setSub(line.hasOption("s"));
			
			action.setClear(line.hasOption("c"));
			
			File pathfile = new File(action.getPath());
			PurgeGest.nbrFile=0;
			PurgeGest.supprimerFichiers(pathfile, action);
			
			if (PurgeGest.nbrFile>0) {
				System.out.println("");
				System.out.println("Purge total de " + Action.convertTailleToString(PurgeGest.tailleGlobale)+".");
			} else {
				System.out.println("Aucun fichier trouvé.");
			}
			
	//		System.out.println("---------------------------------------------------");
	//		System.out.println("-path " + action.getPath());
	//		System.out.println("-file " + action.getFile());
	//		System.out.println("-sub " + action.isSub());
	//		System.out.println("-clear " + action.isClear());
			
		} catch (ParseException e) {
			help();
		}
	}

	private static void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Purge", options);
		System.exit(0);
	}
	 
}
