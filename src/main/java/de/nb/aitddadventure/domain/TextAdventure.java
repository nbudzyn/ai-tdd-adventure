package de.nb.aitddadventure.domain;

import static de.nb.aitddadventure.domain.PlayerAction.ENTER_STONE_CIRCLE;
import static de.nb.aitddadventure.domain.PlayerAction.GO_TO_CLEARING;
import static de.nb.aitddadventure.domain.PlayerAction.GO_TO_FOREST;
import static de.nb.aitddadventure.domain.PlayerAction.INSPECT_LARGE_STONE;
import static de.nb.aitddadventure.domain.PlayerAction.PULL_SWORD_FROM_STONE;
import static de.nb.aitddadventure.domain.PlayerAction.TOUCH_STONE_MARKS;

/**
 * Aufbau der Welt des Textadventures mit ihrem Startpunkt.
 */
public class TextAdventure {
  public Room start() {
    return createWorld();
  }

  private Room createWorld() {
    var pulledSword = createPulledSword();
    var swordInStone = createSwordInStone(pulledSword);
    var stoneCircle = createStoneCircle();
    var strangeFeeling = createStrangeFeeling(swordInStone);
    var largeStone = createLargeStone(strangeFeeling);
    var returnedClearing = createReturnedClearing(stoneCircle, largeStone);
    var returnedForest = createReturnedForest(returnedClearing);
    var clearing = createClearing(stoneCircle, largeStone, returnedForest);
    return createStartForest(clearing);
  }

  private Room createStartForest(Room clearing) {
    return new Room("Du stehst in einem Wald.", new Option(GO_TO_CLEARING, clearing));
  }

  private Room createClearing(Room stoneCircle, Room largeStone, Room returnedForest) {
    return new Room("Du stehst auf einer Lichtung, in der Mitte ein Steinkreis.", new Option(ENTER_STONE_CIRCLE, stoneCircle),
        new Option(INSPECT_LARGE_STONE, largeStone), new Option(GO_TO_FOREST, returnedForest));
  }

  private Room createReturnedForest(Room returnedClearing) {
    return new Room("Du stehst wieder im Wald.", new Option(GO_TO_CLEARING, returnedClearing));
  }

  private Room createReturnedClearing(Room stoneCircle, Room largeStone) {
    return new Room("Du betrittst wieder die Lichtung, in deren Mitte der Steinkreis steht.", new Option(ENTER_STONE_CIRCLE, stoneCircle),
        new Option(INSPECT_LARGE_STONE, largeStone));
  }

  private Room createLargeStone(Room strangeFeeling) {
    return new Room("In der Steinfläche entdeckst du frische Kerben, die nicht vom Wetter stammen.",
        new Option(TOUCH_STONE_MARKS, strangeFeeling));
  }

  private Room createStrangeFeeling(Room swordInStone) {
    return new Room("Als dein Finger in den Kerben liegt, spürst du aus dem Kreis einen Luftzug.",
        new Option(ENTER_STONE_CIRCLE, swordInStone));
  }

  private Room createStoneCircle() {
    return new Room("Zwischen den alten Steinen fühlst du dich unwohl.");
  }

  private Room createSwordInStone(Room pulledSword) {
    return new Room("Mitten im Steinkreis fällt dir ein großer Block auf - war der vorher schon da? In dem Block steckt ein Schwert.",
        new Option(PULL_SWORD_FROM_STONE, pulledSword));
  }

  private Room createPulledSword() {
    return new Room("Als du das Schwert aus dem Stein ziehst, glimmt die Klinge kurz auf und wird warm in deiner Hand.");
  }
}
