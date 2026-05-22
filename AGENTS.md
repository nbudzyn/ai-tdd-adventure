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
- Danach stoppen und Review abwarten.

## REFACTOR

- Kein neues Verhalten einführen.
- Code oder Teststruktur nur aufräumen, wenn es sinnvoll ist.
- Danach immer `.\localTest.ps1` ausführen.
- Falls nach einem Fehlschlag mehr Details nötig sind, danach `.\localTest.ps1 -Stacktrace` ausführen.
- Danach stoppen und Review abwarten.

## Fachliche Leitplanken

- Tests sind im Given-When-Then-Stil strukturiert.
- Tests prüfen Fachlichkeit, nicht I/O.
- Optionen sind fachliche Objekte, keine freien Strings.
- User-Auswahl erfolgt aktuell über `option.choose()`.
- Räume liefern Beschreibung und verfügbare Optionen.
- Nach jedem Zwischenschritt Review abwarten.
