package de.uni_hannover.hci.cardgame.Player;

import de.uni_hannover.hci.cardgame.Cards.CardStack;

import java.util.ArrayList;
import java.util.List;


/**
 * This represents one of the 8 possible players.
 */
public class Player {
    /**
     * Id of the player
     */
    private int id;
    /**
     * Custom name of the player.
     */
    private String name;
    /**
     * The handcarts of the player. THIS MAY NEVER BE FINAL
     */
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

    public int getAmountOfCards() {
        return this.handCards.size();
    }

    // FIXME: Null is not allowed throws exception.
    // FIXME: The app crashes if the card stack is empty and you try to draw from it.

    /**
     * Draws one Card and pops it from the card stack.
     *
     * @param stack the card stack to be drawn and popped from.
     */
    private void drawCard(CardStack stack) {
        try {
            int firstCard = stack.getFirstCard();
            handCards.add(firstCard);
            stack.popFirstCard();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

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
        return String.format("ID: %s\tName: %s\tCards in hand: %s\n", getId(), getName(), handCards.toString());
    }
}
