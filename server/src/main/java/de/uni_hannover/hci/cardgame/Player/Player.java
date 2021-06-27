package de.uni_hannover.hci.cardgame.Player;

import de.uni_hannover.hci.cardgame.Cards.CardStack;

import java.util.ArrayList;
import java.util.List;


/**
 * This represents one of the 8 possible players.
 */
public class Player {
    /** Id of the player */
    private int id;
    /** Custom name of the player. */
    private String name;
    /** The handcarts of the player.*/
    private final List<Integer> handCards;

    public Player(int id, String name) {
        setId(id);
        setName(name);
        handCards = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    // FIXME: Null is not allowed throws exception.
    /**
     * Draws one Card and pops it from the card stack.
     * @param stack the card stack to be drawn and popped from.
     */
    private void drawCard(CardStack stack) {
        handCards.add(stack.getFirstCard());
        stack.popFirstCard();
    }

    /**
     * Draws cards and pops it from the card stack.
     * @param amount amount to be drawn.
     * @param stack the stack to be drawn and popped from.
     */
    public void drawCards(int amount, CardStack stack) {
        for (int i = 0; i < amount; i++) {
            drawCard(stack);
        }
    }


    // TODO: Correct formatting.
    @Override
    public String toString() {
        return String.format("ID: %s\tName: %s\t Cards in hand: %s", getId(), getName(), handCards.toString());
    }
}
