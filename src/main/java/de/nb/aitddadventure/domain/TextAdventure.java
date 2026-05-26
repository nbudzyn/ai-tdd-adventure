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
import static de.nb.aitddadventure.domain.RoomId.CLEARING;
import static de.nb.aitddadventure.domain.RoomId.LARGE_STONE;
import static de.nb.aitddadventure.domain.RoomId.PULLED_SWORD;
import static de.nb.aitddadventure.domain.RoomId.RETURNED_CLEARING;
import static de.nb.aitddadventure.domain.RoomId.RETURNED_FOREST;
import static de.nb.aitddadventure.domain.RoomId.RETURNED_LARGE_STONE;
import static de.nb.aitddadventure.domain.RoomId.START_FOREST;
import static de.nb.aitddadventure.domain.RoomId.STONE_CIRCLE;
import static de.nb.aitddadventure.domain.RoomId.STONE_CIRCLE_EXIT;
import static de.nb.aitddadventure.domain.RoomId.STONE_SURFACE_CLEARING;
import static de.nb.aitddadventure.domain.RoomId.STONE_SURFACE_RETURNED_CLEARING;
import static de.nb.aitddadventure.domain.RoomId.STRANGE_FEELING;
import static de.nb.aitddadventure.domain.RoomId.SWORD_IN_STONE;

import java.util.EnumMap;
import java.util.List;

/**
 * Aufbau der Welt des Textadventures mit ihrem Startpunkt.
 */
public class TextAdventure {
  private final EnumMap<RoomId, Room> rooms = createWorld();

  public Room start() {
    return rooms.get(START_FOREST);
  }

  public Room choose(Option option) {
    var nextRoom = rooms.get(option.target());
    if (nextRoom == null) {
      throw new IllegalStateException("Raum ist nicht definiert: " + option.target());
    }
    return nextRoom;
  }

  private EnumMap<RoomId, Room> createWorld() {
    var roomDefinitions = createWorldDefinitions();
    var rooms = createRooms(roomDefinitions);
    validateWorld(roomDefinitions, rooms);
    return rooms;
  }

  private List<RoomDefinition> createWorldDefinitions() {
    return List.of(createStartForest(), createClearing(), createReturnedForest(), createLargeStone(), createStoneSurfaceClearing(),
        createReturnedClearing(), createStoneSurfaceReturnedClearing(), createStoneCircle(), createStoneCircleExit(),
        createReturnedLargeStone(), createStrangeFeeling(), createSwordInStone(), createPulledSword());
  }

  private RoomDefinition createStartForest() {
    return room(START_FOREST, "Du bist in einem Wald.", option(GO_TO_CLEARING, CLEARING));
  }

  private RoomDefinition createReturnedForest() {
    return room(RETURNED_FOREST, "Du gehst in den Wald zurück.", option(RETURN_TO_CLEARING, RETURNED_CLEARING));
  }

  private RoomDefinition createReturnedClearing() {
    return room(RETURNED_CLEARING, "Du kommst wieder auf die Lichtung. In ihrer Mitte liegt der Steinkreis.",
        option(ENTER_STONE_CIRCLE, STONE_CIRCLE), option(INSPECT_LARGE_STONE, RETURNED_LARGE_STONE), option(GO_TO_FOREST, RETURNED_FOREST));
  }

  private RoomDefinition createStoneSurfaceClearing() {
    return room(STONE_SURFACE_CLEARING, "Du trittst von der Steinfläche zurück und stehst wieder auf der Lichtung.",
        option(ENTER_STONE_CIRCLE, STONE_CIRCLE), option(INSPECT_LARGE_STONE, LARGE_STONE), option(GO_TO_FOREST, RETURNED_FOREST));
  }

  private RoomDefinition createStoneSurfaceReturnedClearing() {
    return room(STONE_SURFACE_RETURNED_CLEARING, "Du trittst von der Steinfläche zurück und stehst wieder auf der Lichtung.",
        option(ENTER_STONE_CIRCLE, STONE_CIRCLE), option(INSPECT_LARGE_STONE, RETURNED_LARGE_STONE), option(GO_TO_FOREST, RETURNED_FOREST));
  }

