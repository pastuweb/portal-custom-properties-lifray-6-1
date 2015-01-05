package net.appuntivari.properties.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class Config {

	private static final Logger logger = Logger.getLogger(Config.class.getName());

	
	private static Properties m_cfg = null;
	private static boolean expansion = true;

	private static final String startTag = "${";
	private static final String endTag = "}";

	
	private static final String propsFileName = "/home/portaleLiferay/liferay-portal-6.1.2-ce-ga3/my-personal.properties";
	
	private static synchronized void configInit() {
		try {
			//String jbossHome = System.getenv("HOME");
			//logger.info("Loading propsFileName [" + propsFileName + "]");
			Config.load(new FileInputStream(propsFileName));
		} catch (Exception ex) {
			String msg = "Errore di caricamento dei file di properties [" + propsFileName + "]";
			//logger.info(msg, ex);
		}
	}

	
	/**
	 * @param in
	 *            the key which string value to be replaced with values from
	 *            this property list
	 * @throws Exception 
	 */
	private static String expand(String in){
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}
		
		if (in == null)
			return null;
		StringBuffer out = new StringBuffer();
		int index = 0;
		int i = 0;
		String key = null;
		while ((index = in.indexOf(startTag, i)) > -1) {
			key = in.substring(index + 2, in.indexOf(endTag, index + 3));
			out.append(in.substring(i, index));
			String val = getProp(key);
			if (val != null) {
				// be careful here
				out.append(expand(val));
			} else {
				out.append(startTag).append(key).append(endTag);
			}
			i = index + 3 + key.length();
		}
		return out.append(in.substring(i)).toString();
	}

	private static String expand(String in, Properties props) {
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}
		
		if (in == null)
			return null;
		StringBuffer out = new StringBuffer();
		int index = 0;
		int i = 0;
		String key = null;
		while ((index = in.indexOf(startTag, i)) > -1) {
			key = in.substring(index + 2, in.indexOf(endTag, index + 3));
			out.append(in.substring(i, index));
			String val = getProp(key, props);
			if (val != null) {
				// be careful here
				out.append(expand(val, props));
			} else {
				out.append(startTag).append(key).append(endTag);
			}
			i = index + 3 + key.length();
		}
		return out.append(in.substring(i)).toString();
	}

	/**
	 * Replaces all occurrences of the substitution tag in the given Properties.
	 * @throws Exception 
	 */
	protected static void expandAll(Properties p) {
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}
		
		for (Enumeration en = p.keys(); en.hasMoreElements();) {
			String k = (String) en.nextElement();
			Object v = p.get(k);
			if (v instanceof String) {
				String nv = expand((String) v);
				p.put(k, nv);
			}
			if (v instanceof Properties) {
				expandAll((Properties) v);
			}
		}
	}

	public static boolean getBoolean(String prop){
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}
		
		boolean result = false;
		result = getProp(prop).equalsIgnoreCase("true");
		return result;
	}

	/**
	 * @param prop
	 *            prop identifica la chiave con cui ricercare la properties nel
	 *            file
	 * @return ritorna il valore della properties ricercata
	 * @throws Exception 
	 */
	public static char getChar(String prop){
		char result = '-';
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}

		try {
			result = getProp(prop).charAt(0);
		} catch (Exception ex) {
			String msg = "Property [" + prop + "] charAt(0) error!";
			logger.info(msg, ex);
		}

		return result;
	}

	/**
	 * @param prop
	 *            prop identifica la chiave con cui ricercare la properties nel
	 *            file
	 * @return ritorna il valore della properties ricercata come intero
	 * @throws Exception 
	 */
	public static int getInt(String prop) {
		int requestedItem = 0;
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}
		
		try {
			requestedItem = Integer.parseInt(getProp(prop));
		} catch (NumberFormatException ex) {
			String msg = "Property [" + prop + "] is not a number!";
			//logger.info(msg, ex);
		}

		return requestedItem;

	}

	/**
	 * @param prop
	 *            prop identifica la chiave con cui ricercare la properties nel
	 *            file
	 * @return ritorna il valore della properties ricercata come intero
	 * @throws Exception 
	 */
	public static long getLong(String prop) {
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}
		
		long requestedItem = 0;

		try {
			requestedItem = Long.parseLong(getProp(prop));
		} catch (NumberFormatException ex) {
			String msg = "Property [" + prop + "] is not a number!";
			//logger.info(msg, ex);
		}

		return requestedItem;

	}

	/**
	 * @param propName
	 *            identifica la chiave con cui ricercare la properties nel file
	 * @return ritorna il valore della properties ricercata come String
	 * @throws Exception 
	 */
	public static String getProp(String propName) {
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}
		
		String propValue;

		if (m_cfg == null) {
			configInit();
		}

		if ((propValue = m_cfg.getProperty(propName)) == null) {
			//logger.info("Configuration item [" + propName + "] missing!");
		}

		if (isExpansionEnabled()) {
			propValue = expand(propValue);
		}

		//logger.info("returning propName [" + propName + "] propValue [" + propValue + "]");
		return propValue;
	}

	public static String getProp(String propName, Properties customProps) {
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}
		
		String propValue;

		if (m_cfg == null) {
			configInit();
		}

		customProps.putAll(m_cfg);
		if ((propValue = customProps.getProperty(propName)) == null) {
			//logger.info("Configuration item [" + propName + "] missing!");
		}

		if (isExpansionEnabled()) {
			propValue = expand(propValue, customProps);
		}

		//logger.info("returning propName [" + propName + "] propValue [" + propValue + "]");
		return propValue;
	}

	/**
	 * @return
	 */
	public static Properties getPropertiesObj() {
		if (m_cfg == null) {
			logger.info("Properties object null!");
		}

		return m_cfg;
	}

	/**
	 * @param prop
	 *            identifica la chiave con cui ricercare la properties nel file
	 * @return ritorna la lista di quelle properties configurate come una lista
	 *         separata da |
	 * @throws Exception 
	 */
	public static List<String> getProps(String prop) {
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}
		
		String requestedItem;
		List<String> result = new ArrayList<String>();

		try {
			requestedItem = getProp(prop);
			StringTokenizer st = new StringTokenizer(requestedItem, "|");
			while (st.hasMoreElements()) {
				String elem = st.nextToken();
				result.add(elem);
			}
		} catch (Exception ex) {
			String msg = "Property [" + prop + "] error!";
			//logger.info(msg, ex);
		}

		return result;

	}

	/**
	 * @param keyList
	 *            Stringa che identifica la lista di proprieta' che sranno
	 *            chiave della mappa
	 * @param valueList
	 *            Stringa che identifica la lista di proprieta' che sranno
	 *            valori della mappa
	 * @return result Ritorna la mappa delle proprieta' associando i campi desc
	 *         e value presenti nel file di properties
	 * @throws Exception 
	 */

	public static Map<String, String> getPropsMap(String keyList, String valueList) {
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}
		
		Map<String, String> result = new HashMap<String, String>();
		List requestedKey;
		List requestedValue;
		int i;

		requestedKey = getProps(keyList);
		requestedValue = getProps(valueList);

		if (requestedKey.size() == requestedValue.size()) {
			for (i = 0; i < requestedKey.size(); i++) {
				result.put((String) requestedKey.get(i), (String) requestedValue.get(i));
			}

			return result;
		} else {
			return result;
		}
	}
	
	
	public static Map<String, String> getAllProperties() throws FileNotFoundException, IOException {
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}
		
		Map<String, String> result = new HashMap<String, String>();
		
		Properties p = new Properties();
	    p.load(new FileInputStream(propsFileName));
	    
	    Set<Object> keys = p.keySet();
	    
		for(Object k : keys){
			String key = (String)k;
			result.put(key, (String)p.getProperty(key));
		}	
	    return result;
	}
	
	public static List<String> getAllKeyProperties() throws FileNotFoundException, IOException {
		
		try{
			reload();
		}catch(Exception e){
			logger.info("Reload FALLITO - Properties NON ALLINEATE");
		}
		
		List<String> result = new ArrayList<String>();
		
		Properties p = new Properties();
	    p.load(new FileInputStream(propsFileName));
	    
	    Set<Object> keys = p.keySet();
	    
		for(Object k : keys){
			String key = (String)k;
			result.add(key);
		}	
		
	    return result;
	}

	public static boolean isExpansionEnabled() {
		return expansion;
	}

	/**
	 * @param InputStream
	 *            da caricare
	 * @throws Exception
	 */
	private static synchronized void load(InputStream is) throws Exception {
		try {
			m_cfg = new Properties();
			m_cfg.load(is);
		} catch (Exception ex) {
			String msg = "Error loading properties!";
			//logger.info(msg, ex);
		}
	}
	
	/**
	 * @param InputStream
	 *            da caricare
	 * @throws Exception
	 */
	public static synchronized void reload() throws Exception {
		configInit();
	}
	
	public static void updateOldPropWithNewProp(String oldProp,String newProp) throws IOException {

	    // we need to store all the lines
	    List<String> lines = new ArrayList<String>();

	    // first, read the file and store the changes
	    BufferedReader in = new BufferedReader(new FileReader(propsFileName));
	    String line = in.readLine();
	    while (line != null) {
	        if (line.startsWith(oldProp)) {
	            line = newProp;
	        }
	        lines.add(line);
	        line = in.readLine();
	    }
	    in.close();

	    // now, write the file again with the changes
	    PrintWriter out = new PrintWriter(propsFileName);
	    for (String l : lines)
	        out.println(l);
	    out.close();

	}
	
	public static void insertNewProp(String newProp) throws IOException {

	    // we need to store all the lines
	    List<String> lines = new ArrayList<String>();

	    // first, read the file and store the changes
	    BufferedReader in = new BufferedReader(new FileReader(propsFileName));
	    String line = in.readLine();
	    while (line != null) {
	        lines.add(line);
	        line = in.readLine();
	    }
	    lines.add(newProp);
	    in.close();

	    
	    // now, write the file again with the changes
	    PrintWriter out = new PrintWriter(propsFileName);
	    for (String l : lines)
	        out.println(l);
	    out.close();

	}

	
}
