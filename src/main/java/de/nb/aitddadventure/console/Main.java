package de.nb.aitddadventure.console;

import de.nb.aitddadventure.domain.TextAdventure;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Einstiegspunkt der Konsolenanwendung des Adventures.
 */
public class Main {

  @SuppressWarnings("java:S106")
  static void main() {
    ConsoleAdventure consoleAdventure = new ConsoleAdventure(new TextAdventure());
    System.out.println(consoleAdventure.run());

    var output = new PrintWriter(System.out);

    while (!consoleAdventure.isFinished()) {
      consoleAdventure.play(new Scanner(System.in), output);
      output.flush();
    }
  }
}
