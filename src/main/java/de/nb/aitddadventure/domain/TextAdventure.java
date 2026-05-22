package de.nb.aitddadventure.domain;

import static de.nb.aitddadventure.domain.PlayerAction.ENTER_STONE_CIRCLE;
import static de.nb.aitddadventure.domain.PlayerAction.GO_TO_CLEARING;
import static de.nb.aitddadventure.domain.PlayerAction.GO_TO_FOREST;
import static de.nb.aitddadventure.domain.PlayerAction.INSPECT_LARGE_STONE;
import static de.nb.aitddadventure.domain.PlayerAction.TOUCH_STONE_MARKS;

public class TextAdventure {
  public Room start() {
    return createWorld();
  }

  private Room createWorld() {
    Room swordInStone = createSwordInStone();
    Room stoneCircle = createStoneCircle();
    Room strangeFeeling = createStrangeFeeling(swordInStone);
    Room largeStone = createLargeStone(strangeFeeling);
    Room returnedClearing = createReturnedClearing(stoneCircle, largeStone);
    Room returnedForest = createReturnedForest(returnedClearing);
    Room clearing = createClearing(stoneCircle, largeStone, returnedForest);
    return createStartForest(clearing);
  }

  private Room createStartForest(Room clearing) {
    return new Room("Du stehst in einem Wald.", new Option(GO_TO_CLEARING, "Geradeaus gehen", clearing));
  }

  private Room createClearing(Room stoneCircle, Room largeStone, Room returnedForest) {
    return new Room("Du stehst auf einer Lichtung, in der Mitte ein Steinkreis.",
        // TODO Duplizierten Text ("In den Steinkreis treten") extrahieren.
        // - Vielleicht Sollte der Text teil der PlayerAction werden?
        new Option(ENTER_STONE_CIRCLE, "In den Steinkreis treten", stoneCircle),
        new Option(INSPECT_LARGE_STONE, "Einen der großen Steine ansehen", largeStone),
        new Option(GO_TO_FOREST, "Zurück in den Wald gehen", returnedForest));
  }

  private Room createReturnedForest(Room returnedClearing) {
    return new Room("Du stehst wieder im Wald.", new Option(GO_TO_CLEARING, "Auf die Lichtung gehen", returnedClearing));
  }

  private Room createReturnedClearing(Room stoneCircle, Room largeStone) {
    return new Room("Du betrittst wieder die Lichtung, in deren Mitte der Steinkreis steht.",
        new Option(ENTER_STONE_CIRCLE, "In den Steinkreis treten", stoneCircle),
        new Option(INSPECT_LARGE_STONE, "Einen der großen Steine ansehen", largeStone));
  }

  private Room createLargeStone(Room strangeFeeling) {
    return new Room("In der Steinfläche entdeckst du frische Kerben, die nicht vom Wetter stammen.",
        new Option(TOUCH_STONE_MARKS, "Einen Finger in die Kerben legen", strangeFeeling));
  }

  private Room createStrangeFeeling(Room swordInStone) {
    return new Room("Als dein Finger in den Kerben liegt, spürst du aus dem Kreis einen Luftzug.",
        new Option(ENTER_STONE_CIRCLE, "In den Steinkreis treten", swordInStone));
  }

  private Room createStoneCircle() {
    return new Room("Zwischen den alten Steinen fühlst du dich unwohl.");
  }

  private Room createSwordInStone() {
    return new Room("Mitten im Steinkreis fällt dir ein großer Block auf - war der vorher schon da? In dem Block steckt ein Schwert.");
  }
}
