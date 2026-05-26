package de.nb.aitddadventure.domain;

import com.google.common.annotations.VisibleForTesting;

import java.util.List;

/**
 * Ort mit besuchsabhängiger Beschreibung und Handlungsoptionen.
 */
public final class Room {
  private final String firstDescription;
  private final String repeatedDescription;
  private final List<Option> firstOptions;
  private final List<Option> repeatedOptions;
  private int visitCount;

  private Room(String firstDescription, String repeatedDescription, List<Option> firstOptions, List<Option> repeatedOptions) {
    this.firstDescription = firstDescription;
    this.repeatedDescription = repeatedDescription;
    this.firstOptions = List.copyOf(firstOptions);
    this.repeatedOptions = List.copyOf(repeatedOptions);
  }

  public static Room of(String description, Option... options) {
    return new Room(description, description, List.of(options), List.of(options));
  }

  public static Room of(String firstDescription, String repeatedDescription, List<Option> firstOptions, List<Option> repeatedOptions) {
    return new Room(firstDescription, repeatedDescription, firstOptions, repeatedOptions);
  }

  public String description() {
    return visitCount <= 1 ? firstDescription : repeatedDescription;
  }

  public List<Option> options() {
    return visitCount <= 1 ? firstOptions : repeatedOptions;
  }

  public Option option(int i) {
    return options().get(i);
  }

  public void enter() {
    visitCount++;
  }

  @VisibleForTesting
  public Option option(PlayerAction action) {
    return options()
        .stream()
        .filter(it -> it.action() == action)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Action is not available in this room: " + action));
  }
}
