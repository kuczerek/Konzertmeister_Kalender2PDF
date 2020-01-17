package kuczerek.konzertmeisterTerminExport;

import java.awt.*;

public class Main {

	public static void main(String[] args) {

		//TODO: Manifest Datei ergänzen: SplashScreen-Image: ressources/splash.png
		Splash splash = new Splash();
		try{
			Point upperLeft = SplashScreen.getSplashScreen().getBounds().getLocation();
			splash.setLocation(upperLeft);
		} catch (Exception e) {
			//do nothing?
		}

		splash.setVisible(true);
		try{
			SplashScreen.getSplashScreen().close();
		} catch (Exception e) {
			//do nothing
		}

		splash.setPbarValue(1);
		splash.setPbarString("Download Konzertmeister Kalender...");

		DownloadIcs down = new DownloadIcs(Properties.KONZERTMEISTER_ICS_URL);
		//down.downloadIcs2Disk();

		splash.setPbarValue(25);
		splash.setPbarString("Einlesen Konzertmeister Kalender...");
		DetermineStoredIcsFiles dIcs = new DetermineStoredIcsFiles();  
		Terminplan alleTermineAlt = new Terminplan(dIcs.getSecLatestFile());
		Terminplan alleTermineNeu = new Terminplan(dIcs.getLatestFile());

		splash.setPbarValue(50);
		splash.setPbarString("Erkenne Änderungen...");
		TerminplanVergleich vergl = new TerminplanVergleich (alleTermineAlt, alleTermineNeu);

		splash.setPbarValue(75);
		splash.setPbarString("Schreibe PDF...");
		ExportPdf cpdf = new ExportPdf(vergl.getVerglichenenTerminplan());
		cpdf.printPdf();
		splash.setPbarValue(100);
		splash.setPbarString("Fertig.");
		splash.dispose();
		//at.printTermine2StdOut();

	}
}