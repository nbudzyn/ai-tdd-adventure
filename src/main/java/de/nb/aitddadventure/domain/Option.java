package de.nb.aitddadventure.domain;

/**
 * Auswählbare Spieleraktion mit Zielraumkennung.
 */
public record Option(PlayerAction action, RoomId target) {
}
