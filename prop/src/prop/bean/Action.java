package prop.bean;

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

	private String value;
	private String comment;
	private String file;
	private boolean read;
	private boolean delete;
	private Pattern pattern;
	private String key;
	private boolean set;
	
	private final static NumberFormat formatter = new DecimalFormat("#0.00");  
	
	// Key
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}



	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
	
	
	public boolean isRead() {
		return read;
	}


	public void setRead(boolean read) {
		this.read = read;
	}


	public boolean isDelete() {
		return delete;
	}


	public void setDelete(boolean delete) {
		this.delete = delete;
	}


	public Pattern getPattern() {
		return pattern;
	}
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}



	public boolean isSet() {
		return set;
	}

	public void setSet(boolean set) {
		this.set = set;
	}
	
	
	
}
