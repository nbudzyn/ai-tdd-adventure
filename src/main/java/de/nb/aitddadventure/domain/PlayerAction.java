package de.nb.aitddadventure.domain;

/**
 * Fachliche Aktion des Spielers samt sichtbarem Auswahltext.
 */
public enum PlayerAction {
  GO_TO_CLEARING("Geradeaus gehen"),
  GO_TO_FOREST("Zurück in den Wald gehen"),
  ENTER_STONE_CIRCLE("In den Steinkreis treten"),
  INSPECT_LARGE_STONE("Einen der großen Steine ansehen"),
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