  private RoomDefinition createStoneCircleExit() {
    return room(STONE_CIRCLE_EXIT, "Du trittst aus dem Steinkreis und kommst wieder auf die Lichtung.",
        option(ENTER_STONE_CIRCLE, STONE_CIRCLE), option(GO_TO_FOREST, RETURNED_FOREST));
  }

  private RoomDefinition createClearing() {
    return room(CLEARING, "Du kommst auf eine Lichtung. In ihrer Mitte liegt ein Steinkreis.", option(ENTER_STONE_CIRCLE, STONE_CIRCLE),
        option(INSPECT_LARGE_STONE, LARGE_STONE), option(GO_TO_FOREST, RETURNED_FOREST));
  }

  private RoomDefinition createLargeStone() {
    return room(LARGE_STONE, "Als du die Steinfläche näher ansiehst, entdeckst du frische Kerben, die nicht vom Wetter stammen.",
        option(STEP_BACK_FROM_STONE_SURFACE, STONE_SURFACE_CLEARING), option(TOUCH_STONE_MARKS, STRANGE_FEELING));
  }

  private RoomDefinition createReturnedLargeStone() {
    return room(RETURNED_LARGE_STONE, "Als du die Steinfläche näher ansiehst, entdeckst du frische Kerben, die nicht vom Wetter stammen.",
        option(STEP_BACK_FROM_STONE_SURFACE, STONE_SURFACE_RETURNED_CLEARING), option(TOUCH_STONE_MARKS, STRANGE_FEELING));
  }

  private RoomDefinition createStrangeFeeling() {
    return room(STRANGE_FEELING, "Als du einen Finger in die Kerben legst, spürst du einen Luftzug aus dem Kreis.",
        option(ENTER_STONE_CIRCLE, SWORD_IN_STONE), option(RETURN_TO_FOREST, RETURNED_FOREST));
  }

  private RoomDefinition createStoneCircle() {
    return room(STONE_CIRCLE, "Du trittst zwischen die alten Steine und fühlst dich unwohl.", option(EXIT_STONE_CIRCLE, STONE_CIRCLE_EXIT));
  }

  private RoomDefinition createSwordInStone() {
    return room(SWORD_IN_STONE, "Mitten im Steinkreis fällt dir ein großer Block auf. War der vorher schon da? In ihm steckt ein Schwert.",
        option(PULL_SWORD_FROM_STONE, PULLED_SWORD));
  }

  private RoomDefinition createPulledSword() {
    return room(PULLED_SWORD, "Als du das Schwert aus dem Stein ziehst, glimmt die Klinge kurz auf und wird warm in deiner Hand.");
  }

  private RoomDefinition room(RoomId id, String description, OptionDefinition... options) {
    return new RoomDefinition(id, description, List.of(options));
  }

  private OptionDefinition option(PlayerAction action, RoomId target) {
    return new OptionDefinition(action, target);
  }

  private EnumMap<RoomId, Room> createRooms(List<RoomDefinition> roomDefinitions) {
    var rooms = new EnumMap<RoomId, Room>(RoomId.class);
    for (var roomDefinition : roomDefinitions) {
      rooms.put(roomDefinition.id(), createRoom(roomDefinition));
    }
    return rooms;
  }

  private Room createRoom(RoomDefinition roomDefinition) {
    var options = roomDefinition.options().stream().map(option -> new Option(option.action(), option.target())).toList();
    return new Room(roomDefinition.description(), options);
  }

  private void validateWorld(List<RoomDefinition> roomDefinitions, EnumMap<RoomId, Room> rooms) {
    for (var roomDefinition : roomDefinitions) {
      validateRoomExists(rooms, roomDefinition.id());
      for (var option : roomDefinition.options()) {
        validateRoomExists(rooms, option.target());
      }
    }
  }

  private void validateRoomExists(EnumMap<RoomId, Room> rooms, RoomId roomId) {
    if (!rooms.containsKey(roomId)) {
      throw new IllegalStateException("Raum ist nicht definiert: " + roomId);
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
