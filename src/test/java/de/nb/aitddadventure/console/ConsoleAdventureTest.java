package de.nb.aitddadventure.console;

import static de.nb.aitddadventure.domain.PlayerAction.GO_TO_CLEARING;
import static org.assertj.core.api.Assertions.assertThat;

import de.nb.aitddadventure.domain.TextAdventure;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class ConsoleAdventureTest {

  @Test
  void shouldPrintFirstRoomDescriptionWhenStarting() {
    // Given
    TextAdventure adventure = new TextAdventure();
    ConsoleAdventure consoleAdventure = new ConsoleAdventure(adventure);

    // When
    String output = consoleAdventure.run();

    // Then
    assertThat(output).contains(adventure.start().description());
  }

  @Test
  void shouldPrintAvailableOptionsWhenStarting() {
    // Given
    TextAdventure adventure = new TextAdventure();
    ConsoleAdventure consoleAdventure = new ConsoleAdventure(adventure);

    // When
    String output = consoleAdventure.run();

    // Then
    assertThat(output) //
        .contains(//
            "1", //
            adventure.start().options().getFirst().text());
  }

  @Test
  void shouldMoveToNextRoomWhenChoosingFirstOption() {
    // Given
    TextAdventure adventure = new TextAdventure();
    ConsoleAdventure consoleAdventure = new ConsoleAdventure(adventure);
    StringWriter writer = new StringWriter();
    PrintWriter output = new PrintWriter(writer);
    consoleAdventure.run();

    // When
    consoleAdventure.play(new Scanner(new StringReader("1\n")), output);
    output.flush();

    // Then
    assertThat(writer.toString()).contains(adventure.start().option(GO_TO_CLEARING).target().description());
  }
}
