package gest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;

import javax.swing.text.BadLocationException;

import org.apache.commons.io.FileUtils;

import bean.Action;



public class PurgeGest {
	
	public static long tailleGlobale = 0;
	public static int nbrFile = 0;
	public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public static void supprimerFichiers(File inFile, final Action action) throws BadLocationException, Exception {
		
		
		try {
			if (inFile.exists() ) {
				for (File file : /*FileUtils.listFiles(inFile, TrueFileFilter.INSTANCE,  DirectoryFileFilter.INSTANCE) */ inFile.listFiles() ) {
					if (file.isDirectory()) {
						String nom = file.getName();
						Matcher matcher = action.getPattern().matcher(nom);
						if (matcher.find(0)) {
							if (action.isDel()) {
								long size = getFolderSize(file);
								FileUtils.deleteDirectory(file);
								nbrFile++;
								tailleGlobale += size;
								System.out.println(getTailleString(size) + "Suppression du Répertoire " + file.getAbsolutePath());
							} else if (action.isClear()) {	
								long size = getFolderSize(file);
								FileUtils.cleanDirectory(file);
								nbrFile++;
								tailleGlobale += size;
								System.out.println(getTailleString(size) + "Nettoyage du Répertoire " + file.getAbsolutePath());
							} else {
							    nbrFile++;
							    long size = getFolderSize(file);
							    tailleGlobale += size;
							    System.out.println(getTailleString(size) + "Répertoire " + file.getAbsolutePath());
							}
						} else if (action.isSub()) {
							supprimerFichiers(file, action);
						}
					} else {
						String nom = file.getName();
						Matcher matcher = action.getPattern().matcher(nom);
						if (matcher.find(0)) {
							updateOrDelete(action, file);
						}
					}
				}
			}
		} catch (Exception e) {
			
			if (inFile.isDirectory()) {
				System.out.print("Répertoire " + inFile.getAbsolutePath());
			} else {
				System.out.print("Fichier " + inFile.getAbsolutePath());
			}
			
			Path filePath = Paths.get(inFile.getAbsolutePath());
			if (!Files.isWritable(filePath)) {
				System.out.println(" (Accès refusé)");
			} else {
				System.out.println(e.toString());
			}
		}
	}

	/**
	 * // http://www.baeldung.com/java-folder-size
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static long getFolderSize(final File file) throws IOException {
		AtomicLong size = new AtomicLong(0);
	    Path folder = Paths.get(file.getAbsolutePath());
	 
	    Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) 
	          throws IOException {
	            size.addAndGet(attrs.size());
	            return FileVisitResult.CONTINUE;
	        }
	    });
	    return size.longValue();
	}
	
	
	private static void updateOrDelete(final Action action, File file) {
		
		long taille = file.length();
		if (action.isDel()) {
			System.out.println(getTailleString(file.length()) + "Suppression du fichier " + file.getAbsolutePath());
			file.delete();
			nbrFile++;
			tailleGlobale += taille;
		} else if (action.isClear()) {
			System.out.println(getTailleString(file.length()) + "Nettoyage du fichier " + file.getAbsolutePath());
				FileOutputStream fos;
				try {
					fos = new FileOutputStream( file );
					fos.close( );
					nbrFile++;
					tailleGlobale += taille;
				} catch (FileNotFoundException e) {
					System.out.println(e);
				} catch (IOException e) {
					System.out.println(e);
				}
				nbrFile++;
		} else {
			nbrFile++;
			System.out.println(getTailleString(file.length()) + "Fichier " + file.getAbsolutePath());
			tailleGlobale += taille;
		}
	}

	private static String getTailleString(long taille) {
		String strTaille = String.format("%10s", Action.convertTailleToString(taille)) + " " ;
		return strTaille;
	}
	
	private static String getDate() {
		return dateFormat.format(new Date()) + " ";
	}
	
}


