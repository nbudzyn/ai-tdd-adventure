# Backlog

- Naechster fachlicher Schritt: `Dir das Schwert an den Guertel haengen`
- main() macht zu viel auf einmal und erzeugt im Loop jedes Mal einen neuen Scanner. Das ist unnötig und koppelt Starten, Eingabe
  und Spielschleife eng zusammen.
  Das würde ich glätten, indem ein Scanner einmalig erzeugt wird, die Schleife in eine eigene Runner-Methode oder in ConsoleAdventure
  wandert und der Einstiegspunkt wirklich nur noch Bootstrap macht
- ConsoleAdventure.java: Die Klasse vermischt drei Verantwortlichkeiten:
    - Eingabe lesen
    - Spielzustand weiterbewegen
    - Ausgabe rendern
- Spaeter pruefen, ob der Starttext `Du bist in einem Wald.` als Einstieg dauerhaft passend ist
