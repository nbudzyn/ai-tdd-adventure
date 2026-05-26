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

import java.util.EnumMap;
import java.util.List;

/**
 * Aufbau der Welt des Textadventures mit ihrem Startpunkt.
 */
public class TextAdventure {
  public Room start() {
    return createWorld();
  }

  private Room createWorld() {
    var roomDefinitions = createWorldDefinitions();
    var roomLinks = createRoomLinks(roomDefinitions);
    var rooms = createRooms(roomDefinitions, roomLinks);
    return rooms.get(RoomId.START_FOREST);
  }

  private List<RoomDefinition> createWorldDefinitions() {
    return List.of(createStartForest(), createClearing(), createReturnedForest(), createLargeStone(), createStoneSurfaceClearing(),
        createReturnedClearing(), createStoneSurfaceReturnedClearing(), createStoneCircle(), createStoneCircleExit(),
        createReturnedLargeStone(), createStrangeFeeling(), createSwordInStone(), createPulledSword());
  }

  private RoomDefinition createStartForest() {
    return room(RoomId.START_FOREST, "Du bist in einem Wald.", option(GO_TO_CLEARING, RoomId.CLEARING));
  }

  private RoomDefinition createReturnedForest() {
    return room(RoomId.RETURNED_FOREST, "Du gehst in den Wald zurück.", option(RETURN_TO_CLEARING, RoomId.RETURNED_CLEARING));
  }

  private RoomDefinition createReturnedClearing() {
    return room(RoomId.RETURNED_CLEARING, "Du kommst wieder auf die Lichtung. In ihrer Mitte liegt der Steinkreis.",
        option(ENTER_STONE_CIRCLE, RoomId.STONE_CIRCLE), option(INSPECT_LARGE_STONE, RoomId.RETURNED_LARGE_STONE),
        option(GO_TO_FOREST, RoomId.RETURNED_FOREST));
  }

  private RoomDefinition createStoneSurfaceClearing() {
    return room(RoomId.STONE_SURFACE_CLEARING, "Du trittst von der Steinfläche zurück und stehst wieder auf der Lichtung.",
        option(ENTER_STONE_CIRCLE, RoomId.STONE_CIRCLE), option(INSPECT_LARGE_STONE, RoomId.LARGE_STONE),
        option(GO_TO_FOREST, RoomId.RETURNED_FOREST));
  }

  private RoomDefinition createStoneSurfaceReturnedClearing() {
    return room(RoomId.STONE_SURFACE_RETURNED_CLEARING, "Du trittst von der Steinfläche zurück und stehst wieder auf der Lichtung.",
        option(ENTER_STONE_CIRCLE, RoomId.STONE_CIRCLE), option(INSPECT_LARGE_STONE, RoomId.RETURNED_LARGE_STONE),
        option(GO_TO_FOREST, RoomId.RETURNED_FOREST));
  }

  private RoomDefinition createStoneCircleExit() {
    return room(RoomId.STONE_CIRCLE_EXIT, "Du trittst aus dem Steinkreis und kommst wieder auf die Lichtung.",
        option(ENTER_STONE_CIRCLE, RoomId.STONE_CIRCLE), option(GO_TO_FOREST, RoomId.RETURNED_FOREST));
  }

  private RoomDefinition createClearing() {
    return room(RoomId.CLEARING, "Du kommst auf eine Lichtung. In ihrer Mitte liegt ein Steinkreis.",
        option(ENTER_STONE_CIRCLE, RoomId.STONE_CIRCLE), option(INSPECT_LARGE_STONE, RoomId.LARGE_STONE),
        option(GO_TO_FOREST, RoomId.RETURNED_FOREST));
  }

  private RoomDefinition createLargeStone() {
    return room(RoomId.LARGE_STONE, "Als du die Steinfläche näher ansiehst, entdeckst du frische Kerben, die nicht vom Wetter stammen.",
        option(STEP_BACK_FROM_STONE_SURFACE, RoomId.STONE_SURFACE_CLEARING), option(TOUCH_STONE_MARKS, RoomId.STRANGE_FEELING));
  }

  private RoomDefinition createReturnedLargeStone() {
    return room(RoomId.RETURNED_LARGE_STONE,
        "Als du die Steinfläche näher ansiehst, entdeckst du frische Kerben, die nicht vom Wetter stammen.",
        option(STEP_BACK_FROM_STONE_SURFACE, RoomId.STONE_SURFACE_RETURNED_CLEARING), option(TOUCH_STONE_MARKS, RoomId.STRANGE_FEELING));
  }

