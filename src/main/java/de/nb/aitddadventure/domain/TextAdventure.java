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

import java.util.List;

/**
 * Aufbau der Welt des Textadventures mit ihrem Startpunkt.
 */
public class TextAdventure {
  public Room start() {
    return createWorld();
  }

  private Room createWorld() {
    var forestLink = new RoomLink("Wald");
    var clearingLink = new RoomLink("Lichtung");
    var stoneCircleLink = new RoomLink("Steinkreis");
    var stoneCircleExitLink = new RoomLink("Steinkreis-Ausgang");
    var largeStoneLink = new RoomLink("Großer Stein");
    var strangeFeelingLink = new RoomLink("Seltsames Gefühl");
    var swordInStoneLink = new RoomLink("Schwert im Stein");
    var pulledSwordLink = new RoomLink("Gezogenes Schwert");
    var stoneSurfaceLink = new RoomLink("Steinfläche");

    var goToClearing = new Option(GO_TO_CLEARING, clearingLink);
    var returnToClearing = new Option(RETURN_TO_CLEARING, clearingLink);
    var enterStoneCircle = new Option(ENTER_STONE_CIRCLE, stoneCircleLink);
    var exitStoneCircle = new Option(EXIT_STONE_CIRCLE, stoneCircleExitLink);
    var inspectLargeStone = new Option(INSPECT_LARGE_STONE, largeStoneLink);
    var stepBackFromStoneSurface = new Option(STEP_BACK_FROM_STONE_SURFACE, stoneSurfaceLink);
    var touchStoneMarks = new Option(TOUCH_STONE_MARKS, strangeFeelingLink);
    var returnToForest = new Option(RETURN_TO_FOREST, forestLink);
    var goToForest = new Option(GO_TO_FOREST, forestLink);
    var enterSwordInStone = new Option(ENTER_STONE_CIRCLE, swordInStoneLink);
    var pullSwordFromStone = new Option(PULL_SWORD_FROM_STONE, pulledSwordLink);

    var forest = Room.of("Du bist in einem Wald.", "Du gehst in den Wald zurück.",
        List.of(goToClearing), List.of(returnToClearing));
    var clearing = Room.of("Du kommst auf eine Lichtung. In ihrer Mitte liegt ein Steinkreis.",
        "Du kommst wieder auf die Lichtung. In ihrer Mitte liegt der Steinkreis.",
        List.of(enterStoneCircle, inspectLargeStone, goToForest),
        List.of(enterStoneCircle, inspectLargeStone, goToForest));
    var stoneCircle = Room.of("Du trittst zwischen die alten Steine und fühlst dich unwohl.",
        exitStoneCircle);
    var stoneCircleExit = Room.of("Du trittst aus dem Steinkreis und kommst wieder auf die Lichtung.",
        enterStoneCircle, goToForest);
    var largeStone = Room.of("Als du die Steinfläche näher ansiehst, entdeckst du frische Kerben, die nicht vom Wetter stammen.",
        stepBackFromStoneSurface, touchStoneMarks);
    var strangeFeeling = Room.of("Als du einen Finger in die Kerben legst, spürst du einen Luftzug aus dem Kreis.",
        enterSwordInStone, returnToForest);
    var swordInStone = Room.of("Mitten im Steinkreis fällt dir ein großer Block auf. War der vorher schon da? In ihm steckt ein Schwert.",
        pullSwordFromStone);
    var pulledSword = Room.of("Als du das Schwert aus dem Stein ziehst, glimmt die Klinge kurz auf und wird warm in deiner Hand.");
    var stoneSurface = Room.of("Du trittst von der Steinfläche zurück und stehst wieder auf der Lichtung.",
        enterStoneCircle, inspectLargeStone, goToForest);

    forestLink.connect(forest);
    clearingLink.connect(clearing);
    stoneCircleLink.connect(stoneCircle);
    stoneCircleExitLink.connect(stoneCircleExit);
    largeStoneLink.connect(largeStone);
    strangeFeelingLink.connect(strangeFeeling);
    swordInStoneLink.connect(swordInStone);
    pulledSwordLink.connect(pulledSword);
    stoneSurfaceLink.connect(stoneSurface);

    forest.enter();
    return forest;
  }
}
