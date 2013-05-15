package database;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.*;
import java.util.Date;
 

public class XMLWorkSet {

	private static XMLWorkSet xws = null;
	private XStream stream = new XStream(new StaxDriver());
    protected static final String logPath = System.getProperty("user.home") + File.separator
            + "CashBook" + File.separator + "log.xml";

	private XMLWorkSet() {
		
	}

	static synchronized XMLWorkSet getInstance() {
		if (xws == null) {
			xws = new XMLWorkSet();
		}
		return xws;
	}

	

	public Object load(String fileNameandPath) throws IOException {
		
		File file = new File(fileNameandPath);

		if (!file.exists() || file.length() == 0) {
			throw new IOException();
		} else {
			return stream.fromXML(new InputStreamReader(new FileInputStream((file)),"UTF-8"));
		}

	}

	public void save(String fileNameandPath, Object object) throws IOException {

		
		File file = new File(fileNameandPath);
		if (file.exists()) {
			stream.toXML(object, new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
		} else {
			
			new File(file.getParent()).mkdir();

			file.createNewFile();
			stream.toXML(object, new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));

		}
	}
	

	public void saveErrorTrace(Exception e, String message) {
		
		
		try{
			File file = new File(logPath);
			if (!file.exists()) {				
				new File(file.getParent()).mkdir();
				file.createNewFile();				
			}
			
			 
		         FileWriter fstream = new FileWriter(file, true);
		         BufferedWriter out = new BufferedWriter(fstream);
		         PrintWriter pWriter = new PrintWriter(out, true);
		         pWriter.println();
		         pWriter.println();
		         pWriter.println(new Date().toString());
		         pWriter.println();
                 pWriter.println(message);
		         e.printStackTrace(pWriter);
		      }
		      catch (Exception ie) {
		         throw new RuntimeException("Could not write Exception to file", ie);
		      }
		
	}


}
