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
	public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // new SimpleDateFormat("dd-MM-yy HH:mm:ss");
	
	public static void supprimerFichiers(File inFile, final Action action) throws BadLocationException, Exception {
		if (inFile.exists()) {
			for (File file : inFile.listFiles()) {
				if (file.isDirectory()) {
					String nom = file.getName();
					Matcher matcher = action.getPattern().matcher(nom);
					if (matcher.find(0)) {
						deleteDirectory(file, action);
					}
					if (action.isSub()/*inSousRep*/) {
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
		System.out.println(getTailleString(racine) + "Suppression du répertoire " + racine.getAbsolutePath());
		racine.delete();
	}
	
	private static void updateOrDelete(final Action action, File file) {
		
		if (!action.isClear()) {
			long taille = file.length();
//			long inTaille =+ taille;
			System.out.println(getTailleString(file) + "Suppression du fichier " + file.getAbsolutePath());
			file.delete();
			nbrFile++;
		} else if ( true/* FILE.equals(action.getClear()) */ /* inAction.getClearFichier() */) {
			System.out.println(getTailleString(file) + "Nettoyage du fichier " + file.getAbsolutePath());
				FileOutputStream fos;
				try {
					fos = new FileOutputStream( file );
					fos.close( );
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nbrFile++;
		}
	}

	private static String getTailleString(File file) {
		long taille = file.length();
		tailleGlobale += taille;
		String strTaille = String.format("%10s", Action.convertTailleToString(taille)) + " " ;
		return strTaille;
	}
	
	private static String getDate() {
		return dateFormat.format(new Date()) + " ";
	}
	
}


