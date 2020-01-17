package kuczerek.konzertmeisterTerminExport;

import java.util.*;

public class TerminplanVergleich {

	private Terminplan verglichenerPlan;

	public TerminplanVergleich (Terminplan alterPlan, Terminplan neuerPlan) {
		
		this.verglichenerPlan = new Terminplan();
		
		this.verglichenerPlan.setCreated(neuerPlan.getCreated());
		this.verglichenerPlan.setRead(neuerPlan.getRead());
		this.verglichenerPlan.setComparedTo(alterPlan.getRead());
		
		ArrayList<BchTermin> einzelPlan;
		einzelPlan = einzelPlanVergleich(alterPlan.getProbenTermine(), neuerPlan.getProbenTermine());
		this.verglichenerPlan.setProbenTermine(einzelPlan);
		einzelPlan = einzelPlanVergleich(alterPlan.getAuftrittsTermine(), neuerPlan.getAuftrittsTermine());
		this.verglichenerPlan.setAuftrittsTermine(einzelPlan);
		einzelPlan = einzelPlanVergleich(alterPlan.getSonstigeTermine(), neuerPlan.getSonstigeTermine());
		this.verglichenerPlan.setSonstigeTermine(einzelPlan);
		
		
	}
	
	public Terminplan getVerglichenenTerminplan () {
		return this.verglichenerPlan;
	}
	
	private ArrayList<BchTermin> einzelPlanVergleich (ArrayList<BchTermin> einzelPlanAlt, ArrayList<BchTermin> einzelPlanNeu) {

		ArrayList<BchTermin> einzelPlanVerglichen = new ArrayList<>(); 
		HashMap<String, Boolean> istDerTerminNeu = new HashMap<String, Boolean>(); 


		BchTermin terminNeu;
		BchTermin terminAlt;
		boolean foundTermin;

		/*
		 * Finden wir erstmal neue Termine:
		 * Iterieren über den neuen Einzelplan und im dazugehörigen alten Einzelplan korrespondierende Startdatümer suchen
		 * Werden neue Termin gefunden --> kennzeichnen und ab in den "Verglichen-Array" 
		 */
		terminNeu = null;
		terminAlt = null;
		foundTermin = false;
		for (Iterator<BchTermin> einzelPlanNeuListe = einzelPlanNeu.iterator(); einzelPlanNeuListe.hasNext(); ) {
			terminNeu = einzelPlanNeuListe.next();
			terminAlt = null;
			foundTermin = false;

			//Den alten Termin zu dem aktuellen finden, um das Delta zu ermitteln.
			Iterator<BchTermin> einzelPlanAltListe = einzelPlanAlt.iterator();
			while (einzelPlanAltListe.hasNext()) {
				terminAlt = einzelPlanAltListe.next();
				if (terminNeu.getStartString().equals(terminAlt.getStartString())) {
					//Zu dem neuen Termin wurde ein alter gefunden, ist also nicht Neu erstellt --> Weiter mit dem nächsten neuen Termin
					foundTermin = true;
					break;
				}
			}
			if (!foundTermin) {
				//Aha, es wurde kein korrospondierender Termin gefunden. Dieser muss also neu sein!
				terminNeu.setNew(true);	
				einzelPlanVerglichen.add(terminNeu);
				
				//wir merken uns diesen Termin, damit wir den Datensatz später beim Abgleich der Einzelfelder übersringen können. 
				istDerTerminNeu.put(terminNeu.getStartString(), new Boolean(true));
			} else {
				istDerTerminNeu.put(terminNeu.getStartString(), new Boolean(false));
			}
		}
		
		/*
		 * Finden wir gelöschte Termine:
		 * Das gleich nochmal, nur andersrum... 
		 */
		terminNeu = null;
		terminAlt = null;
		foundTermin = false;
		for (Iterator<BchTermin> einzelPlanAltListe = einzelPlanAlt.iterator(); einzelPlanAltListe.hasNext(); ) {
			terminNeu = null;
			terminAlt = einzelPlanAltListe.next();
			foundTermin = false;

			//Den neuenTermin zu dem aktuellen finden, um das Delta zu ermitteln.
			Iterator<BchTermin> einzelPlanNeuListe = einzelPlanNeu.iterator();
			while (einzelPlanNeuListe.hasNext()) {
				terminNeu = einzelPlanNeuListe.next();
				if (terminAlt.getStartString().equals(terminNeu.getStartString())) {
					//Zu dem alten Termin wurde ein neuer gefunden, ist also nicht gelöscht --> Weiter mit dem nächsten neuen Termin
					foundTermin = true;
					break;
				}
			}
			if (!foundTermin) {
				//Aha, es wurde kein korrospondierender Termin gefunden. Dieser muss also gelöscht sein!
				terminAlt.setDeleted(true);
				einzelPlanVerglichen.add(terminAlt);
			}
		}
		
		/*
		 * Jetzt noch die übrig gebliebenen Termine vergleichen, ob sich eizelne Felder geändert haben
		 */
		terminNeu = null;
		terminAlt = null;
		for (Iterator<BchTermin> einzelPlanNeuListe = einzelPlanNeu.iterator(); einzelPlanNeuListe.hasNext(); ) {
			terminNeu = einzelPlanNeuListe.next();
			terminAlt = null;
			
			//Termine die schon als komplett neu gekennzeichnet sind brauchen wir nicht mehr im Detail anschauen.
			if (istDerTerminNeu.get(terminNeu.getStartString())) {
				continue;
			}

			//Den alten Termin zu dem aktuellen finden, um das Delta zu ermitteln.
			Iterator<BchTermin> einzelPlanAltListe = einzelPlanAlt.iterator();
			while (einzelPlanAltListe.hasNext()) {
				terminAlt = einzelPlanAltListe.next();
				if (terminNeu.getStartString().equals(terminAlt.getStartString())) {
					//Zu dem neuen Termin wurde der alte gefunden, also Felder vergeleichen, markieren und ab in den dritten Array!
					terminNeu.setUpdated(true);
					if (!terminNeu.getStartString().equals(terminAlt.getStartString())){
						terminNeu.setStartUpdated(true);
					}
					if (!terminNeu.getEndeString().equals(terminAlt.getEndeString())){
						terminNeu.setEndeUpdated(true);
					}
					if (!terminNeu.getSimpleDateString().equals(terminAlt.getSimpleDateString())){
						terminNeu.setSimpleDateUpdated(true);
					}
					if (!terminNeu.getSimpleTimeString().equals(terminAlt.getSimpleTimeString())){
						terminNeu.setSimpleTimeUpdated(true);
					}
					if (!terminNeu.getTitelString().equals(terminAlt.getTitelString())) {
						terminNeu.setTitelUpdated(true);
					}
					if (!terminNeu.getBeschreibungString().equals(terminAlt.getBeschreibungString())) {
						terminNeu.setBeschreibungUpdated(true);
					}
					if (!terminNeu.getOrtString().equals(terminAlt.getOrtString())) {
						terminNeu.setOrtUpdated(true);
					}
					terminNeu.setUpdated(true);
					einzelPlanVerglichen.add(terminNeu);
					break;
				}
			}
		}
		
		Collections.sort(einzelPlanVerglichen);
		return einzelPlanVerglichen;
	}

}
