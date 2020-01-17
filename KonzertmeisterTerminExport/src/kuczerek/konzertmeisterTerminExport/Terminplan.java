package kuczerek.konzertmeisterTerminExport;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.*;
import java.util.Iterator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.*;

import javax.swing.JOptionPane;

public class Terminplan {

	private ArrayList<BchTermin> probenTermine;
	private ArrayList<BchTermin> auftrittsTermine;
	private ArrayList<BchTermin> sonstigeTermine;
	private java.util.Calendar created;
	private java.util.Calendar comparedTo;
	private java.util.Calendar read;

	public Terminplan() {
		this.probenTermine = new ArrayList<BchTermin>();
		this.auftrittsTermine = new ArrayList<BchTermin>();
		this.probenTermine = new ArrayList<BchTermin>();
		this.created = java.util.Calendar.getInstance();
	}
	
	public Terminplan(String dest) {

		//Lesedatum setzen
		this.read = decodeFileName2Calendar(dest);
		
		// Einlesen der Datei und Erstellen eines Calendar-Objekts
		FileInputStream fin;
		try {
			fin = new FileInputStream(dest);
		} catch (FileNotFoundException e) {
			fin = null;
			e.printStackTrace();
		}

		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = new Calendar();
		try {
			calendar = builder.build(fin);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserException e) {
			e.printStackTrace();
		}

		// Initialisierung der Daten-Arrays
		probenTermine = new ArrayList<>();
		auftrittsTermine = new ArrayList<>();
		sonstigeTermine = new ArrayList<>();

		// Aufteilung der VEVENTS aus dem Calendar-Objekt in die Daten-Arrays
		ComponentList<VEvent> cl = calendar.getComponents(Component.VEVENT);
		for (Iterator<VEvent> i = cl.iterator(); i.hasNext();) {
			VEvent ce = i.next();

			BchTermin bchTermin = new BchTermin();

			// UID
			try {
				bchTermin.setUid(ce.getUid().getValue());
			} catch (Exception e) {
				bchTermin.setUid("");
			}

			// Start
			net.fortuna.ical4j.model.Date dateStart = ce.getStartDate().getDate();
			if (dateStart != null) {
				bchTermin.setStart(dateStart);
			} else {
				bchTermin.setStart(new net.fortuna.ical4j.model.Date());
			}

			// Ende
			net.fortuna.ical4j.model.Date dateEnde = ce.getEndDate().getDate();
			if (dateEnde != null) {
				bchTermin.setEnde(dateEnde);
			} else {
				bchTermin.setEnde(new net.fortuna.ical4j.model.Date());
			}

			// Titel
			try {
				String titel = ce.getSummary().getValue();
				int indexTitel = titel.indexOf("(Bläsercorps Hackenstedt e.V.)");
				titel = titel.substring(0, indexTitel);
				titel = titel.trim();
				bchTermin.setTitel(titel);
			} catch (Exception e) {
				bchTermin.setTitel("");
			}

			// Beschreibung + Art
			try {
				String rohBeschreibung = ce.getDescription().getValue();
				int indexBeschr = rohBeschreibung.indexOf(":");
				if (indexBeschr < 0) {
					String art = rohBeschreibung;
					String beschreibung = "";
					bchTermin.setArt(art);
					bchTermin.setBeschreibung(beschreibung);
				}
				else {			
					String art = rohBeschreibung.substring(0, indexBeschr);
					String beschreibung = rohBeschreibung.substring(indexBeschr + 2);
					beschreibung = beschreibung.trim();
					bchTermin.setArt(art);
					bchTermin.setBeschreibung(beschreibung);
				}
			} catch (Exception e) {
				bchTermin.setArt("");
				bchTermin.setBeschreibung("");
			}

			// Location
			try {
				bchTermin.setOrt(ce.getLocation().getValue());
			} catch (Exception e) {
				bchTermin.setOrt("");
			}

			// Geo
			try {
				bchTermin.setGeo(ce.getGeographicPos().getValue());
			} catch (Exception e) {
				bchTermin.setGeo("");
			}

			String str = bchTermin.getArtString();
			switch (str) {
			case "PROBE":
				probenTermine.add(bchTermin);
				break;
			case "AUFTRITT":
				auftrittsTermine.add(bchTermin);
				break;
			case "SONSTIGES":
				sonstigeTermine.add(bchTermin);
				break;
			case "":
				sonstigeTermine.add(bchTermin);
				break;
			default:
				sonstigeTermine.add(bchTermin);
				break;
				// do nothing
			}
		}

		// Sortieren der Datenarrays
		Collections.sort(probenTermine);
		Collections.sort(auftrittsTermine);
		Collections.sort(sonstigeTermine);
		this.created = java.util.Calendar.getInstance();
	}

