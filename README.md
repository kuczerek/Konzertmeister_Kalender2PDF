# Konzertmeister_Kalender2PDF

#0. Vorbemerkung
Die Datei "Konzertmeister Termine2PDF" ist ein Java Programm, welche unter der Java Version 1.8.0_23 entwickelt und getestet wurde. Eine JRE (Java Laufzeitumgebung) zum Ausführen des Programms kann hier heruntergeladen werden (https://www.java.com/de/download/). Die Installation einer JRE ist Voraussetzung zur Ausführung des Programms. In den meisten Fällen ist auf dem Rechner allerdings bereits eine JRE enthalten. Also zunächst mal versuchen, das Programm zu starten. Wenn es funktioniert ist alles gut, wenn nicht - eine neue JRE installieren.
#1. Wie installiere ich das Programm?
Einfach die Datei "Konzertmeister Termine2PDF.jar" in ein beliebiges Verzeichnis auf der Festplatte kopieren
#2. Was leistet die Software?
Das Programm lädt einen Kalenderexport aus https://konzertmeister.app/de/ auf die eigene Festplatte herunter (a) und erstellt aus diesem Download eine PDF mit einem Terminplan (b). Findet das Programm beim Herunterladen des Kalenderexports bereits einen vorherigen Kalenderexport auf der Festplatte vor, so gleicht es den neuen Download mit dem bereits vorhandenen ab und markiert bei der PDF Erstellung des Terminplans die Änderungen zwischen dem vorherigen und dem aktuellen Kalenderexport (c).
Zu (a)
Beim Herunterladen eines Kalenderexports erzeugt das Programm ein neues Unterverzeichnis mit der Bezeichnung "Konzertmeister Kalenderdateien". Hier werden die heruntergeladenen Konzertmeister Kalenderexporte abgelegt. Die Exporte folgen immer der Namenskonvention: "Konzertmeister JJJJ.MM.TT.-HH.MM.SS.ical". Wird das Programm am 13.02.2020 um 12:05:00 Uhr ausgeführt, so wird also automatisch eine Download-Datei mit dem Dateinamen "Konzertmeister 2020.02.13.-12.05.00.ical" im Unterverzeichnis "Konzertmeister Kalenderdateien" erzeugt.
Zu (b)
Zur Ausgabe des PDF-Teminplans erzeugt das Programm ein Unterverzeichnis mit der Bezeichnung "Erstellte PDF Terminpläne". Die erstellten Terminpläne folgend dabei der Namenskonvention "BCH Teminplan JJJJ.MM-TT.-HH.MM.SS.pdf". Die Zeitangaben zwischen dem heruntergeladenen Kalenderexport und dem PDF Terminplan sind dabei immer gleich. In (a) wurde das Programm am 13.02.2020 um 12:05:00 Uhr ausgeführt, daher trug der Kalenderexport den Dateinamen "Konzertmeister 2020.02.13.-12.05.00.ical". Der jetzt erzeugte Terminplan im Unterverzeichnis "Erstellte PDF Terminpläne" trägt demnach den Dateinamen BCH Teminplan "2020-02.13.-12.05.00.pdf". 
 
Zu (c)
Bevor der Terminplan in (b) erzeugt wird, untersucht das Programm, ob sich im Downloadverzeichnis des Kalenderexports (siehe a) noch ältere Downloads befinden. Ist dies der Fall, ermittelt das Programm immer den letzten Download vor dem gerade aktuellen, und gleicht die Veränderungen vor der Ausgabe des PDF-Terminplans ab. Wird das Programm also am 13.02.2020 um 12:05:00 ausgeführt und im Verzeichnis "Konzertmeister Kalenderdateien" befinden sich noch folgende ältere Kalenderexports aus den vorhergehenden Programmläufen:
"Konzertmeister 2020.02.10.-13.12.33.ical"
"Konzertmeister 2020.02.02.-18.41.56.ical"
"Konzertmeister 2020.01.16.-12.13.03.ical"
"Konzertmeister 2020.01.11.-07.27.51.ical",
dann ermittelt das Programm die Datei "Konzertmeister 2020.02.10.-13.12.33.ical" als jüngsten Download vor dem gerade aktuellen und gleicht die Daten vor Ausgabe des PDF-Terminplans (siehe b) mit den Inhalten des Kalenderexports vom 10.02.2020 um 13:12:33 Uhr für eine Änderungsmarkierung ab.
#3. Wie starte ich das Programm?
Einfach durch einen Doppelklick auf die Datei "Konzertmeister Termine2PDF.jar". Es sind keine weiteren Benutzereingaben erforderlich.
#4. Was mache ich mit den alten Downloads und erstellten Terminplänen in den Verzeichnissen "Konzertmeister Kalenderdateien" und "Erstellte PDF Terminpläne"
Eigentlich nix. Die erstellten Terminpläne in Form von PDF Dateien können jederzeit auch wieder gelöscht werden. Gleiches gilt für die heruntergeladenen Kalenderexports. Werden alle Kalenderexports gelöscht und das Programm findet keine vorherigen Downloads, so wird einfach ein ganz normaler PDF-Terminplan erzeugt ohne Änderungsmarkierungen in Bezug auf einen vorherigen Download.
#5. Wie kann ich forcieren, dass die Änderungsmarkierungen gegenüber einem anderen Zeitpunkt durchgeführt werden?
Eigentlich ganz einfach! Man muss dafür sorgen, dass der Zeitpunkt zu dem die Änderungsmarkierungen ermittelt werden sollen, einfach die jüngste Datei im Verzeichnis "Konzertmeister Kalenderdateien" ist. 
Soll bei der Programmausführung am 13.02.2020 um 12:05:00 nicht wie im Beispiel gegen den Kalenderexport vom 10.02.2020 um 13:12:33 Uhr (siehe c), sondern gegen den vom 16.01.2020 um 12:13:03 Uhr abgeglichen werden, so müssen vor der Programmausführung einfach die Dateien:
"Konzertmeister 2020.02.10.-13.12.33.ical"
"Konzertmeister 2020.02.02.-18.41.56.ical"
aus dem Verzeichnis "Konzertmeister Kalenderdateien" entfernt werden. Der letzte Download vor dem gerade aktuellen ist dann der Kalenderexport vom 16.01.2020 um 12:13:03 Uhr, welcher für die Änderungsmarkierung herangezogen wird.
