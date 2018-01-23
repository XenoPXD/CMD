package prop.impl.properties;

public class Property {

	private String key;
	private String value;
	private Integer index;
	
	public Property(String key, String value, Integer index) {
		super();
		this.key = key;
		this.value = value;
		this.index = index;
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
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	
	
}
