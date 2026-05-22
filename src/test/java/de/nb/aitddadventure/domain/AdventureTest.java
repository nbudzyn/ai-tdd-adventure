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
    assertThat(room.description()).isEqualTo("Du bist in einem Wald.");
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
    assertThat(nextRoom.description()).isEqualTo("Du kommst auf eine Lichtung. In ihrer Mitte liegt ein Steinkreis.");
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
    assertThat(nextRoom.description()).isEqualTo("Du gehst in den Wald zurück.");
  }

  @Test
  void shouldOfferReturningToClearingWhenStandingInReturnedForest() {
    // Given
    var adventure = new TextAdventure();
    var returnedForest = adventure.start().option(GO_TO_CLEARING).choose().option(GO_TO_FOREST).choose();

    // When
    var options = returnedForest.options();

    // Then
    assertThat(options).hasSize(1);
    assertThat(options.getFirst().action()).isEqualTo(RETURN_TO_CLEARING);
  }

  @Test
  void shouldReturnToClearingFromReturnedForest() {
    // Given
    var adventure = new TextAdventure();
    var forest = adventure.start();
    var returnedForest = forest.option(GO_TO_CLEARING).choose().option(GO_TO_FOREST).choose();
    var option = returnedForest.option(RETURN_TO_CLEARING);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("Du kommst wieder auf die Lichtung. In ihrer Mitte liegt der Steinkreis.");
  }

  @Test
  void shouldOfferReturningToForestAgainWhenStandingOnReturnedClearing() {
    // Given
    var adventure = new TextAdventure();
    var returnedClearing = adventure.start().option(GO_TO_CLEARING).choose().option(GO_TO_FOREST).choose().option(RETURN_TO_CLEARING).choose();

    // When
    var options = returnedClearing.options();

    // Then
    assertThat(options).hasSize(3);
    assertThat(options.getFirst().action()).isEqualTo(ENTER_STONE_CIRCLE);
    assertThat(options.get(1).action()).isEqualTo(INSPECT_LARGE_STONE);
    assertThat(options.get(2).action()).isEqualTo(GO_TO_FOREST);
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
    assertThat(nextRoom.description()).isEqualTo("Du trittst zwischen die alten Steine und fühlst dich unwohl.");
  }

  @Test
  void shouldReturnToClearingWhenSteppingOutOfStoneCircle() {
    // Given
    var adventure = new TextAdventure();
    var stoneCircle = goToStoneCircle(adventure);
    var option = stoneCircle.option(EXIT_STONE_CIRCLE);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("Du trittst aus dem Steinkreis und kommst wieder auf die Lichtung.");
  }

  @Test
  void shouldReturnToForestAsSecondOptionWhenSteppingOutOfStoneCircle() {
    // Given
    var adventure = new TextAdventure();
    var stoneCircle = goToStoneCircle(adventure);
    var clearingAfterStoneCircle = stoneCircle.option(EXIT_STONE_CIRCLE).choose();

    // When
    var options = clearingAfterStoneCircle.options();

    // Then
    assertThat(options).hasSize(2);
    assertThat(options.get(1).action()).isEqualTo(GO_TO_FOREST);
  }

  @Test
  void shouldReachStoneCircleAgainAfterReturningToForestFromStoneCircleExit() {
    // Given
    var adventure = new TextAdventure();
    var stoneCircle = goToStoneCircle(adventure);
    var clearingAfterStoneCircle = stoneCircle.option(EXIT_STONE_CIRCLE).choose();
    var forest = clearingAfterStoneCircle.option(GO_TO_FOREST).choose();
    var clearing = forest.option(RETURN_TO_CLEARING).choose();
    var option = clearing.option(ENTER_STONE_CIRCLE);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("Du trittst zwischen die alten Steine und fühlst dich unwohl.");
  }

  @Test
  void shouldReachStoneCircleAgainAfterReturningToForestFromStoneCircleExitTwice() {
    // Given
    var adventure = new TextAdventure();
    var firstStoneCircle = goToStoneCircle(adventure);
    var firstClearingAfterStoneCircle = firstStoneCircle.option(EXIT_STONE_CIRCLE).choose();
    var firstForest = firstClearingAfterStoneCircle.option(GO_TO_FOREST).choose();
    var firstClearing = firstForest.option(RETURN_TO_CLEARING).choose();
    var secondStoneCircle = firstClearing.option(ENTER_STONE_CIRCLE).choose();
    var secondClearingAfterStoneCircle = secondStoneCircle.option(EXIT_STONE_CIRCLE).choose();
    var secondForest = secondClearingAfterStoneCircle.option(GO_TO_FOREST).choose();
    var secondClearing = secondForest.option(RETURN_TO_CLEARING).choose();
    var option = secondClearing.option(ENTER_STONE_CIRCLE);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("Du trittst zwischen die alten Steine und fühlst dich unwohl.");
  }

  @Test
  void shouldFeelUnwellAgainWhenEnteringStoneCircleAfterSteppingOut() {
    // Given
    var adventure = new TextAdventure();
    var stoneCircle = goToStoneCircle(adventure);
    var clearingAfterStoneCircle = stoneCircle.option(EXIT_STONE_CIRCLE).choose();
    var option = clearingAfterStoneCircle.option(ENTER_STONE_CIRCLE);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("Du trittst zwischen die alten Steine und fühlst dich unwohl.");
  }

  @Test
  void shouldFeelUnwellAgainWhenEnteringStoneCircleAfterSteppingOutTwice() {
    // Given
    var adventure = new TextAdventure();
    var firstStoneCircle = goToStoneCircle(adventure);
    var firstClearingAfterStoneCircle = firstStoneCircle.option(EXIT_STONE_CIRCLE).choose();
    var secondStoneCircle = firstClearingAfterStoneCircle.option(ENTER_STONE_CIRCLE).choose();
    var secondClearingAfterStoneCircle = secondStoneCircle.option(EXIT_STONE_CIRCLE).choose();
    var option = secondClearingAfterStoneCircle.option(ENTER_STONE_CIRCLE);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("Du trittst zwischen die alten Steine und fühlst dich unwohl.");
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
    assertThat(nextRoom.description()).isEqualTo(
        "Als du die Steinfläche näher ansiehst, entdeckst du frische Kerben, die nicht vom Wetter stammen.");
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
    assertThat(nextRoom.description()).isEqualTo("Als du einen Finger in die Kerben legst, spürst du einen Luftzug aus dem Kreis.");
  }

  @Test
  void shouldOfferReturningToForestAfterFeelingTheDraft() {
    // Given
    var adventure = new TextAdventure();
    var strangeFeeling = goToStrangeFeeling(adventure);

    // When
    var options = strangeFeeling.options();

    // Then
    assertThat(options).hasSize(2);
    assertThat(options.getFirst().action()).isEqualTo(ENTER_STONE_CIRCLE);
    assertThat(options.get(1).action()).isEqualTo(RETURN_TO_FOREST);
  }

  @Test
  void shouldReturnToClearingWhenSteppingBackFromTheStoneSurface() {
    // Given
    var adventure = new TextAdventure();
    var largeStone = goToLargeStone(adventure);
    var option = largeStone.option(STEP_BACK_FROM_STONE_SURFACE);

    // When
    var nextRoom = option.choose();

    // Then
    assertThat(nextRoom.description()).isEqualTo("Du trittst von der Steinfläche zurück und stehst wieder auf der Lichtung.");
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
        "Mitten im Steinkreis fällt dir ein großer Block auf. War der vorher schon da? In ihm steckt ein Schwert.");
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

  private Room goToStoneCircle(TextAdventure adventure) {
    return goToClearing(adventure).option(ENTER_STONE_CIRCLE).choose();
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
