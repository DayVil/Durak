package de.uni_hannover.hci.cardgame.gameLogic;

import de.uni_hannover.hci.cardgame.gameLogic.Cards.CardColor;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.CardStack;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards;
import de.uni_hannover.hci.cardgame.gameLogic.Player.Player;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This contains every function to execute the game and manage it.
 */
public class GameManager {
    public static CardStack cardStack_;
    public static ArrayList<int[]> visibleCards_;
    private final ArrayList<Player> players_;
    private static CardColor trump_;
    public static int activeId_;

    // TODO implement run
    // TODO Implement rest of game logic
    // TODO figure it out how to to swap between attackers
    // Problem finding both attackers
    public GameManager(int[] IDs) {
        activeId_ = -1;
        this.players_ = new ArrayList<>();
        cardStack_ = new CardStack();
        visibleCards_ = new ArrayList<>();
        createTrump();

        for (int id: IDs) {
            this.players_.add(new Player(id, "TestPlayer"));
        }
        Collections.shuffle(players_);
    }

    public static CardColor getTrump_ ()
    {
        return trump_;
    }

    public void initGame() {
        int beginning = 0;
        players_.get(beginning).setActive_(true);
        players_.get(beginning).setAttacker_(true);
        refreshActiveID();

        players_.get(beginning + 1).setDefender_(true);
    }

    public void sandBox() {
        /// SANDBOX
        players_.get(0).drawCards(7, cardStack_);
        int[] vis = {54, 60};
        visibleCards_.add(vis);
        System.out.println(gameBoardState(0));
        ///
    }

    private void createTrump()
    {
        trump_ = Cards.getColor(cardStack_.getLastCard());
    }

    private int countVisibleCards()
    {
        // per int-array there is 2 cards lying down, one from attacker and the second from defender
        int returnValue = visibleCards_.size() * 2;

        // looks for empty spot of defenders card, if it is empty the amount is one less.
        // as there is no way we have multiple open spots this is the only thing we have to check here
        if(visibleCards_.get(visibleCards_.size() - 1)[1] == -1) returnValue -= 1;

        return returnValue;
    }

    public boolean isVisibleCardsfull() {
        if (visibleCards_.size() == 6) {
            if (visibleCards_.get(5)[1] != -1) {
                return true;
            }
        }
        return false;
    }

    public void newTurn(boolean defWon) {
        int pos = 0;
        boolean found = false;

        for (Player p : players_) {
            if(p.isDefender_()) { //Found defender
                found = true;
            }

            if (!found) { //Stop counting
                pos++;
            }

            p.resetFlags(); // Sets all flags false
        }

        if(!defWon) {
            pos++;
        }

        players_.get(pos).setAttacker_(true);
        players_.get(pos + 1).setDefender_(true);

        refreshActiveID();
    }

    public void refreshActiveID() {
        for (Player p: players_) {
            if (p.isActive_()) {
                activeId_ = p.getId_();
            }
        }
    }

    private String visibleCardsToString()
    {
        StringBuilder returnString = new StringBuilder();
        for (int[] arr:visibleCards_)
        {
            returnString.append(String.format("%d ", arr[0]));
            if(arr[1] < 11) continue;
            returnString.append(String.format("%d ", arr[1]));
        }
        return returnString.toString();
    }

    public boolean playPCard(int id, int card) {
        Player player = null;

        // Gets the Player
        for (Player p: players_) {
            if (id == p.getId_()) {
                player = p;
            }
        }

        // Player doesn't exist
        if (player == null) return false;


        return player.playCard(card);
    }

    /**
     * The format the the client can process.
     *
     * @param playerId id of the current player.
     * @return returns the state of the game.
     */
    public String gameBoardState(int playerId)
    {
        // As per String convention specified in ServerNetwork.java this would be the correct approach to generate the String

        StringBuilder returnString = new StringBuilder();

        returnString.append(String.format("%s ", cardStack_.remainingCards()));                 // StackSize
        returnString.append(String.format("%s ", Cards.getColorInt(trump_)));                   // Trump color as Int
        returnString.append(String.format("%s ", players_.size()));                             // Player count

        for (Player p: players_)
        {
            returnString.append(String.format("%s ", p.getId_()));                              // Player id
            returnString.append(String.format("%s ", p.getName_()));                            // Player name
            returnString.append(String.format("%s ", p.getAmountOfHandCards()));                // Player hand cards amount
            returnString.append(String.format("%s ", p.isAttackerInt_()));                      // Player is attacker as Int
            returnString.append(String.format("%s ", p.isDefenderInt_()));                      // Player is defender as Int
            returnString.append(String.format("%s ", p.isActiveInt_()));                        // Player is active
        }

        returnString.append(String.format("%s ", countVisibleCards()));                         // Amount of visible cards
        returnString.append(String.format("%s", visibleCardsToString()));                       // Visible cards

        for (Player pID : players_)
        {
            if (pID.getId_() == playerId)
            {
                returnString.append(String.format("%s ", pID.getAmountOfHandCards()));          // specified players hand cards amount
                returnString.append(String.format("%s", pID.handCardsToString()));              // specified players hand cards
            }
        }

        returnString.append(String.format("%s", 1));                                            // Was successful
        return returnString.toString();
    }
}
