package kuczerek.konzertmeisterTerminExport;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DownloadIcs {
	
	private String icsUrl;
	private String filename;

	public DownloadIcs(String icsUrl) {
		this.icsUrl = icsUrl;
		
		Calendar now = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.-HH.mm.ss");
		String filename = "Konzertmeister " + formatter.format(now.getTime()) + ".ical";
		this.filename = filename;
	}
	
	public void downloadIcs2Disk() {
		
		//Verzeichnis ggf. erstellen
		Path filePath = Paths.get(Properties.REL_PATH_ICS + filename);
	     File folder = new File(Properties.REL_PATH_ICS);
	     if(!folder.exists()) {
	    	 try {
	    		 Files.createDirectory(filePath.getParent());
	    	 } catch (IOException e) {
	    		 e.printStackTrace();
	    	 }
	     }
		
		//Datei runterladen
		try {
			InputStream in = new URL(icsUrl).openStream();
			Files.copy(in, Paths.get(Properties.REL_PATH_ICS + filename), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
