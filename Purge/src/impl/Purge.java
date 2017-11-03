package impl;
import java.io.File;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
	protected static long startTime = System.currentTimeMillis();
	public static DateFormat dateFormat = DateFormat.getDateTimeInstance();//("HH':'mm':'ss'.'SSS");
	
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
	    
	    final Option methodeOption = Option.builder("m") 
	    		.longOpt("methode") 
	            .desc("Methode j7 aIO") 
	            .hasArg(true) 
	            .numberOfArgs(1)
	            .argName("methode") 
	            .required(false) 
	            .build();

	    final Option delOption = Option.builder("d") 
	            .longOpt("del")
	            .desc("Del") 
	            .hasArg(false) 
	            .argName("del")
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
	    
	    final Option verboseOption = Option.builder("v") 
	            .longOpt("verbose")
	            .desc("Verbose") 
	            .hasArg(false) 
	            .argName("verbose")
	            .required(false) 
	            .build();
	    
	    final Options options = new Options();

	    options.addOption(helpOption);
	    options.addOption(subOption);
	    options.addOption(pathOption);
	    options.addOption(fileOption);
	    options.addOption(methodeOption);
	    options.addOption(clearOption);
	    options.addOption(delOption);
	    options.addOption(verboseOption);

	    return options;
	}
	
	
	public static void main(String[] args) throws BadLocationException, Exception {
		
		
		System.out.println("");
		
		options = configParameters();
		
		try {
		
			final CommandLineParser parser = new DefaultParser();
			final CommandLine line = parser.parse(options, args);
	
			if (line.hasOption("h")) {
				help();
			}
			
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
			action.setDel(line.hasOption("d"));
			action.setClear(line.hasOption("c"));
			
			File pathfile = new File(action.getPath());
			PurgeGest.nbrFile=0;
			PurgeGest.supprimerFichiers(pathfile, action);
			
			if (PurgeGest.nbrFile>0) {
				System.out.println("");
				System.out.println("Taille total " + Action.convertTailleToString(PurgeGest.tailleGlobale));
			} else {
				System.out.println("Aucun fichier trouv�.");
			}
			
		} catch (ParseException e) {
			help();
		} finally {
		    System.out.println("Temps d'ex�cution " + getTimeString(System.currentTimeMillis()-startTime));
		}
	}

	private static void printArgs(String[] args) {
		System.out.print("Purge");
		for (int i = 0; i < args.length; i++) {
			System.out.print(" "+args[i]);
		}
		System.out.println();
		System.out.println();
	}

	private static void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Purge", options);
		System.exit(0);
	}
	 
	private static String getTimeString(final long millis) {
		String hms = String.format("%02d:%02d:%02d.%03d", TimeUnit.MILLISECONDS.toHours(millis),
			    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
			    TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1),
			    TimeUnit.MILLISECONDS.toMillis(millis) % TimeUnit.SECONDS.toMillis(1));
		return hms;
	}
}