	public ArrayList<BchTermin> getProbenTermine() {
		return probenTermine;
	}

	public void setProbenTermine(ArrayList<BchTermin> probenTermine) {
		this.probenTermine = probenTermine;
	}

	public ArrayList<BchTermin> getAuftrittsTermine() {
		return auftrittsTermine;
	}

	public void setAuftrittsTermine(ArrayList<BchTermin> auftrittsTermine) {
		this.auftrittsTermine = auftrittsTermine;
	}

	public ArrayList<BchTermin> getSonstigeTermine() {
		return sonstigeTermine;
	}

	public void setSonstigeTermine(ArrayList<BchTermin> sonstigeTermine) {
		this.sonstigeTermine = sonstigeTermine;
	}
	
	public java.util.Calendar getCreated(){
		return created;
	}
	
	public java.util.Calendar getComparedTo() {
		return comparedTo;
	}

	public void setComparedTo(java.util.Calendar comparedTo) {
		this.comparedTo = comparedTo;
	}

	public java.util.Calendar getRead() {
		return read;
	}

	public void setRead(java.util.Calendar read) {
		this.read = read;
	}

	public void setCreated(java.util.Calendar created) {
		this.created = created;
	}

	public void printTermine2StdOut() {

		System.out.println(BchTermin.getCSVHeader());
		for (Iterator<BchTermin> probenListe = probenTermine.iterator(); probenListe.hasNext();) {
			BchTermin termin = probenListe.next();
			System.out.println(termin.getCSVValue());
		}
		for (Iterator<BchTermin> auftrittsListe = auftrittsTermine.iterator(); auftrittsListe.hasNext();) {
			BchTermin termin = auftrittsListe.next();
			System.out.println(termin.getCSVValue());
		}
		for (Iterator<BchTermin> sonstigeListe = sonstigeTermine.iterator(); sonstigeListe.hasNext();) {
			BchTermin termin = sonstigeListe.next();
			System.out.println(termin.getCSVValue());
		}
	}
	
	private java.util.Calendar decodeFileName2Calendar (String filename){
		
		java.util.Calendar cal = java.util.Calendar.getInstance(); 
		
		Pattern pattern = Pattern.compile("[0-9]+");
    	Matcher matcher = pattern.matcher(filename);

    	int jahr = 0;
    	int monat = 0;
    	int tag = 0;
    	int stunde = 0;
    	int minute = 0;
    	int sekunde = 0;
    	
    	try {
    		matcher.find();
        	jahr = Integer.parseInt(matcher.group());
        	matcher.find();
        	monat = Integer.parseInt(matcher.group());
        	matcher.find();
        	tag = Integer.parseInt(matcher.group());
        	matcher.find();
        	stunde = Integer.parseInt(matcher.group());
        	matcher.find();
        	minute = Integer.parseInt(matcher.group());
        	matcher.find();
        	sekunde = Integer.parseInt(matcher.group());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problem mit dem Dateinamen des Konzertmeister Kalenders festgestellt.\n"
					+ "Der vorgefundenene Dateiname lautet \"" + filename +"\".\n"
					+ "Bitte stellen Sie sicher, dass der Dateiname dem Muster \"Konzertmeister JJJJ.MM.TT.-HH.MM.SS.ical\" entspricht.\n"
					+ "Das Programm wird jetzt beendet.", "Konzertmeister Kalender2PDF", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
    	
    	cal.set(jahr, monat-1, tag, stunde, minute, sekunde);
		return cal;
	}
}