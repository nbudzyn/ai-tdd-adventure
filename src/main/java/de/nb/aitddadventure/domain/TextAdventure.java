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
    RoomLink.validateAllConnected(
        links.clearing(),
        links.stoneCircle(),
        links.stoneCircleExit(),
        links.returnedForest(),
        links.returnedClearing(),
        links.largeStone(),
        links.returnedLargeStone(),
        links.stoneSurfaceClearing(),
        links.stoneSurfaceReturnedClearing());
    return rooms.startForest();
  }

  private WorldLinks createWorldLinks() {
    return new WorldLinks(
        new RoomLink("Lichtung am Anfang"),
        new RoomLink("Steinkreis"),
        new RoomLink("Ausgang aus dem Steinkreis"),
        new RoomLink("Wald nach dem Verlassen des Steinkreises"),
        new RoomLink("Zurueckgekehrte Lichtung nach dem Verlassen des Steinkreises"),
        new RoomLink("Steinfläche an der Lichtung"),
        new RoomLink("Steinfläche an der zurueckgekehrten Lichtung"),
        new RoomLink("Lichtung nach dem Zuruecktreten von der Steinflaeche"),
        new RoomLink("Zurueckgekehrte Lichtung nach dem Zuruecktreten von der Steinflaeche"));
  }

  private WorldRooms createWorldRooms(WorldLinks links) {
    var startForest = createStartForest(links.clearing());
    var clearing = createClearing(links.stoneCircle(), links.largeStone(), links.returnedForest());
    var stoneCircle = createStoneCircle(links.stoneCircleExit());
    var stoneCircleExit = createStoneCircleExit(links.stoneCircle(), links.returnedForest());
    var returnedForest = createReturnedForest(links.returnedClearing());
    var returnedClearing = createReturnedClearing(links.stoneCircle(), links.returnedLargeStone(), links.returnedForest());
    var pulledSword = createPulledSword();
    var swordInStone = createSwordInStone(pulledSword);
    var strangeFeeling = createStrangeFeeling(swordInStone, links.returnedForest());
    var largeStone = createLargeStone(links.stoneSurfaceClearing(), strangeFeeling);
    var returnedLargeStone = createLargeStone(links.stoneSurfaceReturnedClearing(), strangeFeeling);
    var stoneSurfaceClearing = createStoneSurfaceStepBack(links.stoneCircle(), links.largeStone(), links.returnedForest());
    var stoneSurfaceReturnedClearing = createStoneSurfaceStepBack(links.stoneCircle(), links.returnedLargeStone(), links.returnedForest());
    return new WorldRooms(
        startForest,
        clearing,
        stoneCircle,
        stoneCircleExit,
        returnedForest,
        returnedClearing,
        pulledSword,
        swordInStone,
        strangeFeeling,
        largeStone,
        returnedLargeStone,
        stoneSurfaceClearing,
        stoneSurfaceReturnedClearing);
  }

  private void connectWorld(WorldLinks links, WorldRooms rooms) {
    links.clearing().connect(rooms.clearing());
    links.stoneCircle().connect(rooms.stoneCircle());
    links.stoneCircleExit().connect(rooms.stoneCircleExit());
    links.returnedForest().connect(rooms.returnedForest());
    links.returnedClearing().connect(rooms.returnedClearing());
    links.largeStone().connect(rooms.largeStone());
    links.returnedLargeStone().connect(rooms.returnedLargeStone());
    links.stoneSurfaceClearing().connect(rooms.stoneSurfaceClearing());
    links.stoneSurfaceReturnedClearing().connect(rooms.stoneSurfaceReturnedClearing());
  }

  private Room createStartForest(RoomLink clearingLink) {
    return new Room("Du bist in einem Wald.", new Option(GO_TO_CLEARING, clearingLink));
  }

  private Room createClearing(RoomLink stoneCircleLink, RoomLink largeStoneLink, RoomLink returnedForestLink) {
    return createClearingLike("Du kommst auf eine Lichtung. In ihrer Mitte liegt ein Steinkreis.",
        stoneCircleLink, largeStoneLink, returnedForestLink);
  }

  private Room createReturnedForest(RoomLink returnedClearingLink) {
    return new Room("Du gehst in den Wald zurück.", new Option(RETURN_TO_CLEARING, returnedClearingLink));
  }

  private Room createReturnedClearing(RoomLink stoneCircleLink, RoomLink largeStoneLink, RoomLink returnedForestLink) {
    return createClearingLike("Du kommst wieder auf die Lichtung. In ihrer Mitte liegt der Steinkreis.",
        stoneCircleLink, largeStoneLink, returnedForestLink);
  }

  private Room createClearingLike(String description, RoomLink stoneCircleLink, RoomLink largeStoneLink, RoomLink returnedForestLink) {
    return new Room(description, new Option(ENTER_STONE_CIRCLE, stoneCircleLink),
        new Option(INSPECT_LARGE_STONE, largeStoneLink), new Option(GO_TO_FOREST, returnedForestLink));
  }

  private Room createStoneSurfaceStepBack(RoomLink stoneCircleLink, RoomLink largeStoneLink, RoomLink returnedForestLink) {
    return new Room("Du trittst von der Steinfläche zurück und stehst wieder auf der Lichtung.",
        new Option(ENTER_STONE_CIRCLE, stoneCircleLink),
        new Option(INSPECT_LARGE_STONE, largeStoneLink),
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

  private Room createStoneCircle(RoomLink stoneCircleExitLink) {
    return new Room("Du trittst zwischen die alten Steine und fühlst dich unwohl.",
        new Option(EXIT_STONE_CIRCLE, stoneCircleExitLink));
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

  private record WorldLinks(RoomLink clearing, RoomLink stoneCircle, RoomLink stoneCircleExit, RoomLink returnedForest,
      RoomLink returnedClearing, RoomLink largeStone, RoomLink returnedLargeStone, RoomLink stoneSurfaceClearing,
      RoomLink stoneSurfaceReturnedClearing) {
  }

  private record WorldRooms(Room startForest, Room clearing, Room stoneCircle, Room stoneCircleExit, Room returnedForest,
      Room returnedClearing, Room pulledSword, Room swordInStone, Room strangeFeeling, Room largeStone,
      Room returnedLargeStone, Room stoneSurfaceClearing, Room stoneSurfaceReturnedClearing) {
  }
}
