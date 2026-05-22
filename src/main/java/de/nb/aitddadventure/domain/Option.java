package de.nb.aitddadventure.domain;

import com.google.common.annotations.VisibleForTesting;

/**
 * Auswählbare Spieleraktion mit Zielraum.
 */
public record Option(PlayerAction action, Room target) {

  public Room choose() {
    return target;
  }

  @Override
  @VisibleForTesting
  public Room target() {
    return target;
  }
}
