package prop.impl.properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
 
/**
 * http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/util/Hashtable.java#Hashtable.containsKey%28java.lang.Object%29
 * https://coderanch.com/t/544562/java/save-properties-file-affecting-existing
 * This class is similar to the java.util.Properties class in functionality
 * but is not derived from it. The main feature of this class is that it
 * attempts to preserve the order that the properties were in when they were
 * loaded so that saving them again should yield a properties file that is
 * in pretty much the same order. New properties will be added to the end.
 * Ignored white space will not appear after store() is called.
 *
 * Not supported:
 * Line continuation by escaping the new-line.
 * Escaping characters in the key.
 * Warning: other differences may exist.
 *
 * Comments beginning with "#!" are created during the store() operation and
 * are not kept in the data structure during a load() operation.
 *
 * @author cbrown
 *
 */
public class LinkedProperties
implements Iterable<Map.Entry<String,String>>
{
    private final LinkedHashMap<String,String>        map = new LinkedHashMap<String,String>();
    private int             commentCount;
 
    //----------------------------------------------------------------------
    public String getProperty( String key )
    {
        return map.get( key ).trim();
    }
 
    //----------------------------------------------------------------------
	public boolean isEmpty() {
		return map.isEmpty();
    }
	
    //----------------------------------------------------------------------
	public boolean containsKey(String key) {
		String s = map.get(key);
		if (s != null && !"".equals(s.trim())) {
			return true;
		}
		return false;
    }
    
	// ----------------------------------------------------------------------
	public void remove(String key) {
		map.remove(key);
	}
		
    //----------------------------------------------------------------------
    public String getProperty( String key, String defaultValue )
    {
        String      s = map.get( key );
        
        if( s != null )
            return s;
        return defaultValue;
    }
 
    //----------------------------------------------------------------------
    /**
     * If the key does not exist then add new entry to the end of the
     * linked list. If the key does exist then replace the entry at
     * its current location in the list.
     * @param key
     * @param value
     * @return previous value of the key (which may be null)
     */
    public String setProperty( String key, String value )
    {
        String      s = map.get( key );
        map.put( key, value );
        return s;
    }
 
    public String setProperty( Property property )
    {
        String      s = map.get( property.getKey() );
        map.put( property.getKey(), property.getValue() );
        return s;
    }
    
    //----------------------------------------------------------------------
    private void setLine( String s, int lineno )
    {
        int             i = 0;
        char            c;
        int             state = 0;
 
        if( s.startsWith( "#!" ) )
            return; // strip out #! comments
 
        StringBuffer    key = new StringBuffer();
        StringBuffer    value = new StringBuffer();
 
        for( ; i < s.length() ; i++ )
        {
            c = s.charAt( i );
            switch( state )
            {
            case 0:
                if( ! Character.isWhitespace( c ) )
                    { state = 1; i--; }
                // ignore leading white space
                break;
            case 1:
                if( c == '#' || c == '!' )
                    i = s.length(); // break out for comments
                else
                    { key.append( c ); state = 2; }
                break;
            case 2:
                if( Character.isWhitespace( c ) || c == '=' || c == ':' )
                    { state = 3; i--; }
                else
                    key.append( c );
                break;
            case 3:
                if( ! Character.isWhitespace( c ) )
                    { state = 4; i--; }
                // ignore leading white space
                break;
            case 4:
                if( c == '=' || c == ':' )
                    state = 5;
                else
                    throw new IllegalArgumentException( "Line in properties file is malformed: " + lineno );
                break;
            case 5:
                if( ! Character.isWhitespace( c ) )
                    { state = 6; i--; }
                // ignore leading white space
                break;
            case 6:
                value.append( c );
                break;
            }
        }
 
        if( key.length() == 0 )
            addComment( s );
        else
            map.put( key.toString(), value.toString() );
    }
 
    //----------------------------------------------------------------------
    public void addComment( String s )
    {
        map.put( "#" + (++commentCount), s );
    }
    
    //----------------------------------------------------------------------
    public boolean containsComment( String value )
    {
    	String key = getKeyComment(value);
    	if (key != null && !"".equals(key)) {
    		return true;
    	}
    	return false;
    }
    
    //----------------------------------------------------------------------
    public void setComment( String key, String value  )
    {
    		map.put(key, value);
    }
    
    
	/**
	 * Return the key of the comment attached to the property
	 * @param property
	 * @return
	 */
	public String getKeyComment(Property property) {
		String keyComment = null;
		List<String> listKey = new ArrayList<String>(map.keySet());
		Collections.reverse(listKey);
		boolean isStart = false;
		for (String key : listKey) {
			if (isStart) {
				if (key.trim().startsWith("#") && !"".equals(map.get(key).trim())) {
					keyComment = key;
					break;
				} else if (!key.trim().startsWith("#")) {
					break;
				}
			}
			if (key.equals(property.getKey())) {
				isStart = true;
			}
		}
		return keyComment;
	}
    
    //----------------------------------------------------------------------
    public String getKeyComment( String value )
    {
    	String key= null;
        for(Map.Entry<String, String> entry: map.entrySet()){
            if(value.equals(entry.getValue())){
                key = (String) entry.getKey();
                break;
            }
        }
        return key;
    }
    
    //----------------------------------------------------------------------
    public Integer getKeyIndex( String key )
    {
		Integer out = null;
    	List<String> list = new ArrayList<String>(map.keySet());
		int index = 0;
		for (String string : list) {
			if (string.equals(key.trim())) {
				out = index;
			}
			index++;
		}
		return out;
    }
 
    //----------------------------------------------------------------------
    public void load( String fname )
    throws IOException
    {
        File            file = new File( fname );
        if( file.exists() && file.isFile() )
        {
            InputStream     is = null;
            try
            {
                load( is = new FileInputStream( file ) );
            }
            catch( IOException e )
            {
                throw e;
            }
            finally
            {
                if( is != null )
                    { try{ is.close(); }catch( IOException ex ){ ex.printStackTrace(); }
                }
            }
        }
    }
 
    //----------------------------------------------------------------------
    public void load( InputStream is )
    throws IOException
    {
        load( new InputStreamReader( is ) );
    }
 
    //----------------------------------------------------------------------
    /**
     * Warning: Calling this more than once will possibly cause
     * comments to be overwritten. NOTE: Does not close the Reader.
     *
     * @param reader
     * @throws IOException
     */
    public void load( Reader reader )
    throws IOException
    {
        String          s;
        int             lineno = 0;
 
        commentCount = 0;
        BufferedReader  in = new BufferedReader( reader );
        while( (s = in.readLine()) != null )
            setLine( s, ++lineno );
    }
 
    //----------------------------------------------------------------------
    public void store( String fname, String comment )
    throws IOException
    {
        File            file = new File( fname );
        if( file.exists() && ! file.isFile() )
            return;
 
        OutputStream        os = null;
        try
        {
            store( os = new FileOutputStream( file ), comment );
        }
        catch( IOException e )
        {
            throw e;
        }
        finally
        {
            if( os != null )
                { try{ os.close(); }catch( IOException ex ){ ex.printStackTrace(); } }
        }
    }
 
    //----------------------------------------------------------------------
    public void store( OutputStream out, String comment )
    throws IOException
    {
        store( new PrintWriter( out, true ), comment );
    }
 
    //----------------------------------------------------------------------
    public void store( Writer writer, String comment )
    throws IOException
    {
        store( new PrintWriter( writer, true ), comment );
    }
 
    //----------------------------------------------------------------------
    public void store( PrintWriter out, String comment )
    throws IOException
    {
        if (comment != null & !"".equals(comment)) out.println( "#! " + comment );
        out.println( "#! " + new Date() );
 
        Iterator<Entry<String,String>>  itr = iterator( false );
        while( itr.hasNext() )
            out.println( toString( itr.next() ) );
    }
 
    //----------------------------------------------------------------------
    public static String toString( Entry<String,String> entry )
    {
        if( entry.getKey().charAt( 0 ) == '#' )
            return entry.getValue();
        return entry.getKey() + "=" + entry.getValue();
    }
 
    //----------------------------------------------------------------------
    // For debugging
    public void list( PrintStream out )
    {
        Iterator<Entry<String,String>>  itr = iterator( false );
        while( itr.hasNext() )
            out.println( toString( itr.next() ) );
    }
 
    //----------------------------------------------------------------------
    /**
     * This is the default iterator which automatically skips comment
     * entries. This is what is instantiated in a "for each" loop.
     */
    @Override
    public Iterator<Entry<String, String>> iterator()
    {
        return new LinkedPropertiesIterator( map.entrySet(), true );
    }
 
    //----------------------------------------------------------------------
    /**
     * This is a special iterator which allows you to choose whether or not
     * it skips over comment entries. This cannot be used in a "for each"
     * loop.
     */
    public Iterator<Entry<String, String>> iterator( boolean skipComments )
    {
        return new LinkedPropertiesIterator( map.entrySet(), skipComments );
    }
 
    //----------------------------------------------------------------------
    // Unit testing
//    public static void main( String[] args )
//    {
//        LinkedProperties    props = new LinkedProperties();
//        String              userHome = System.getProperty( "user.home" );
//        String              fileName = userHome + "\\testLinkedProperties.properties";
//        try
//        {
//            props.load( fileName );
//            props.list( System.out );
//            props.store( fileName, "This is a test, this is only a test, ..." );
//        }
//        catch( Exception ex )
//        {
//            ex.printStackTrace();
//        }
//    }
}