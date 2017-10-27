package bean;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

public class Action {

	private String path;
	private String file;
	private boolean sub;
	private boolean clear;
	private Pattern pattern;
	
	private final static NumberFormat formatter = new DecimalFormat("#0.00");  
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path.replaceAll("'", "");
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file.replaceAll("'", "");
		String strPattern = this.file.replaceAll("\\*", "(.)*");
		this.pattern = Pattern.compile("^"+strPattern+"$");
	}
	
	public boolean isSub() {
		return sub;
	}
	public void setSub(boolean sub) {
		this.sub = sub;
	}
	public boolean isClear() {
		return clear;
	}
	public void setClear(boolean clear) {
		this.clear = clear;
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
