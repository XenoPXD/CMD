package impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.BadLocationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import bean.Action;
import gest.XmlGest;

public class FileMain {

	private static Options options = null;
	private static long startTime = System.currentTimeMillis();
	private static String PATH_PURGE;
	private static final String dir = System.getProperty("user.dir");
	private static List<String> listeLines = new ArrayList<String>();
	
	private static Options configParameters() {

	    final Option subOption = Option.builder("s") 
	            .longOpt("sub")
	            .desc("Sub directory") 
	            .hasArg(false) 
	            .argName("sub")
	            .required(false) 
	            .build();

	    final Option xpathOption = Option.builder("xpath") 
	            .longOpt("xpath") 
	            .desc("Xpath") 
	            .hasArg(true) 
	            .argName("xpath") 
	            .required(false) 
	            .numberOfArgs(1)
	            .build();

	    final Option fileOption = Option.builder("file") 
	    		.longOpt("file") 
	            .desc("File pattern") 
	            .hasArg(true) 
	            .numberOfArgs(1)
	            .argName("file") 
	            .required(false) 
	            .build();
	    
	    final Option valueOption = Option.builder("v") 
	    		.longOpt("value") 
	            .desc("Value") 
	            .hasArg(true) 
	            .numberOfArgs(1)
	            .argName("value") 
	            .required(false) 
	            .build();
	    
	    final Option infoOption = Option.builder("info") 
	            .longOpt("info")
	            .desc("Info") 
	            .hasArg(false) 
	            .argName("info")
	            .required(false) 
	            .build();
	    
	    final Option helpOption = Option.builder("h") 
	            .longOpt("help")
	            .desc("Help") 
	            .hasArg(false) 
	            .argName("help")
	            .required(false) 
	            .build();
	    
	    final Option choiceOption = Option.builder("c") 
	            .longOpt("choice")
	            .desc("Choice Workspace") 
	            .hasArg(false) 
	            .argName("choice")
	            .required(false) 
	            .build();
	    
	    final Option agrsFileOption = Option.builder("af") 
	            .longOpt("argsFile") 
	            .desc("Arguments in file") 
	            .hasArg(true) 
	            .argName("argsFile") 
	            .required(false) 
	            .numberOfArgs(1)
	            .build();
	    
	    final Option createOption = Option.builder("create") 
	            .longOpt("create") 
	            .desc("Create") 
	            .hasArg(true) 
	            .argName("create") 
	            .required(false) 
	            .numberOfArgs(2)
	            .build();
	    
	    final Option readOption = Option.builder("read") 
	            .longOpt("read")
	            .desc("Read") 
	            .hasArg(false) 
	            .argName("read")
	            .required(false) 
	            .build();
	    
	    final Option updateOption = Option.builder("update") 
	            .longOpt("update") 
	            .desc("Update") 
	            .hasArg(true) 
	            .argName("update") 
	            .required(false) 
	            .numberOfArgs(1)
	            .build();
	    
	    final Option deleteOption = Option.builder("delete") 
	            .longOpt("delete")
	            .desc("Delete") 
	            .hasArg(false) 
	            .argName("delete")
	            .required(false) 
	            .build();
	    
	    final Options options = new Options();

	    options.addOption(helpOption);
	    options.addOption(subOption);
	    options.addOption(xpathOption);
	    options.addOption(fileOption);
	    options.addOption(infoOption);
	    options.addOption(valueOption);
	    options.addOption(agrsFileOption);
	    options.addOption(choiceOption);
	    
	    options.addOption(createOption);
	    options.addOption(readOption);
	    options.addOption(updateOption);
	    options.addOption(deleteOption);

	    return options;
	}
	
	public static void main(String[] args) throws BadLocationException, Exception {
		
		System.out.println("");
		
		PATH_PURGE = new File(FileMain.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
		
		options = configParameters();

		CommandLineParser parser = new DefaultParser();
		final CommandLine line = parser.parse(options, args);
		executeAction(line);
	}
	
	private static void executeAction(final CommandLine line) throws BadLocationException, Exception {
		
		if (line.hasOption("h")) {
			help();
		}
		
		// Valorisation de l'action
		Action action = new Action();
		
		if (line.hasOption("xpath")) {
			action.setXpath(line.getOptionValue("xpath"));
		}

		
		if (line.hasOption("file")) {
			action.setFile(line.getOptionValue("file"));
		}
		action.setCreate(line.hasOption("create"));
		if (line.hasOption("create")) {
			String[] values = line.getOptionValues("create");
			action.setElement(values[0]);
			action.setValue(values[1]);
//			System.out.println("elem : " + action.getElement());
//			System.out.println("value : " + action.getValue());
//			System.out.println("attr : " + action.getAttrName());
		}
		
		action.setRead(line.hasOption("read"));
		action.setUpdate(line.hasOption("update"));
		if (line.hasOption("update")) {
			action.setValue(line.getOptionValue("update"));
//			System.out.println("value : " + action.getValue());
		}
		action.setDelete(line.hasOption("delete"));
		action.setCrud(line.getOptionValue("crud"));
//		action.setElement(line.getOptionValue("elem"));
		
		action.setInfo(line.hasOption("info"));
		
		if (line.hasOption("af")) {
			
			if (line.hasOption("info")) {
				if (line.hasOption("choice")) {
					choiceListFiles(line);
				} else {
					searchInfoListFiles(line);
				}
			}
			
		}
		
		
		// Action
		
		if (line.hasOption("xpath")) {
			XmlGest.crud(new File(action.getFile()) , action);
		}
		
	}

	
	private static void choiceListFiles(final CommandLine line) throws BadLocationException, Exception {
		CommandLineParser parserTmp = new DefaultParser();
		try (BufferedReader br = new BufferedReader(new FileReader(line.getOptionValue("af")))) {

			String sCurrentLine;
			int i = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				if (!"".equals(sCurrentLine)) {
					i++;
					System.out.println("[" + i + "] " + sCurrentLine);
					listeLines.add(sCurrentLine);
				}
			}
			System.out.println("");
			System.out.print("Votre choix ? ");
			int num = choice();
			
			System.out.println("");
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	
	private static String getLineFile(int numLine) throws BadLocationException, Exception {
		String out = "";
		int i = 0;
		for (String string : listeLines) {
			i++;
			if (numLine == i) {
				out = string;
				break;
			}
		}
		
		return out;
		
	}
	
	
	private static int choice() throws BadLocationException, Exception {
		
		Scanner in = new Scanner(System.in);
		int num = in.nextInt();
		
		return num;
		
	}
	
	private static void searchInfoListFiles(final CommandLine line) throws BadLocationException, Exception {
		CommandLineParser parserTmp = new DefaultParser();
		try (BufferedReader br = new BufferedReader(new FileReader(line.getOptionValue("af")))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				if (!"".equals(sCurrentLine)) {
					String pf = sCurrentLine + File.separator + "PRJ_EAR_CONFIG" + File.separator
							+ "config.env.properties";
					File file = new File(pf);
					if (file.exists()) {

					}
				}
			}

		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	
	private static void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Eqx", options);
		System.exit(0);
	}
}
