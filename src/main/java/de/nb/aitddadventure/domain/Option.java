package de.nb.aitddadventure.domain;

import com.google.common.annotations.VisibleForTesting;

import java.util.function.Supplier;

/**
 * Auswählbare Spieleraktion mit Zielraum.
 */
public final class Option {
  private final PlayerAction action;
  private final Supplier<Room> targetSupplier;

  public Option(PlayerAction action, Room target) {
    this(action, () -> target);
  }

  public Option(PlayerAction action, Supplier<Room> targetSupplier) {
    this.action = action;
    this.targetSupplier = targetSupplier;
  }

  public PlayerAction action() {
    return action;
  }

  public Room choose() {
    return targetSupplier.get();
  }

  @VisibleForTesting
  public Room target() {
    return choose();
  }
}
