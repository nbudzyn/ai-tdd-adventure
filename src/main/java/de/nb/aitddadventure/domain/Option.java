package de.nb.aitddadventure.domain;

import com.google.common.annotations.VisibleForTesting;

/**
 * Auswählbare Spieleraktion mit Zielraum.
 */
public final class Option {
  private final PlayerAction action;
  private final RoomLink targetLink;

  public Option(PlayerAction action, Room target) {
    this(action, new RoomLink(target));
  }

  public Option(PlayerAction action, RoomLink targetLink) {
    this.action = action;
    this.targetLink = targetLink;
  }

  public PlayerAction action() {
    return action;
  }

  public Room choose() {
    return targetLink.follow();
  }

  @VisibleForTesting
  public Room target() {
    return choose();
  }
}
