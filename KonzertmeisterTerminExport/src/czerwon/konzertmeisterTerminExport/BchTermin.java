package czerwon.konzertmeisterTerminExport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.fortuna.ical4j.model.Date;
import java.util.Calendar;

public class BchTermin implements Comparable<BchTermin> {
	
	private Date start;
	private Date ende;
	private String simpleDate;
	private String simpleTime;
	private String titel;
	private String beschreibung;
	private String art;
	private String geo;
	private String uid;
	private String ort;
	
	private boolean isNew;
	private boolean isDeleted;
	private boolean isUpdated;
	private boolean isStartUpdated;
	private boolean isSimpleDateUpdated;
	private boolean isSimpleTimeUpdated;
	private boolean isEndeUpdated;
	private boolean isTitelUpdated;
	private boolean isBeschreibungUpdated;
	private boolean isArtUpdated;
	private boolean isGeoUpdated;
	private boolean isOrtUpdated;
	
	
	public BchTermin () {
		this.start = new Date();
		this.ende = new Date();
		this.titel = "";
		this.beschreibung = "";
		this.art = "";
		this.geo = "";
		this.uid = "";
		this.ort = "";
		this.isNew = false;
		this.isDeleted = false;
		this.isUpdated = false;
		this.isStartUpdated = false;
		this.isEndeUpdated = false;
		this.isTitelUpdated = false;
		this.isBeschreibungUpdated = false;
		this.isArtUpdated = false;
		this.isGeoUpdated = false;
		this.isOrtUpdated = false;
		
	}
	
	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isUpdated() {
		return isUpdated;
	}

	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	public boolean isStartUpdated() {
		return isStartUpdated;
	}

	public void setStartUpdated(boolean isStartUpdated) {
		this.isStartUpdated = isStartUpdated;
	}

	public boolean isEndeUpdated() {
		return isEndeUpdated;
	}

	public void setEndeUpdated(boolean isEndeUpdated) {
		this.isEndeUpdated = isEndeUpdated;
	}

	public boolean isSimpleDateUpdated() {
		return isSimpleDateUpdated;
	}

	public boolean isSimpleTimeUpdated() {
		return isSimpleTimeUpdated;
	}

	public void setSimpleDateUpdated(boolean isSimpleDateUpdated) {
		this.isSimpleDateUpdated = isSimpleDateUpdated;
	}

	public void setSimpleTimeUpdated(boolean isSimpleTimeUpdated) {
		this.isSimpleTimeUpdated = isSimpleTimeUpdated;
	}
	
	public boolean isTitelUpdated() {
		return isTitelUpdated;
	}

	public void setTitelUpdated(boolean isTitelUpdated) {
		this.isTitelUpdated = isTitelUpdated;
	}

	public boolean isBeschreibungUpdated() {
		return isBeschreibungUpdated;
	}

	public void setBeschreibungUpdated(boolean isBeschreibungUpdated) {
		this.isBeschreibungUpdated = isBeschreibungUpdated;
	}

	public boolean isArtUpdated() {
		return isArtUpdated;
	}

	public void setArtUpdated(boolean isArtUpdated) {
		this.isArtUpdated = isArtUpdated;
	}

	public boolean isGeoUpdated() {
		return isGeoUpdated;
	}

	public void setGeoUpdated(boolean isGeoUpdated) {
		this.isGeoUpdated = isGeoUpdated;
	}

	public boolean isOrtUpdated() {
		return isOrtUpdated;
	}

	public void setOrtUpdated(boolean isOrtUpdated) {
		this.isOrtUpdated = isOrtUpdated;
	}

	public Date getStartDate() {
		return this.start;
	}
	
	public String getStartString() {
		String startString = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(start);
		return startString;
	}
	
	public String getSimpleDateString() {
		return this.simpleDate;
	}
	
	public String getSimpleTimeString() {
		return this.simpleTime;
	}

	public void setStart(Date start) {
		this.start = start;
		
		String dayOfWeek;
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		
		switch (cal.get(Calendar.DAY_OF_WEEK)){
			case Calendar.MONDAY:
				dayOfWeek = "Mo.";
				break;
			case Calendar.TUESDAY:
				dayOfWeek = "Di.";
				break;
			case Calendar.WEDNESDAY:
				dayOfWeek = "Mi.";
				break;
			case Calendar.THURSDAY:
				dayOfWeek = "Do.";
				break;
			case Calendar.FRIDAY:
				dayOfWeek = "Fr.";
				break;
			case Calendar.SATURDAY:
				dayOfWeek = "Sa.";
				break;
			case Calendar.SUNDAY:
				dayOfWeek = "So.";
				break;
			default: 
				dayOfWeek = "";
		}
		
		SimpleDateFormat formatterDate = new SimpleDateFormat("dd.MM.yyyy");
		this.simpleDate= dayOfWeek + ", " + formatterDate.format(cal.getTime());
		
		if (this.ende != null) {
			Calendar startCal = Calendar.getInstance();
			Calendar endeCal = Calendar.getInstance();
			
			startCal.setTime(this.start);
			endeCal.setTime(this.ende);
			SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
			this.simpleTime = formatterTime.format(startCal.getTime()) + " - " + formatterTime.format(endeCal.getTime());
		}
	}

	public Date getEndeDate() {
		return this.ende;
	}
	
	public String getEndeString() {
		
		String endeString = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(ende);
		return endeString;
	}

	public void setEnde(Date ende) {
		this.ende = ende;
		
		if (this.start != null) {
			Calendar startCal = Calendar.getInstance();
			Calendar endeCal = Calendar.getInstance();
			
			startCal.setTime(this.start);
			endeCal.setTime(this.ende);
			SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
			this.simpleTime = formatterTime.format(startCal.getTime()) + " - " + formatterTime.format(endeCal.getTime());
		}
	}

	public String getTitelString() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getBeschreibungString() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getArtString() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}
	
	public String getGeoString() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public String getUidString() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOrtString() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getCSVValue() {
		return uid + ";" + start + ";" + ende + ";" + art + ";" + titel + ";" + beschreibung + ";" + ort + ";" + geo;
	}
	
	public static String getCSVHeader() {
		return "UID;Start;Ende;Art;Titel;Beschreibung;Ort;GEO";
	}

	@Override
	public int compareTo(BchTermin compTermin) {
		int erg = start.compareTo(compTermin.getStartDate()); 
		return erg; 
	} 
}
