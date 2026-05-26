package de.nb.aitddadventure.domain;

import static de.nb.aitddadventure.domain.PlayerAction.ENTER_STONE_CIRCLE;
import static de.nb.aitddadventure.domain.PlayerAction.EXIT_STONE_CIRCLE;
import static de.nb.aitddadventure.domain.PlayerAction.GO_TO_CLEARING;
import static de.nb.aitddadventure.domain.PlayerAction.GO_TO_FOREST;
import static de.nb.aitddadventure.domain.PlayerAction.INSPECT_LARGE_STONE;
import static de.nb.aitddadventure.domain.PlayerAction.PULL_SWORD_FROM_STONE;
import static de.nb.aitddadventure.domain.PlayerAction.RETURN_TO_CLEARING;
import static de.nb.aitddadventure.domain.PlayerAction.RETURN_TO_FOREST;
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
    var links = createWorldLinks();
    var rooms = createWorldRooms(links);
    connectWorld(links, rooms);
    RoomLink.validateAllConnected(links.stoneSurfaceClearing(), links.stoneSurfaceReturnedClearing(), links.stoneCircle(), links.returnedForest());
    return createStartForest(rooms.clearing());
  }

  private WorldLinks createWorldLinks() {
    return new WorldLinks(
        new RoomLink("Lichtung nach dem Zuruecktreten von der Steinflaeche"),
        new RoomLink("Zurueckgekehrte Lichtung nach dem Zuruecktreten von der Steinflaeche"),
        new RoomLink("Steinkreis nach dem Verlassen"),
        new RoomLink("Wald nach dem Verlassen des Steinkreises"));
  }

  private WorldRooms createWorldRooms(WorldLinks links) {
    var stoneCircleExit = createStoneCircleExit(links.stoneCircle(), links.returnedForest());
    var pulledSword = createPulledSword();
    var swordInStone = createSwordInStone(pulledSword);
    var stoneCircle = createStoneCircle(stoneCircleExit);
    var strangeFeeling = createStrangeFeeling(swordInStone, links.returnedForest());
    var largeStone = createLargeStone(links.stoneSurfaceClearing(), strangeFeeling);
    var returnedLargeStone = createLargeStone(links.stoneSurfaceReturnedClearing(), strangeFeeling);
    var stoneSurfaceClearing = createStoneSurfaceStepBack(stoneCircle, largeStone, links.returnedForest());
    var stoneSurfaceReturnedClearing = createStoneSurfaceStepBack(stoneCircle, returnedLargeStone, links.returnedForest());
    var returnedClearing = createReturnedClearing(stoneCircle, returnedLargeStone, links.returnedForest());
    var returnedForest = createReturnedForest(returnedClearing);
    var clearing = createClearing(stoneCircle, largeStone, links.returnedForest());
    return new WorldRooms(
        clearing,
        stoneCircle,
        stoneSurfaceClearing,
        stoneSurfaceReturnedClearing,
        returnedForest,
        returnedClearing);
  }

  private void connectWorld(WorldLinks links, WorldRooms rooms) {
    links.stoneCircle().connect(rooms.stoneCircle());
    links.stoneSurfaceClearing().connect(rooms.stoneSurfaceClearing());
    links.stoneSurfaceReturnedClearing().connect(rooms.stoneSurfaceReturnedClearing());
    links.returnedForest().connect(rooms.returnedForest());
  }

  private Room createStartForest(Room clearing) {
    return new Room("Du bist in einem Wald.", new Option(GO_TO_CLEARING, clearing));
  }

  private Room createClearing(Room stoneCircle, Room largeStone, RoomLink returnedForestLink) {
    return createClearingLike("Du kommst auf eine Lichtung. In ihrer Mitte liegt ein Steinkreis.",
        stoneCircle, largeStone, returnedForestLink);
  }

  private Room createReturnedForest(Room returnedClearing) {
    return new Room("Du gehst in den Wald zurück.", new Option(RETURN_TO_CLEARING, returnedClearing));
  }

  private Room createReturnedClearing(Room stoneCircle, Room largeStone, RoomLink returnedForestLink) {
    return createClearingLike("Du kommst wieder auf die Lichtung. In ihrer Mitte liegt der Steinkreis.",
        stoneCircle, largeStone, returnedForestLink);
  }

  private Room createClearingLike(String description, Room stoneCircle, Room largeStone, RoomLink returnedForestLink) {
    return new Room(description, new Option(ENTER_STONE_CIRCLE, stoneCircle),
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

  private Room createStrangeFeeling(Room swordInStone, RoomLink returnedForestLink) {
    return new Room("Als du einen Finger in die Kerben legst, spürst du einen Luftzug aus dem Kreis.",
        new Option(ENTER_STONE_CIRCLE, swordInStone),
        new Option(RETURN_TO_FOREST, returnedForestLink));
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

  private record WorldLinks(RoomLink stoneSurfaceClearing, RoomLink stoneSurfaceReturnedClearing, RoomLink stoneCircle,
      RoomLink returnedForest) {
  }

  private record WorldRooms(Room clearing, Room stoneCircle, Room stoneSurfaceClearing, Room stoneSurfaceReturnedClearing,
      Room returnedForest, Room returnedClearing) {
  }
}
