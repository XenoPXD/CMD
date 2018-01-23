package prop.gest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import prop.bean.Action;
import prop.impl.properties.LinkedProperties;
import prop.impl.properties.Property;

public class PropertiesGest {

	public static String crud(File inFile, final Action action) throws Exception {
		
		String out = "";
		
		LinkedProperties linkedProperties = new LinkedProperties();
		FileInputStream fileInputStream = new FileInputStream(new File(action.getFile()));
		linkedProperties.load(fileInputStream);
		
		
//		for (int i = 0; i < nodeList.getLength(); i++) {
			if (action.isSet()) {
				create(linkedProperties, action);
			/*} else if (action.isUpdate()) {
				update(linkedProperties, action); */
			} else if (action.isDelete()) {
				delete(linkedProperties, action);
			} else if (action.isRead()) {
				out += read(linkedProperties, action);
			}
//		}
		
		fileInputStream.close();
		if(out != null & !"".equals(out.trim()) ) System.out.println(out);
		return out;
		
	}
	
	public static String read(LinkedProperties linkedProperties, final Action action) throws IOException {
		String out = "";
		if (linkedProperties.containsKey(action.getKey())) {
			out = linkedProperties.getProperty(action.getKey());
		}
		return out;
	}
	
	
	public static void create(LinkedProperties linkedProperties, final Action action) throws IOException {
		if (linkedProperties.containsKey(action.getKey())) {
			action.setKey(action.getKey());
			update(linkedProperties, action);
		} else {
			FileOutputStream fileOutputStream = new FileOutputStream(new File(action.getFile()));
			if (action.getComment() != null
					&& !"".equals(action.getComment().trim()) ) {
				linkedProperties.addComment(action.getComment().trim());
			}
			linkedProperties.setProperty(action.getKey(), action.getValue());
			linkedProperties.store(fileOutputStream,null);
			fileOutputStream.close();
		}
	}
	
	
	public static void update(LinkedProperties linkedProperties, final Action action) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(new File(action.getFile()));
		boolean isUpdate = false;
		if (linkedProperties.containsKey(action.getKey())) {
			Property property = new Property(action.getKey(), action.getValue(), null);
			linkedProperties.setProperty(property);
			isUpdate = true;
			if (action.getComment() != null
					&& !"".equals(action.getComment().trim()) 
					&& linkedProperties.getKeyComment(property) != null ) {
				String keyComment = linkedProperties.getKeyComment(property);
				linkedProperties.setComment(keyComment, action.getComment());
			}
		}
		if (isUpdate) linkedProperties.store(new FileWriter(action.getFile()),null);
		fileOutputStream.close();
	}
	
	
	public static void delete(LinkedProperties linkedProperties, final Action action) throws IOException {
		FileOutputStream fileOutputStream;
		boolean isDelete = false;
		if (linkedProperties.containsKey(action.getKey())) {
			isDelete = true;
			linkedProperties.remove(action.getKey());
		}
		if (action.getComment() != null
				&& !"".equals(action.getComment().trim()) 
				&& linkedProperties.containsComment(action.getComment().trim()) ) {
			String keyComment = linkedProperties.getKeyComment(action.getComment().trim());
			isDelete = true;
			linkedProperties.remove(keyComment);
		}
		if (isDelete) {
			fileOutputStream = new FileOutputStream(new File(action.getFile()));
			linkedProperties.store(new FileWriter(action.getFile()),null);
			fileOutputStream.close();
		}
	}
	
}
