# Projektregeln

Wir entwickeln strikt testgetrieben in kleinen Zyklen.

- Der Standard-Testlauf erfolgt immer über `.\localTest.ps1`.
- Ein direkter Gradle-Testlauf über die Task `test` darf nur nach Rückfrage beim User durchgeführt werden.

## RED

- Nur den nächsten fachlichen Test schreiben oder anpassen.
- Danach immer `.\localTest.ps1` ausführen.
- Falls nach einem Fehlschlag mehr Details nötig sind, danach `.\localTest.ps1 -Stacktrace` ausführen.
- Der Test muss fehlschlagen oder nicht kompilieren.
- Danach stoppen und Review abwarten.

## GREEN

- Nur Produktivcode ändern.
- Keine Tests ändern.
- Minimal implementieren, sodass der rote Test grün wird.
- Danach immer `.\localTest.ps1` ausführen.
- Falls nach einem Fehlschlag mehr Details nötig sind, danach `.\localTest.ps1 -Stacktrace` ausführen.
- Nach GREEN wird die umgesetzte Funktion aus dem Backlog gelöscht.
- Danach stoppen und Review abwarten.

## REFACTOR

- Kein neues Verhalten einführen.
- Code oder Teststruktur nur aufräumen, wenn es sinnvoll ist.
- Danach immer `.\localTest.ps1` ausführen.
- Falls nach einem Fehlschlag mehr Details nötig sind, danach `.\localTest.ps1 -Stacktrace` ausführen.
- Nach REFACTOR prüfen, ob sich daraus eine neue oder geschärfte Projektregel ableiten lässt, und diese gegebenenfalls vorschlagen.
- Danach stoppen und Review abwarten.

## Regeln zu Architektur und Vorgehen

- Tests sind im Given-When-Then-Stil strukturiert.
- Tests prüfen Fachlichkeit, nicht I/O.
- User-Auswahl erfolgt über `option.choose()`.
- Räume liefern Beschreibung und verfügbare Optionen.
- Jede Produktivklasse trägt einen deutschen Klassenkommentar.
- Klassenkommentare im Produktivcode sind knappe Nominalphrasen, die ein Objekt beschreiben.
- Produktivmethoden, die aktuell nur aus Tests verwendet werden, tragen `@VisibleForTesting` von Google.
- Tests enthalten keine Klassen- oder Methodenkopfkommentare.
- Methoden innerhalb einer Datei sind so sortiert, dass Aufrufe von oben nach unten verlaufen.
- `var` wird verwendet, wenn der konkrete Typ aus Initialisierung und Kontext unmittelbar klar ist; explizite Typen bleiben, wenn sie das
  Verständnis verbessern.
- Nach jedem Zwischenschritt Review abwarten.

## Fachliche Leitplanken

- Neue Raumtexte werden so formuliert, dass sie natürlich an die auslösende Bewegung oder Handlung anschließen.

