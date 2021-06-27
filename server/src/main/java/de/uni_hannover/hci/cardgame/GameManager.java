package de.uni_hannover.hci.cardgame;

import de.uni_hannover.hci.cardgame.Cards.CardStack;
import de.uni_hannover.hci.cardgame.Player.Player;


/**
 * This contains every function to execute the game and manage it.
 */
public class GameManager {
    /** The card stack where everyone draws from.*/
    public static CardStack cardStack;
    /// For Debugging
    Player play;
    ///

    /**
     * Inits the game.
     */
    public GameManager()
    {
        cardStack = new CardStack();
        cardStack.shuffleList();

        /// Test Here
        this.play = new Player(4, "claus");
        System.out.println(cardStack);
        System.out.println(play);
        this.play.drawCards(10, cardStack);
        System.out.println(cardStack);
        System.out.println(play);
        ///
    }

    /**
     * The format the the client can process.
     * @param playerId id of the current player.
     * @return returns the state of the game.
     */
    public String gameBoardState(int playerId)
    {
        return String.format("drawPileHeight: {12}; trump: {3}; Player list: " +
                "[id :{2}, name: {yann}, handCardNumber: {4}, attacker: {false}, defender: {false}; " +
                "id :{1}, name: {patrick}, handCardNumber: {3}, attacker: {true}, defender: {false}; " +
                "id :{0}, name: {robert}, handCardNumber: {7}, attacker: {false}, defender: {true}]; " +
                "Visible Cards: [{13,15}; {27,32}; {49,50}; {52,-1};]; " +
                "Hand cards: [{11}; {12}; {14}]");
    }
}
