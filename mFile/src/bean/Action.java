package bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Action {

	private String xpath;
	private String value;
	private String file;
	private String crud;
	private String element;
	private boolean info;
	private boolean create;
	private boolean read;
	private boolean update;
	private boolean delete;
	private Pattern pattern;
	
	
	
	
	private final static NumberFormat formatter = new DecimalFormat("#0.00");  
	
	public Node getNode() throws SAXException, IOException, ParserConfigurationException {
		Node node = (Node) DocumentBuilderFactory
		    .newInstance()
		    .newDocumentBuilder()
		    .parse(new ByteArrayInputStream(this.getValue().getBytes()))
		    .getDocumentElement();	
		return node;
	}
	
	
	public String getAttrName() {
		String out = "";
		if (!"".equals(this.getValue().trim())) {
			out = this.element.replaceAll("@", "");
		}
		return out;
	}
	public String getAttrValue() {
		String out = "";
		if (!"".equals(this.getValue().trim())) {
			String[] parts = this.getValue().trim().split("=");
			out = parts[1];
		}
		return out;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getXpath() {
		return xpath;
	}
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file; /*.replaceAll("'", "");
		String strPattern = this.file.replaceAll("\\*", "(.)*");
		this.pattern = Pattern.compile("^"+strPattern+"$"); */
	}
	
	
	public String getCrud() {
		return crud;
	}
	public void setCrud(String crud) {
		this.crud = crud;
	}
	
	public boolean isCreate() {
		return create;
	}


	public void setCreate(boolean create) {
		this.create = create;
	}


	public boolean isRead() {
		return read;
	}


	public void setRead(boolean read) {
		this.read = read;
	}


	public boolean isUpdate() {
		return update;
	}


	public void setUpdate(boolean update) {
		this.update = update;
	}


	public boolean isDelete() {
		return delete;
	}


	public void setDelete(boolean delete) {
		this.delete = delete;
	}


	public boolean isInfo() {
		return info;
	}
	public void setInfo(boolean clear) {
		this.info = clear;
	}
	public Pattern getPattern() {
		return pattern;
	}
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
	public static String convertTailleToString(long val) {
		String outVal = "";
		if ((double) val / (1024 * 1024 * 1024) > 1) {
			outVal = formatter.format(round((double) val / (1024 * 1024 * 1024), 2)).concat(" Go");
		} else if ((double) val / (1024 * 1024) > 1) {
			outVal = formatter.format(round((double) val / (1024 * 1024), 2)).concat(" Mo");
		} else if ((double) val / 1024 > 1) {
			outVal = formatter.format(round((double) val / 1024, 2)).concat(" Ko");
		} else if (val > 1) {
			outVal = (val + "  o");
		} else {
			outVal = ("0  o");
		}
		return outVal;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	
}
