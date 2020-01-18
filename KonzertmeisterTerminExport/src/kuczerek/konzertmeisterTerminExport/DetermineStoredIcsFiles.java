package kuczerek.konzertmeisterTerminExport;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class DetermineStoredIcsFiles {
	
	private String latestFile;
	private String secLatestFile;
		
	public DetermineStoredIcsFiles () {
		
		String relPath = "./Konzertmeister Kalenderdateien/";
		
		String[] entries = new File(relPath).list();
		String str;
		
		ArrayList<String> sortedFilenames = new ArrayList<String>();
		for (int i = 0; i < entries.length; i++ ) {
			str = new String(entries[i]);
			if (str.endsWith(".ical")) {
				sortedFilenames.add(str);
			}
		}
		Collections.sort(sortedFilenames, Collections.reverseOrder());
				
		switch (sortedFilenames.size()){
			case 0:
				JOptionPane.showMessageDialog(null, "Es wurde keine Konzertmeister Kalenderdatei gefunden.\n"
						+ "Das Programm wird beendet.", Properties.APPTITLE, JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			case 1:
				this.latestFile = relPath + sortedFilenames.get(0).toString();
				this.secLatestFile = relPath + sortedFilenames.get(0).toString();
				break;
			default:
				this.latestFile = relPath + sortedFilenames.get(0).toString();
				this.secLatestFile = relPath + sortedFilenames.get(1).toString();
		}
	}
	
				

	public String getLatestFile() {
		return latestFile;
	}

	public String getSecLatestFile() {
		return secLatestFile;
	}
}
