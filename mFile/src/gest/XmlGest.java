package gest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import bean.Action;

public class XmlGest {

	private static Document document;
	
	public static String crud(File inFile, final Action action) throws Exception {
		
		// https://www.w3.org/TR/xpath/
		// https://www.w3.org/TR/xpath/#nt-bnf
		// https://docs.oracle.com/javase/tutorial/jaxp/xslt/xpath.html
		// https://stackoverflow.com/questions/11211182/getting-the-value-of-an-attribute-in-java-from-a-xml-using-xpath
		// http://tutorialspointexamples.com/dom-parser-to-modify-xml-file-in-java/
		
//		System.out.print(action.getXpath());
//		System.out.print(" " +  action.getValue());
//		System.out.println(" " +  action.getElement());
		
		DocumentBuilderFactory xmlFact = DocumentBuilderFactory.newInstance();
		xmlFact.setNamespaceAware(false);
				
		DocumentBuilder builder = xmlFact.newDocumentBuilder();
		
		
		javax.xml.xpath.XPathFactory xpathFactory = javax.xml.xpath.XPathFactory.newInstance();
		String expr = action.getXpath();
		XPath xpath = xpathFactory.newXPath();
		
		InputStream inputStream= new FileInputStream(inFile);
		Reader reader = new InputStreamReader(inputStream,"UTF-8");
		InputSource is = new InputSource(reader);
		
		document = builder.parse(is);
		NodeList nodeList = (NodeList) xpath.compile(expr).evaluate(document, XPathConstants.NODESET);
		
//		System.out.println(nodeList.getLength());
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (action.isCreate()) {
				create(node, action);
			} else if (action.isUpdate()) {
				update(node, action);
			} else if (action.isDelete()) {
				delete(node, action);
			} else if (action.isRead()) {
				read(node, action);
			}
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File(inFile.getAbsolutePath()));
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, result);
	
		//For console Output.
//		StreamResult consoleResult = new StreamResult(System.out);
//		transformer.transform(source, consoleResult);	
				
//		System.out.println(nl);
		
		return "";
			     
	}
	
	public static void create(Node node, final Action action) throws Exception {

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			if (cons.Element.NODE.equalsIgnoreCase(action.getElement())) {
				Node nodeNew = document.importNode(action.getNode(), true);
				node.appendChild(nodeNew);
			} else if (action.getElement().startsWith(cons.Element.ATTR)) {
				( (Element)node ).setAttribute(action.getAttrName(), action.getValue());
			} else if (cons.Element.TEXT.equalsIgnoreCase(action.getElement())) {
				node.setTextContent(action.getValue());
			}
		}
	}
	
	public static void read(Node node, final Action action) throws Exception {

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			DOMImplementationLS domImplLS = (DOMImplementationLS) document
				    .getImplementation();
			LSSerializer serializer = domImplLS.createLSSerializer();
			serializer.getDomConfig().setParameter("xml-declaration", false);
			String str = serializer.writeToString(node);
			System.out.println(str);
		} else if (node.getNodeType() == Node.ATTRIBUTE_NODE || node.getNodeType() == Node.TEXT_NODE) {
			System.out.println(node.getNodeValue());
		}
		
	}
	
	public static void update(Node node, final Action action) throws Exception {

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Node nodeParent = node.getParentNode();
			Node nodeNew = document.importNode(action.getNode(), true);
			nodeParent.replaceChild(nodeNew, node);
		}  else if (node.getNodeType() == Node.ATTRIBUTE_NODE ) {
			node.setNodeValue(action.getValue());
		} else if (node.getNodeType() == Node.TEXT_NODE) {
			node.setTextContent(action.getValue());
		}
		
	}
	
	public static void delete(Node node, final Action action) throws Exception {

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Node nodeParent = node.getParentNode();
			nodeParent.removeChild(node);
		} else if (node.getNodeType() == Node.ATTRIBUTE_NODE ) {
			Attr attribute = (Attr) node;
			Node nodeParent = attribute.getOwnerElement();
			System.out.println(nodeParent.getNodeName());
			( (Element)nodeParent ).removeAttribute(node.getNodeName());
		} else if (node.getNodeType() == Node.TEXT_NODE) {
			node.setNodeValue("");
		}
		
	}
	
	
}
