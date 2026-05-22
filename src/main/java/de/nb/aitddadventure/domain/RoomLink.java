package de.nb.aitddadventure.domain;

/**
 * Verweis auf einen Zielraum.
 */
public final class RoomLink {
  private static final String UNNAMED_ROOM_LINK = "unbenannter Raumverweis";

  private final String name;
  private Room room;

  public RoomLink() {
    this(UNNAMED_ROOM_LINK);
  }

  public RoomLink(String name) {
    this.name = name;
  }

  public RoomLink(Room room) {
    this(UNNAMED_ROOM_LINK, room);
  }

  public RoomLink(String name, Room room) {
    this(name);
    connect(room);
  }

  public void connect(Room room) {
    this.room = room;
  }

  public Room follow() {
    validateConnected();
    return room;
  }

  public static void validateAllConnected(RoomLink... roomLinks) {
    for (var roomLink : roomLinks) {
      roomLink.validateConnected();
    }
  }

  public void validateConnected() {
    if (room == null) {
      throw new IllegalStateException("Raumverweis ist nicht verbunden: " + name);
    }
  }
}
