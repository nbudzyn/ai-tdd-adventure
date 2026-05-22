# Projektregeln

Wir entwickeln strikt testgetrieben in kleinen Zyklen.

## RED

- Nur den nächsten fachlichen Test schreiben oder anpassen.
- Danach immer `.\gradlew.bat -q localTest` ausführen.
- Der Test muss fehlschlagen oder nicht kompilieren.
- Danach stoppen und Review abwarten.

## GREEN

- Nur Produktivcode ändern.
- Keine Tests ändern.
- Minimal implementieren, sodass der rote Test grün wird.
- Danach immer `.\gradlew.bat -q localTest` ausführen.
- Danach stoppen und Review abwarten.

## REFACTOR

- Kein neues Verhalten einführen.
- Code oder Teststruktur nur aufräumen, wenn es sinnvoll ist.
- Danach immer `.\gradlew.bat -q localTest` ausführen.
- Danach stoppen und Review abwarten.

## Fachliche Leitplanken

- Tests sind im Given-When-Then-Stil strukturiert.
- Tests prüfen Fachlichkeit, nicht I/O.
- Optionen sind fachliche Objekte, keine freien Strings.
- User-Auswahl erfolgt aktuell über `option.choose()`.
- Räume liefern Beschreibung und verfügbare Optionen.
- Nach jedem Zwischenschritt Review abwarten.
