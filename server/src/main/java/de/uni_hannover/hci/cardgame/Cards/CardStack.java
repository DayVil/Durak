package de.uni_hannover.hci.cardgame.Cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Creates a card stack between the values: int 11 - 63
 */
public class CardStack {
    /** The stack of the  cards. */
    private final List<Integer> stack;

    /**
     * Creates the deck.
     */
    public CardStack() {
        int cardStart = 11;
        int cardEnd = 62;

        this.stack = new ArrayList<>();
        for(int i = cardStart; i <= cardEnd; i++) {
            this.stack.add(i);
        }
    }

    /**
     * Creates a readable string of the deck.
     * @return the described string.
     */
    @Override
    public String toString() {
        String str = "";
        for (int x:
             this.stack) {
            str += String.format("Colour: %s\tCValue: %s\tValue: %s\n", Cards.getColor(x), Cards.getCardValue(x), Cards.getCard(x));
        }
        return str;
    }

    /**
     * Shuffles the card stack.
     */
    public void shuffleList() {
        Collections.shuffle(stack);
        Collections.shuffle(stack);
    }

    /**
     * Size of the deck.
     * @return Size of the deck.
     */
    public int remainingCards() {
        return stack.size();
    }

    /**
     * Draws the first card of the deck.
     * @return Draws the first card of the deck.
     */
    public Integer getFirstCard() {
        return stack.get(0);
    }

    /**
     * Pops the first card of the deck.
     */
    public void popFirstCard() {
        stack.remove(0);
    }
}
