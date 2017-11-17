package gest;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import bean.Action;

public class JavascriptGest {

	private static final ScriptEngineManager factory = new ScriptEngineManager();
    private static final ScriptEngine engine = factory.getEngineByName("nashorn");
    private static final Invocable invocable = (Invocable)engine;
    
	public static void code(final Action action) throws Exception {
	
		String out = (String) engine.eval(action.getValue());
	    if (out != null && !"".equals(out.trim())) {
	    	System.out.println(out);
	    }
		
	}
	
	public static void file(final Action action) throws Exception {
		
		// Build a Reader
		for (int i = 0; i < action.getFile().length; i++) {
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(action.getFile()[i]);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			engine.eval(inputStreamReader);
		}
		
	    if (action.getValue() != null && !"".equals(action.getValue().trim())) {
		    code(action);
	    }
		
	}
	
}
