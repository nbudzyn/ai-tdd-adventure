package de.nb.aitddadventure.console;

import static java.lang.System.lineSeparator;

import de.nb.aitddadventure.domain.Option;
import de.nb.aitddadventure.domain.Room;
import de.nb.aitddadventure.domain.TextAdventure;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Einfache Konsolendarstellung des Adventures.
 */
public class ConsoleAdventure {
  private final TextAdventure adventure;
  private Room currentRoom;

  public ConsoleAdventure(TextAdventure adventure) {
    this.adventure = adventure;
  }

  public String run() {
    currentRoom = adventure.start();
    return renderRoom();
  }

  public boolean isFinished() {
    return currentRoom.options().isEmpty();
  }

  public void play(final Scanner scanner, final PrintWriter output) {
    var i = scanner.nextInt() - 1;
    scanner.nextLine();
    currentRoom = adventure.choose(currentRoom.option(i));

    output.println(renderRoom());
  }

  private String renderRoom() {
    return currentRoom.description() + renderOptions();
  }

  private String renderOptions() {
    if (currentRoom.options().isEmpty()) {
      return "";
    }
    return lineSeparator() + renderOptionLines();
  }

  private String renderOptionLines() {
    final var output = new StringBuilder();
    final var optionCount = currentRoom.options().size();
    for (int i = 0; i < optionCount; i++) {
      if (i > 0) {
        output.append(lineSeparator());
      }
      output.append(renderOptionLine(currentRoom.options().get(i), i));
    }
    return output.toString();
  }

  private String renderOptionLine(Option option, int index) {
    return optionNumber(index) + ". " + option.action().text();
  }

  private int optionNumber(int index) {
    return index + 1;
  }
}
