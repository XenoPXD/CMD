package gest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

import javax.swing.text.BadLocationException;

import bean.Action;



public class PurgeGest {
	
	public static long tailleGlobale = 0;
	public static int nbrFile = 0;
	public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public static void supprimerFichiers(File inFile, final Action action) throws BadLocationException, Exception {
		if (inFile.exists()) {
			for (File file : inFile.listFiles()) {
				if (file.isDirectory()) {
					String nom = file.getName();
					Matcher matcher = action.getPattern().matcher(nom);
					if (matcher.find(0)) {
						deleteDirectory(file, action);
					}
					if (action.isSub()) {
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
	}

	private static void deleteDirectory(File racine, Action action) {
		for (File fils : racine.listFiles()) {
			if (fils.isDirectory()) {
				deleteDirectory(fils, action);
			} else {
				updateOrDelete(action, fils);
			}
		}
		if (action.isDel()) {
			System.out.println(getTailleString(racine.length()) + "Suppression du répertoire " + racine.getAbsolutePath());
			long taille = racine.length();
			racine.delete();
			tailleGlobale += taille;
		} else {
			System.out.println(getTailleString(racine.length()) + "Répertoire " + racine.getAbsolutePath());
		}
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
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(e);
				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
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


