package impl;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.sun.xml.internal.ws.util.StringUtils;

import bean.Action;

// http://cynober.developpez.com/tutoriel/java/xml/jdom/#LI-C

public class JdomGest {
	
	static Element racine = new Element("racine");
	static org.jdom2.Document document = new Document(racine);
//
//	   public static void load(String inStrFile) {
//		   
//		  File file = new File (inStrFile);
//		   
//	      SAXBuilder sxb = new SAXBuilder();
//	      try {
//	         document = sxb.build(file);
//	      }
//	      catch(Exception e){}
//	      racine = document.getRootElement();
//	   }
//	   
//	   public static void affiche()
//	   {
//		   try
//		   {
//		      //On utilise ici un affichage classique avec getPrettyFormat()
//		      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
//		      sortie.output(document, System.out);
//		   }
//		   catch (java.io.IOException e){}
//		}
//	   
//	   
//		public static void affiche2() {
//	
//			List<Element> listePurge = racine.getChildren("purge");
//	
//			Iterator<Element> itListePurge = listePurge.iterator();
//			while (itListePurge.hasNext()) {
//				Element courant = itListePurge.next();
//				
//				System.out.println("");
//				System.out.println("id : " + getEspaces("id", 30) + courant.getAttributeValue("id") );
//				System.out.println("nom : " + getEspaces("nom", 30) + courant.getAttributeValue("name") );
//				
//				List<Element> listeElements = courant.getChildren();
//				Iterator<Element> itListeElements = listeElements.iterator();
//				while (itListeElements.hasNext()) {
//					Element element = itListeElements.next();
//					System.out.println(element.getAttributeValue("name") + " : " + getEspaces(element.getAttributeValue("name"), 30) + element.getValue() );
//				}
//			}
//		}
//		
//		
//		public static List<Action> recupereListeActions() {
//			
//			List<Action> listeActions = new ArrayList<Action>();
//			List<Element> listePurge = racine.getChildren("purge");
//			Iterator<Element> itListePurge = listePurge.iterator();
//			while (itListePurge.hasNext()) {
//				Element courant = (Element) itListePurge.next();
//				Action action = new Action(courant.getAttributeValue("id"));
//				action.setNom(courant.getAttributeValue("name"));
//				Boolean actif = ("true".equals(courant.getChild("actif").getText())) ? true : false ;
//				action.setActif(actif);
//				action.setRep(courant.getChild("rep").getText());
//				action.setFichier(courant.getChild("fichier").getText());
//				Boolean sousRep = ("true".equals(courant.getChild("sousRep").getText())) ? true : false ;
//				action.setSousRep(sousRep);
//				Boolean delSousRep = ("true".equals(courant.getChild("delSousRep").getText())) ? true : false ;
//				action.setDelSousRep(delSousRep);
//				Boolean delRep = ("true".equals(courant.getChild("delRep").getText())) ? true : false ;
//				action.setDelRep(delRep);
//				Boolean delFichier = ("true".equals(courant.getChild("delFichier").getText())) ? true : false ;
//				action.setDelFichier(delFichier);
//				Boolean clearFichier = ("true".equals(courant.getChild("clearFichier").getText())) ? true : false ;
//				action.setClearFichier(clearFichier);
//				listeActions.add(action);
//			}
//			return listeActions;
//		}
//		
//		
//		public static void recupereListeActions(Critère critère) {
//			
//			List<Element> listePurge = racine.getChildren("purge");
//	
//			Iterator<Element> itListePurge = listePurge.iterator();
//			while (itListePurge.hasNext()) {
//				Element courant = itListePurge.next();
//				
//				System.out.println("");
//				System.out.println("- " + courant.getAttributeValue("name"));
//				
//				List<Element> listeElements = courant.getChildren();
//				Iterator<Element> itListeElements = listeElements.iterator();
//				while (itListeElements.hasNext()) {
//					Element element = itListeElements.next();
//					if (critère.getValue().equals(element.getAttributeValue("name"))
//							|| critère.getValue().equals(element.getName())) {
//						System.out.println(element.getAttributeValue("name") + " : " + getEspaces(element.getAttributeValue("name"), 30) + element.getValue() );
//					}
//				}
//			}
//		}
//		
//		public static String getEspaces(String value, int nbr) {
//			String out = "";
//			int i = nbr; // - 4;
//			if (i<0) i = 0;
//			if (value != null) {
//				i = nbr - value.length();
//			}
//			for (int j = 0; j < i; j++) {
//				out +=" ";
//			}
//			return out;
//		}
//		
////		public static void enregistrerListeActions(String strFile, List<Action> inModeleAction) {
////			try {
////				XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
////				xmlOutput.output(creerDocument(inModeleAction), new FileOutputStream(strFile));
////			} catch (java.io.IOException e) {
////			}
////		}
////		
//		/**
//		 * affiche
//		 * Affiche la liste des elements actions
//		 * @param inListeActions
//		 */
//		public static void affiche(List<Action> inListeActions) {
//			for (Action action : inListeActions) {
//				System.out.println("");
//				System.out.println("id : " + action.getId());
//				System.out.println("Nom : " + action.getNom());
//				System.out.println("Activation : " + action.getActif());
//				System.out.println("Recherche répertoire : " + action.getRep());
//				System.out.println("Rechercher sous-répertoire : " + action.getSousRep());
//				System.out.println("Supprimer répertoire : " + action.getDelRep());
//				System.out.println("Supprimer sous-répertoire : " + action.getDelSousRep());
//				System.out.println("Supprimer fichier : " + action.getDelFichier());
//				System.out.println("Vider fichier : " + action.getClearFichier());
//			}
//		}
//		
//		/**
//		 * affiche
//		 * Affiche la liste des elements actions
//		 * @param inListeActions
//		 */
//		public static void afficheTableau(List<Action> inListeActions) {
//			String l = "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
//			System.out.println("");
//			System.out.print("Id");
//			System.out.print(getEspaces("id", 5) + "Nom");
//			System.out.print(getEspaces("Nom", 60) + "Activ.");
//			System.out.print(getEspaces("Activ.", 7) + "Recherche");
//			System.out.print(getEspaces("Recherche", 90) + "Rechercher");
//			System.out.print(getEspaces("Rechercher", 22) + "Rechercher");
//			System.out.print(getEspaces("Rechercher", 16) + "Supprimer");
//			System.out.print(getEspaces("Supprimer", 16) + "Supprimer");
//			System.out.print(getEspaces("Supprimer", 16) + "Supprimer");
//			System.out.println(getEspaces("Supprimer", 16) + "Vider");
//			System.out.print("  ");
//			System.out.print(getEspaces("id", 5) + "   ");
//			System.out.print(getEspaces("Nom", 60) + "      ");
//			System.out.print(getEspaces("Activ.", 7) + "répertoire");
//			System.out.print(getEspaces("répertoire", 90) + "fichier");
//			System.out.print(getEspaces("fichier", 22) + "sous-répertoire");
//			System.out.print(getEspaces("sous-répertoire", 16) + "répertoire");
//			System.out.print(getEspaces("répertoire", 16) + "sous-répertoire");
//			System.out.print(getEspaces("sous-répertoire", 16) + "fichier");
//			System.out.print(getEspaces("fichier", 16) + "fichier");
//			System.out.println("");
//			for (Action action : inListeActions) {
//				System.out.println(l);
//				System.out.print(action.getId());
//				System.out.print(getEspaces("id", 5)  + action.getNom());
//				System.out.print(getEspaces(action.getNom(), 60) + action.getActif());
//				System.out.print(getEspaces(action.getActif().toString(), 7) + action.getRep());
//				System.out.print(getEspaces(action.getRep(), 90) + action.getFichier());
//				System.out.print(getEspaces(action.getFichier().toString(), 22) + action.getSousRep());
//				System.out.print(getEspaces(action.getSousRep().toString(), 16) + action.getDelRep());
//				System.out.print(getEspaces(action.getDelRep().toString(), 16) + action.getDelSousRep());
//				System.out.print(getEspaces(action.getDelSousRep().toString(), 16) + action.getDelFichier());
//				System.out.println(getEspaces(action.getDelFichier().toString(), 16) + action.getClearFichier());
//			}
//			System.out.println("");
//		}
//		
////
////		public static Document creerDocument(List<Action> inModeleAction) {
////			
////			Element racine = new Element("racine");
////			Document document = new Document(racine);
////			
////			for (Action action : inModeleAction) {
////				Element purge = new Element("purge");
////				purge.setAttribute(new Attribute("id", action.getNom()));
////				String strAction = ( action.getActif() != null && action.getActif() ) ? "true" : "false" ;
////				purge.addContent(new Element("actif").setText(strAction));
////				purge.addContent(new Element("rep").setText(action.getRep()));
////				purge.addContent(new Element("fichier").setText(action.getFichier()));
////				String strSousRep = ( action.getSousRep() != null && action.getSousRep() ) ? "true" : "false" ;
////				purge.addContent(new Element("sousRep").setText(strSousRep));
////				String strDelSousRep = ( action.getDelSousRep() != null && action.getDelSousRep() ) ? "true" : "false" ;
////				purge.addContent(new Element("delSousRep").setText(strDelSousRep));
////				String strDelRep = ( action.getDelRep() != null && action.getDelRep() ) ? "true" : "false" ;
////				purge.addContent(new Element("delRep").setText(strDelRep));
////				String strDelFile = ( action.getDelFichier() != null && action.getDelFichier() ) ? "true" : "false" ;
////				purge.addContent(new Element("delFichier").setText(strDelFile));
////				String strClearFile = ( action.getClearFichier() != null && action.getClearFichier() ) ? "true" : "false" ;
////				purge.addContent(new Element("clearFichier").setText(strClearFile));
////				document.getRootElement().addContent(purge);
////			}
////			return document;
////		}
////		
////		// Action XML
////		
////		public static List<ActionXml> recupereListeActionsXml() {
////			
////			List<ActionXml> listeActionsXml = new ArrayList<ActionXml>();
////			List<Element> listeModif = racine.getChildren("modification");
////	
////			Iterator<Element> itListeModif = listeModif.iterator();
////			while (itListeModif.hasNext()) {
////				Element courant = (Element) itListeModif.next();
////				ActionXml actionXml = new ActionXml(courant.getAttributeValue("id"));
////				Boolean actif = ("true".equals(courant.getChild("actif").getText())) ? true : false ;
////				actionXml.setActif(actif);
////				actionXml.setFichier(courant.getChild("fichier").getText());
////				actionXml.setXpath(courant.getChild("xpath").getText());
////				actionXml.setItem(courant.getChild("item").getText());
////				actionXml.setValue(courant.getChild("value").getText());
////				listeActionsXml.add(actionXml);
////	
////			}
////			return listeActionsXml;
////		}
////		
////		
////		public static Document creerDocumentModifier(List<ActionXml> inModeleActionXml) {
////			
////			Element racine = new Element("racine");
////			Document document = new Document(racine);
////			
////			for (ActionXml actionXml : inModeleActionXml) {
////				Element purge = new Element("modification");
////				purge.setAttribute(new Attribute("id", actionXml.getNom()));
////				String strAction = ( actionXml.getActif() != null && actionXml.getActif() ) ? "true" : "false" ;
////				purge.addContent(new Element("actif").setText(strAction));
////				purge.addContent(new Element("fichier").setText(actionXml.getFichier()));
////				purge.addContent(new Element("xpath").setText(actionXml.getXpath()));
////				purge.addContent(new Element("item").setText(actionXml.getItem()));
////				purge.addContent(new Element("value").setText(actionXml.getValue()));
////				document.getRootElement().addContent(purge);
////			}
////			return document;
////		}
////
////		public static void enregistrerListeActionsXml(String strFile, List<ActionXml> inModeleActionXml) {
////			try {
////				XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
////				xmlOutput.output(creerDocumentModifier(inModeleActionXml), new FileOutputStream(strFile));
////			} catch (java.io.IOException e) {
////			}
////		}
////		
////		// Variable
////		
////		public static List<Variable> recupereListeVariables() {
////			List<Variable> listeVariables = new ArrayList<Variable>();
////			List<Element> listeModif = racine.getChildren("variable");
////	
////			Iterator<Element> itListeModif = listeModif.iterator();
////			while (itListeModif.hasNext()) {
////				Element courant = (Element) itListeModif.next();
////				Variable variable = new Variable(courant.getAttributeValue("id"));
////				variable.setValue(courant.getChild("value").getText());
////				listeVariables.add(variable);
////			}
////			return listeVariables;
////		}
////				
////				
////		public static Document creerDocumentVariable(List<Variable> inModeleVariable) {
////	
////			Element racine = new Element("racine");
////			Document document = new Document(racine);
////	
////			for (Variable variable : inModeleVariable) {
////				Element eVariable = new Element("variable");
////				eVariable.setAttribute(new Attribute("id", variable.getKey()));
////				eVariable.addContent(new Element("value").setText(variable.getValue()));
////				document.getRootElement().addContent(eVariable);
////			}
////			return document;
////		}
////	
////		public static void enregistrerListeVariables(String strFile, List<Variable> inModeleVariable) {
////			try {
////				XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
////				xmlOutput.output(creerDocumentVariable(inModeleVariable), new FileOutputStream(strFile));
////			} catch (java.io.IOException e) {
////			}
////		}
				
}