  private RoomDefinition createStrangeFeeling() {
    return room(RoomId.STRANGE_FEELING, "Als du einen Finger in die Kerben legst, spürst du einen Luftzug aus dem Kreis.",
        option(ENTER_STONE_CIRCLE, RoomId.SWORD_IN_STONE), option(RETURN_TO_FOREST, RoomId.RETURNED_FOREST));
  }

  private RoomDefinition createStoneCircle() {
    return room(RoomId.STONE_CIRCLE, "Du trittst zwischen die alten Steine und fühlst dich unwohl.",
        option(EXIT_STONE_CIRCLE, RoomId.STONE_CIRCLE_EXIT));
  }

  private RoomDefinition createSwordInStone() {
    return room(RoomId.SWORD_IN_STONE,
        "Mitten im Steinkreis fällt dir ein großer Block auf. War der vorher schon da? In ihm steckt ein Schwert.",
        option(PULL_SWORD_FROM_STONE, RoomId.PULLED_SWORD));
  }

  private RoomDefinition createPulledSword() {
    return room(RoomId.PULLED_SWORD, "Als du das Schwert aus dem Stein ziehst, glimmt die Klinge kurz auf und wird warm in deiner Hand.");
  }

  private RoomDefinition room(RoomId id, String description, OptionDefinition... options) {
    return new RoomDefinition(id, description, List.of(options));
  }

  private OptionDefinition option(PlayerAction action, RoomId target) {
    return new OptionDefinition(action, target);
  }

  private EnumMap<RoomId, RoomLink> createRoomLinks(List<RoomDefinition> roomDefinitions) {
    var roomLinks = new EnumMap<RoomId, RoomLink>(RoomId.class);
    for (var roomDefinition : roomDefinitions) {
      roomLinks.put(roomDefinition.id(), new RoomLink(roomDefinition.id().linkName()));
    }
    return roomLinks;
  }

  private EnumMap<RoomId, Room> createRooms(List<RoomDefinition> roomDefinitions, EnumMap<RoomId, RoomLink> roomLinks) {
    var rooms = new EnumMap<RoomId, Room>(RoomId.class);
    for (var roomDefinition : roomDefinitions) {
      rooms.put(roomDefinition.id(), createRoom(roomDefinition, roomLinks));
    }
    for (var roomDefinition : roomDefinitions) {
      roomLinks.get(roomDefinition.id()).connect(rooms.get(roomDefinition.id()));
    }
    validateWorld(roomDefinitions, roomLinks);
    return rooms;
  }

  private Room createRoom(RoomDefinition roomDefinition, EnumMap<RoomId, RoomLink> roomLinks) {
    var options = roomDefinition.options().stream().map(option -> new Option(option.action(), roomLinks.get(option.target()))).toList();
    return new Room(roomDefinition.description(), options);
  }

  private void validateWorld(List<RoomDefinition> roomDefinitions, EnumMap<RoomId, RoomLink> roomLinks) {
    for (var roomDefinition : roomDefinitions) {
      roomLinks.get(roomDefinition.id()).validateConnected();
    }
  }

  /**
   * Raumkennungen der Welt.
   */
  private enum RoomId {
    START_FOREST("Lichtung am Anfang"),
    CLEARING("Lichtung"),
    RETURNED_FOREST("Wald nach dem Verlassen des Steinkreises"),
    LARGE_STONE("Steinfläche an der Lichtung"),
    STONE_SURFACE_CLEARING("Lichtung nach dem Zurücktreten von der Steinfläche"),
    RETURNED_CLEARING("Zurückgekehrte Lichtung nach dem Verlassen des Steinkreises"),
    STONE_SURFACE_RETURNED_CLEARING("Zurückgekehrte Lichtung nach dem Zurücktreten von der Steinfläche"),
    STONE_CIRCLE("Steinkreis"),
    STONE_CIRCLE_EXIT("Ausgang aus dem Steinkreis"),
    RETURNED_LARGE_STONE("Steinfläche an der zurückgekehrten Lichtung"),
    STRANGE_FEELING("Seltsames Gefühl"),
    SWORD_IN_STONE("Schwert im Stein"),
    PULLED_SWORD("Gezogenes Schwert");

    private final String linkName;

    RoomId(String linkName) {
      this.linkName = linkName;
    }

    public String linkName() {
      return linkName;
    }
  }

  /**
   * Definition eines Raums.
   */
  private record RoomDefinition(RoomId id, String description, List<OptionDefinition> options) {
    private RoomDefinition {
      options = List.copyOf(options);
    }
  }

  /**
   * Definition einer Raumoption.
   */
  private record OptionDefinition(PlayerAction action, RoomId target) {
  }
}
