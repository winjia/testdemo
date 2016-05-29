package wacai.main.base;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Pattern;

public class Utils {

	
	//get configure parament form file with "key=value"
	public static String loadProperty(String configFile, String name) {
		Properties p = new Properties();
		String valueString = null;
		try {	
			//InputStream inputStream = new FileInputStream(configFile); 
			InputStream inputStream = Object.class.getResourceAsStream("/"+configFile); 
			p.load(inputStream);
			inputStream.close();
			valueString = p.getProperty(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return valueString;
	}
	
	//update the property and add comment
	public static void setProperty(String filename, String key, String value, String comment) {
		OutputStream fos = null;
		Properties p = new Properties();
		 try {
			 //fos = new FileOutputStream(Object.class.getResource("/"+filename).getFile());
			 //p.load(new FileInputStream("/"+filename));
			 InputStream inputStream = Object.class.getResourceAsStream("/"+filename); 
			 p.load(inputStream);
			 p.setProperty(key, value);
//	         if (comment.length() != 0) {
//	        	 p.store(fos, comment);
//			}
	     } catch (Exception e) {
	    	 e.printStackTrace();
	     } finally {
//	    	 if (null == fos) {
//				return;
//			}
//	    	 try {
//				fos.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
	     }
	}
	
	public static boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();   
	 }
	
	//read file form property
	public ArrayList<String> readPropertiesFile(String file) {
		InputStream in = this.getClass().getResourceAsStream(file);
		if (in == null) {
			System.out.println("File: \""+file+"\" no exist!");
			return new ArrayList<String>();
		}
		BufferedReader br = null;
		InputStreamReader isr = null;
		ArrayList<String> strList = new ArrayList<String>();
        try {
        	isr = new InputStreamReader(in, "UTF-8");
        	br = new BufferedReader(isr); 
        	String line = null; 
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#") && !line.endsWith("#")) {
					continue;
				}
				strList.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				br.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return strList;
	}
	
	//read text from file 
	public static ArrayList<String> getTextFromFile(String path){
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("File: \""+path+"\" no exist!");
			return new ArrayList<String>();
		}
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		ArrayList<String> strList = new ArrayList<String>();
		try {
			fis = new FileInputStream(path);
			isr = new InputStreamReader(fis, "UTF-8"); 
	        br = new BufferedReader(isr); 
	        String line = null; 
	        while ((line = br.readLine()) != null) {
	        	if (line.startsWith("#") && !line.endsWith("#")) {
					continue;
				}
	        	strList.add(line);
	        } 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				br.close();
				isr.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return strList;
	}
	
	//load field configure file
	public HashMap<String, String> loadCnfFile(String path){
		return new HashMap<String, String>();
	}
	
	//strip all punctuation
	public static String stripPunctuation(String instr){
		String str = instr.replaceAll("[\\pP‘’“”]", "");
		return str;
	}
	
	//split the string with specified delim and return string-list
	public static ArrayList<String> parseMultiString(String str, String delim) {
		ArrayList<String> list = new ArrayList<String>();
		String[] strs = str.split(delim);
		for (int i = 0; i < strs.length; i++) {
			list.add(strs[i]);
		}
		return list;
	}
	
	//get the name of files (no directory) in some directory
	//if need no suffix, set suffix = "";
	public static ArrayList<String> getFileName(String path, String suffix) {
		File f = new File(path);
		if(!f.isDirectory()){
			System.out.println("["+path+"] is not a directory!");
			return new ArrayList<String>();
	    }
		File[] t = f.listFiles();
		ArrayList<String> fname_list = new ArrayList<String>();
		if (suffix.isEmpty()) {
			for(int i=0;i<t.length;i++){
				if (t[i].isDirectory()) {
					continue;
				}
				fname_list.add(t[i].getName());
			}
		} else {
			for(int i=0;i<t.length;i++){
				if (t[i].isDirectory()) {
					continue;
				}
				if (t[i].getName().endsWith(suffix)) {
					fname_list.add(t[i].getName());
				}
			}
		}
		return fname_list;
	}
	
	//get the full path of files (no directory) in some directory
	//if need no suffix, set suffix = "";
	public static ArrayList<String> getFileNameWithPath(String path, String suffix) {
		File f = new File(path);
		if (!f.isDirectory()) {
			System.out.println("[" + path + "] is not a directory!");
			return new ArrayList<String>();
		}
		File[] t = f.listFiles();
		ArrayList<String> fname_list = new ArrayList<String>();
		if (suffix.isEmpty()) {
			for (int i = 0; i < t.length; i++) {
				if (t[i].isDirectory()) {
					continue;
				}
				fname_list.add(t[i].getAbsolutePath());
			}
		} else {
			for (int i = 0; i < t.length; i++) {
				if (t[i].isDirectory()) {
					continue;
				}
				if (t[i].getName().endsWith(suffix)) {
					fname_list.add(t[i].getAbsolutePath());
				}
			}
		}
		return fname_list;
	}
	//get the directory of the given file full path
	//if the given full path is a directory, then return it directly
	public static String getDirectoryOfFile(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			return path;
		}
		return file.getParentFile().getAbsolutePath();
	}
	
	//convert timestamp to format time
	public static String formatTimestamp(long timestamp) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		String sd = sdf.format(new Date(timestamp));
		return sd;
	}
	
	//get current format timestamp
	public static String currentFormatTimestamp() {
		long timestamp = System.currentTimeMillis();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		String sd = sdf.format(new Date(timestamp));
		return sd;
	}
	
	public static void main(String[] args) {
		//System.out.println(Utils.getInstance().currentFormatTimestamp());
		boolean res = Utils.isNumeric("1232a3123");
		if (res) {
			System.out.println("is ditial!");
		} else {
			System.out.println("is no ditial!");
		}
	}
}
