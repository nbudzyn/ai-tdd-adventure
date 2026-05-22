package de.nb.aitddadventure.domain;

import static de.nb.aitddadventure.domain.PlayerAction.ENTER_STONE_CIRCLE;
import static de.nb.aitddadventure.domain.PlayerAction.GO_TO_CLEARING;
import static de.nb.aitddadventure.domain.PlayerAction.GO_TO_FOREST;
import static de.nb.aitddadventure.domain.PlayerAction.INSPECT_LARGE_STONE;
import static de.nb.aitddadventure.domain.PlayerAction.PULL_SWORD_FROM_STONE;
import static de.nb.aitddadventure.domain.PlayerAction.TOUCH_STONE_MARKS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;

class AdventureTest {

  @Test
  void shouldStartInAForest() {
    // Given
    var adventure = new TextAdventure();

    // When
    var room = adventure.start();

    // Then
    assertThat(room.description()).isEqualTo("Du stehst in einem Wald.");
  }

  @Test
  void shouldOfferOnlyGoingStraightAtTheBeginning() {
    // Given
    var adventure = new TextAdventure();
    var room = adventure.start();

    // When
    var options = room.options();

    // Then
    assertThat(options).hasSize(1);
    assertThat(options.getFirst().action()).isEqualTo(GO_TO_CLEARING);
  }

  @Test
  void shouldEnterClearingWithStoneCircleWhenGoingStraight() {
    // Given
    var adventure = new TextAdventure();
    var room = adventure.start();
    var option = room.option(GO_TO_CLEARING);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("Du stehst auf einer Lichtung, in der Mitte ein Steinkreis.");
  }

  @Test
  void shouldReturnToForestFromClearing() {
    // Given
    var adventure = new TextAdventure();
    var clearing = goToClearing(adventure);
    var option = clearing.option(GO_TO_FOREST);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("Du stehst wieder im Wald.");
  }

  @Test
  void shouldReturnToClearingFromReturnedForest() {
    // Given
    var adventure = new TextAdventure();
    var forest = adventure.start();
    var returnedForest = forest.option(GO_TO_CLEARING).choose().option(GO_TO_FOREST).choose();
    var option = returnedForest.option(GO_TO_CLEARING);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("Du betrittst wieder die Lichtung, in deren Mitte der Steinkreis steht.");
  }

  @Test
  void shouldStepIntoStoneCircleFromClearing() {
    // Given
    var adventure = new TextAdventure();
    var clearing = goToClearing(adventure);
    var option = clearing.option(ENTER_STONE_CIRCLE);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("Zwischen den alten Steinen fühlst du dich unwohl.");
  }

  @Test
  void shouldNoticeFreshMarksWhenLookingAtOneOfTheLargeStones() {
    // Given
    var adventure = new TextAdventure();
    var clearing = goToClearing(adventure);
    var option = clearing.option(INSPECT_LARGE_STONE);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("In der Steinfläche entdeckst du frische Kerben, die nicht vom Wetter stammen.");
  }

  @Test
  void shouldFeelSomethingStrangeWhenTouchingTheStoneMarks() {
    // Given
    var adventure = new TextAdventure();
    var largeStone = goToLargeStone(adventure);
    var option = largeStone.option(TOUCH_STONE_MARKS);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("Als dein Finger in den Kerben liegt, spürst du aus dem Kreis einen Luftzug.");
  }

  @Test
  void shouldNoticeSwordInStoneWhenEnteringCircleAfterFeelingTheDraft() {
    // Given
    var adventure = new TextAdventure();
    var strangeFeeling = goToStrangeFeeling(adventure);
    var option = strangeFeeling.option(ENTER_STONE_CIRCLE);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo(
        "Mitten im Steinkreis fällt dir ein großer Block auf - war der vorher schon da? In dem Block steckt ein Schwert.");
  }

  @Test
  void shouldFeelWarmthWhenPullingSwordFromStone() {
    // Given
    var adventure = new TextAdventure();
    var swordInStone = goToSwordInStone(adventure);
    var option = swordInStone.option(PULL_SWORD_FROM_STONE);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo(
        "Als du das Schwert aus dem Stein ziehst, glimmt die Klinge kurz auf und wird warm in deiner Hand.");
  }

  @Test
  void shouldRejectActionThatIsNotAvailableInRoom() {
    // Given
    var adventure = new TextAdventure();
    var forest = adventure.start();

    // When / Then
    assertThatThrownBy(() -> forest.option(INSPECT_LARGE_STONE)).isInstanceOf(IllegalStateException.class);
  }

  private Room goToSwordInStone(TextAdventure adventure) {
    return goToStrangeFeeling(adventure).option(ENTER_STONE_CIRCLE).choose();
  }

  private Room goToStrangeFeeling(TextAdventure adventure) {
    return goToLargeStone(adventure).option(TOUCH_STONE_MARKS).choose();
  }

  private Room goToLargeStone(TextAdventure adventure) {
    return goToClearing(adventure).option(INSPECT_LARGE_STONE).choose();
  }

  private Room goToClearing(TextAdventure adventure) {
    return adventure.start().option(GO_TO_CLEARING).choose();
  }
}
