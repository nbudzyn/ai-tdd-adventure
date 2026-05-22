package de.nb.aitddadventure.domain;

/**
 * Änderbarer Verweis auf einen Zielraum.
 */
public final class RoomLink {
  private Room room;

  public RoomLink() {
  }

  public RoomLink(Room room) {
    connect(room);
  }

  public void connect(Room room) {
    this.room = room;
  }

  public Room follow() {
    if (room == null) {
      throw new IllegalStateException("Room link is not connected.");
    }
    return room;
  }
}
