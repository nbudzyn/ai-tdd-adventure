package de.nb.aitddadventure.domain;

import com.google.common.annotations.VisibleForTesting;

import java.util.List;

/**
 * Ort mit Beschreibung und verfügbaren Handlungsoptionen.
 */
public record Room(String description, List<Option> options) {

  public Room(String description, Option... options) {
    this(description, List.of(options));
  }

  public Room {
    options = List.copyOf(options);
  }

  public Option option(int i) {
    return options.get(i);
  }

  @VisibleForTesting
  public Option option(PlayerAction action) {
    return options
        .stream()
        .filter(it -> it.action() == action)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Action is not available in this room: " + action));
  }
}
