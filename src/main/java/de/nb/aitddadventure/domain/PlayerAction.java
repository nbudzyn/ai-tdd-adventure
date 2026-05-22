package de.nb.aitddadventure.domain;

/**
 * Fachliche Aktion des Spielers samt sichtbarem Auswahltext.
 */
public enum PlayerAction {
  GO_TO_CLEARING("Geradeaus gehen"),
  GO_TO_FOREST("Zurück in den Wald gehen"),
  RETURN_TO_CLEARING("Zurück auf die Lichtung gehen"),
  RETURN_TO_FOREST("Zurück in den Wald gehen"),
  ENTER_STONE_CIRCLE("In den Steinkreis treten"),
  EXIT_STONE_CIRCLE("Wieder aus dem Steinkreis treten"),
  INSPECT_LARGE_STONE("Einen der großen Steine ansehen"),
  STEP_BACK_FROM_STONE_SURFACE("Von der Steinfläche zurücktreten"),
  TOUCH_STONE_MARKS("Einen Finger in die Kerben legen"),
  PULL_SWORD_FROM_STONE("Das Schwert aus dem Stein ziehen");

  private final String text;

  PlayerAction(String text) {
    this.text = text;
  }

  public String text() {
    return text;
  }
}
