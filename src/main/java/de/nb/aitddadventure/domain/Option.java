package de.nb.aitddadventure.domain;

public record Option(PlayerAction action, String text, Room target) {

  public Room choose() {
    return target;
  }
}
