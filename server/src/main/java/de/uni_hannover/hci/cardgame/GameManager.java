package de.uni_hannover.hci.cardgame;

import de.uni_hannover.hci.cardgame.Cards.CardStack;
import de.uni_hannover.hci.cardgame.Player.Player;

import java.util.ArrayList;
import java.util.List;


/**
 * This contains every function to execute the game and manage it.
 */
public class GameManager {
    /**
     * The card stack where everyone draws from.
     */
    public static CardStack cardStack;
    private final List<Player> player;

    //TODO init game into it's own function.

    /**
     * Inits the game.
     */
    public GameManager(String[] args) {
        player = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            player.add(new Player(i, args[i]));
        }

        /// Make function
        cardStack = new CardStack();
        cardStack.shuffleList();
        ///
    }

    /**
     * The format the the client can process.
     * @param playerId id of the current player.
     * @return returns the state of the game.
     */
    public String gameBoardState(int playerId)
    {
        return "drawPileHeight: {12}; trump: {3}; Player list: " +
                "[id :{2}, name: {yann}, handCardNumber: {4}, attacker: {false}, defender: {false}; " +
                "id :{1}, name: {patrick}, handCardNumber: {3}, attacker: {true}, defender: {false}; " +
                "id :{0}, name: {robert}, handCardNumber: {7}, attacker: {false}, defender: {true}]; " +
                "Visible Cards: [{13,15}; {27,32}; {49,50}; {52,-1};]; " +
                "Hand cards: [{11}; {12}; {14}]";
    }
}
