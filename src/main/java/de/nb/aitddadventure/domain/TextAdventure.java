package de.nb.aitddadventure.domain;

import static de.nb.aitddadventure.domain.PlayerAction.ENTER_STONE_CIRCLE;
import static de.nb.aitddadventure.domain.PlayerAction.EXIT_STONE_CIRCLE;
import static de.nb.aitddadventure.domain.PlayerAction.GO_TO_CLEARING;
import static de.nb.aitddadventure.domain.PlayerAction.GO_TO_FOREST;
import static de.nb.aitddadventure.domain.PlayerAction.INSPECT_LARGE_STONE;
import static de.nb.aitddadventure.domain.PlayerAction.PULL_SWORD_FROM_STONE;
import static de.nb.aitddadventure.domain.PlayerAction.RETURN_TO_CLEARING;
import static de.nb.aitddadventure.domain.PlayerAction.STEP_BACK_FROM_STONE_SURFACE;
import static de.nb.aitddadventure.domain.PlayerAction.TOUCH_STONE_MARKS;

/**
 * Aufbau der Welt des Textadventures mit ihrem Startpunkt.
 */
public class TextAdventure {
  public Room start() {
    return createWorld();
  }

  private Room createWorld() {
    var stoneSurfaceClearingLink = new RoomLink("Lichtung nach dem Zuruecktreten von der Steinflaeche");
    var stoneSurfaceReturnedClearingLink = new RoomLink("Zurueckgekehrte Lichtung nach dem Zuruecktreten von der Steinflaeche");
    var stoneCircleLink = new RoomLink("Steinkreis nach dem Verlassen");
    var returnedForestLink = new RoomLink("Wald nach dem Verlassen des Steinkreises");
    var stoneCircleExit = createStoneCircleExit(stoneCircleLink, returnedForestLink);
    var pulledSword = createPulledSword();
    var swordInStone = createSwordInStone(pulledSword);
    var stoneCircle = createStoneCircle(stoneCircleExit);
    stoneCircleLink.connect(stoneCircle);
    var strangeFeeling = createStrangeFeeling(swordInStone);
    var largeStone = createLargeStone(stoneSurfaceClearingLink, strangeFeeling);
    var returnedLargeStone = createLargeStone(stoneSurfaceReturnedClearingLink, strangeFeeling);
    var stoneSurfaceClearing = createStoneSurfaceStepBack(stoneCircle, largeStone, returnedForestLink);
    var stoneSurfaceReturnedClearing = createStoneSurfaceStepBack(stoneCircle, returnedLargeStone, returnedForestLink);
    stoneSurfaceClearingLink.connect(stoneSurfaceClearing);
    stoneSurfaceReturnedClearingLink.connect(stoneSurfaceReturnedClearing);
    var returnedClearing = createReturnedClearing(stoneCircle, returnedLargeStone, returnedForestLink);
    var returnedForest = createReturnedForest(returnedClearing);
    returnedForestLink.connect(returnedForest);
    var clearing = createClearing(stoneCircle, largeStone, returnedForest);
    RoomLink.validateAllConnected(stoneSurfaceClearingLink, stoneSurfaceReturnedClearingLink, stoneCircleLink, returnedForestLink);
    return createStartForest(clearing);
  }

  private Room createStartForest(Room clearing) {
    return new Room("Du bist in einem Wald.", new Option(GO_TO_CLEARING, clearing));
  }

  private Room createClearing(Room stoneCircle, Room largeStone, Room returnedForest) {
    return new Room("Du kommst auf eine Lichtung. In ihrer Mitte liegt ein Steinkreis.", new Option(ENTER_STONE_CIRCLE, stoneCircle),
        new Option(INSPECT_LARGE_STONE, largeStone), new Option(GO_TO_FOREST, returnedForest));
  }

  private Room createReturnedForest(Room returnedClearing) {
    return new Room("Du gehst in den Wald zurück.", new Option(RETURN_TO_CLEARING, returnedClearing));
  }

  private Room createReturnedClearing(Room stoneCircle, Room largeStone, RoomLink returnedForestLink) {
    return new Room("Du kommst wieder auf die Lichtung. In ihrer Mitte liegt der Steinkreis.", new Option(ENTER_STONE_CIRCLE, stoneCircle),
        new Option(INSPECT_LARGE_STONE, largeStone), new Option(GO_TO_FOREST, returnedForestLink));
  }

  private Room createStoneSurfaceStepBack(Room stoneCircle, Room largeStone, RoomLink returnedForestLink) {
    return new Room("Du trittst von der Steinfläche zurück und stehst wieder auf der Lichtung.",
        new Option(ENTER_STONE_CIRCLE, stoneCircle),
        new Option(INSPECT_LARGE_STONE, largeStone),
        new Option(GO_TO_FOREST, returnedForestLink));
  }

  private Room createLargeStone(RoomLink stepBackTarget, Room strangeFeeling) {
    return new Room("Als du die Steinfläche näher ansiehst, entdeckst du frische Kerben, die nicht vom Wetter stammen.",
        new Option(STEP_BACK_FROM_STONE_SURFACE, stepBackTarget),
        new Option(TOUCH_STONE_MARKS, strangeFeeling));
  }

  private Room createStrangeFeeling(Room swordInStone) {
    return new Room("Als du einen Finger in die Kerben legst, spürst du einen Luftzug aus dem Kreis.",
        new Option(ENTER_STONE_CIRCLE, swordInStone));
  }

  private Room createStoneCircle(Room stoneCircleExit) {
    return new Room("Du trittst zwischen die alten Steine und fühlst dich unwohl.",
        new Option(EXIT_STONE_CIRCLE, stoneCircleExit));
  }

  private Room createStoneCircleExit(RoomLink stoneCircleLink, RoomLink returnedForestLink) {
    return new Room("Du trittst aus dem Steinkreis und kommst wieder auf die Lichtung.",
        new Option(ENTER_STONE_CIRCLE, stoneCircleLink),
        new Option(GO_TO_FOREST, returnedForestLink));
  }

  private Room createSwordInStone(Room pulledSword) {
    return new Room("Mitten im Steinkreis fällt dir ein großer Block auf. War der vorher schon da? In ihm steckt ein Schwert.",
        new Option(PULL_SWORD_FROM_STONE, pulledSword));
  }

  private Room createPulledSword() {
    return new Room("Als du das Schwert aus dem Stein ziehst, glimmt die Klinge kurz auf und wird warm in deiner Hand.");
  }
}
