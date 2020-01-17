package kuczerek.konzertmeisterTerminExport;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.io.image.*;

import java.io.File;
import java.net.URL;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JOptionPane;

public class ExportPdf {

	private PdfDocument pdfDocument; 
	private Terminplan alleTermine;
	
	public ExportPdf(String filename, Terminplan terminplan) {

		this.alleTermine = terminplan;
		try {
			pdfDocument = new PdfDocument(new PdfWriter(filename));	
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
		pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
	}
	
	public ExportPdf(Terminplan terminplan) {

		this.alleTermine = terminplan;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.-HH.mm.ss");
		String filename = "BCH Terminplan " + formatter.format(terminplan.getRead().getTime()) + ".pdf";
		
		//Verzeichnis ggf. erstellen
		Path filePath = Paths.get(Properties.REL_PATH_PDF + filename);
	     File folder = new File(Properties.REL_PATH_PDF);
	     if(!folder.exists()) {
	    	 try {
	    		 Files.createDirectory(filePath.getParent());
	    	 } catch (IOException e) {
	    		 e.printStackTrace();
	    	 }
	     }
		
		try {
			pdfDocument = new PdfDocument(new PdfWriter(Properties.REL_PATH_PDF + filename));	
		} catch (IOException ioe){
			//ioe.printStackTrace();
			JOptionPane.showMessageDialog(null, "Die PDF Datei "+filename+" kann nicht geschrieben werden, da sie von einem anderen Programm geöffnet ist.\n"
					+ "Bitte die Datei schließen!\n" 
					+ "Das Programm wird beendet!", "Konzertmeister Kalender2PDF", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
	}
	
	public void printPdf() {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String planVon = formatter.format(alleTermine.getRead().getTime());
		String aenderungenSeit = formatter.format(alleTermine.getComparedTo().getTime());
		//tablesize Array für fünf Spalten mit Ort
		//float [] tablesize = new float[] { 9, 8, 30, 30, 30 };
		//tablesize Array für vier Spalten mit Ort
		float [] tablesize = new float[] { 6, 5, 30, 30 };
				
		try (Document document = new Document(pdfDocument)) {
			
			Table table;
			UnitValue uv = new UnitValue(UnitValue.PERCENT, 100);
			
			//TitelTabelle
			table = new Table(new float[] { 4, 1 });
			table.setWidth(uv);
			table.setFixedLayout();
			
			//Überschrift
			Cell cell = new Cell(1, 1)
					.setTextAlignment(TextAlignment.LEFT)
					.setMargin(5)
					.setBorder(Border.NO_BORDER);
			Paragraph par = new Paragraph("Terminplan des Bläsercorps Hackenstedt e. V.")
					.setFontSize(20);
			cell.add(par);
			par = new Paragraph("Stand des Terminplans vom " + planVon + " mit Änderungen seit dem " + aenderungenSeit + ".")
					.setFontSize(10);
			cell.add(par);
			table.addCell(cell);
			
			//Header Wappen
			cell = new Cell(1, 1)
					.setBaseDirection(BaseDirection.RIGHT_TO_LEFT)
					.setBorder(Border.NO_BORDER);
			try {
				URL fileURL = this.getClass().getResource("/ressources/bch_wappen.png");
				ImageData imageData = ImageDataFactory.create(fileURL);
				Image pdfImg = new Image(imageData);
				pdfImg
					.scaleToFit(60, 60)
					.setHorizontalAlignment(HorizontalAlignment.RIGHT);
				cell.add(pdfImg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			table.addCell(cell);
			document.add(table);		 
			
			//Probentabelle
			Paragraph ueberschrift;
			ueberschrift = new Paragraph("Probentermine");
			document.add(ueberschrift);
			table = new Table(tablesize);
			table.setWidth(uv);
			table.setFixedLayout();
			createTableHeader(table);
			createTableContent(table, alleTermine.getProbenTermine());
			document.add(table);

			//Auftrittstabelle
			ueberschrift = new Paragraph("Auftrittstermine");
			document.add(ueberschrift);
			table = new Table(tablesize);
			table.setWidth(uv);
			table.setFixedLayout();
			createTableHeader(table);
			createTableContent(table, alleTermine.getAuftrittsTermine());
			document.add(table);
			
			//Sonstige Termine
			ueberschrift = new Paragraph("Sonstige Termine");
			document.add(ueberschrift);
			table = new Table(tablesize);
			table.setWidth(uv);
			table.setFixedLayout();
			createTableHeader(table);
			createTableContent(table, alleTermine.getSonstigeTermine());
			document.add(table);
		}		
	}
	
	private void createTableHeader(Table table) {
		
		int fontSize = 11;
		
		Cell cell = new Cell(1, 1)
				.setBackgroundColor(ColorConstants.LIGHT_GRAY)
				.setMargin(5);
		Paragraph par = new Paragraph("Datum")
			.setFontSize(fontSize);
		cell.add(par);
		table.addHeaderCell(cell);
		cell = new Cell(1, 1)
				.setBackgroundColor(ColorConstants.LIGHT_GRAY)
				.setMargin(5);
		par = new Paragraph("Uhrzeit")
				.setFontSize(fontSize);
		cell.add(par);
		table.addHeaderCell(cell);
		cell = new Cell(1, 1)
				.setBackgroundColor(ColorConstants.LIGHT_GRAY)
				.setMargin(5);
		par = new Paragraph("Titel")
				.setFontSize(fontSize);
		cell.add(par);
		table.addHeaderCell(cell);
		cell = new Cell(1, 1)
				.setBackgroundColor(ColorConstants.LIGHT_GRAY)
				.setMargin(5);
		par = new Paragraph("Beschreibung")
				.setFontSize(fontSize);
		cell.add(par);
		table.addHeaderCell(cell);			
//		cell = new Cell(1, 1)
//				.setBackgroundColor(ColorConstants.LIGHT_GRAY)
//				.setMargin(5);
//		par = new Paragraph("Ort")
//				.setFontSize(fontSize);
//		cell.add(par);
//		table.addHeaderCell(cell);	
	}
	
	private void createTableContent(Table table, ArrayList<BchTermin> termine) {
		
		BchTermin termin;
		Cell cell;
		Paragraph par;
		float fontSize = 8;
			
		for (Iterator<BchTermin> termineListe = termine.iterator(); termineListe.hasNext(); ){
			termin = termineListe.next();
			
			//Termine überspringen, die in der Vergangenheit liegen:
			Calendar now = Calendar.getInstance();
			if (termin.getStartDate().before(now.getTime())){
				continue;
			}

			//Start Zelle
			par = new Paragraph(termin.getSimpleDateString())
					.setFontSize(fontSize);
			if (termin.isDeleted()) {
				par.setLineThrough();
			}
			cell = new Cell(1, 1)
					.setTextAlignment(TextAlignment.LEFT)
					.add(par);
			if (termin.isNew() || termin.isDeleted() || termin.isSimpleDateUpdated())  {
				cell.setBackgroundColor(ColorConstants.YELLOW);
			} else {
				cell.setBackgroundColor(ColorConstants.WHITE);	
			}
			table.addCell(cell);
			
			//Ende Zelle
			par = new Paragraph(termin.getSimpleTimeString())
					.setFontSize(fontSize);
			if (termin.isDeleted()) {
				par.setLineThrough();
			}
			cell = new Cell(1, 1)
					.setTextAlignment(TextAlignment.LEFT)
					.add(par);
			if (termin.isNew() || termin.isDeleted() || termin.isSimpleTimeUpdated())  {
				cell.setBackgroundColor(ColorConstants.YELLOW);
			} else {
				cell.setBackgroundColor(ColorConstants.WHITE);	
			}
			table.addCell(cell);
			
			//Titel Zelle
			par = new Paragraph(termin.getTitelString())
					.setFontSize(fontSize);
			if (termin.isDeleted()) {
				par.setLineThrough();
			}
			cell = new Cell(1, 1)
					.setTextAlignment(TextAlignment.LEFT)
					.add(par);
			if (termin.isNew() || termin.isDeleted() || termin.isTitelUpdated())  {
				cell.setBackgroundColor(ColorConstants.YELLOW);
			} else {
				cell.setBackgroundColor(ColorConstants.WHITE);	
			}
			table.addCell(cell);

			//Beschreibung Zelle
			par = new Paragraph(termin.getBeschreibungString())
					.setFontSize(fontSize);
			if (termin.isDeleted()) {
				par.setLineThrough();
			}
			cell = new Cell(1, 1)
					.setTextAlignment(TextAlignment.LEFT)
					.add(par);
			if (termin.isNew() || termin.isDeleted() || termin.isBeschreibungUpdated())  {
				cell.setBackgroundColor(ColorConstants.YELLOW);
			} else {
				cell.setBackgroundColor(ColorConstants.WHITE);	
			}
			table.addCell(cell);
			
//			//Ort Zelle
//			par = new Paragraph(termin.getOrtString())
//					.setFontSize(fontSize);
//			if (termin.isDeleted()) {
//				par.setLineThrough();
//			}
//			cell = new Cell(1, 1)
//					.setTextAlignment(TextAlignment.LEFT)
//					.add(par);
//			if (termin.isNew() || termin.isDeleted() || termin.isOrtUpdated())  {
//				cell.setBackgroundColor(ColorConstants.YELLOW);
//			} else {
//				cell.setBackgroundColor(ColorConstants.WHITE);	
//			}
//			table.addCell(cell);	
		}
	}	
}