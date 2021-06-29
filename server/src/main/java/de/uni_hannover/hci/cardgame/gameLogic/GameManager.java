package de.uni_hannover.hci.cardgame.gameLogic;

import de.uni_hannover.hci.cardgame.gameLogic.Cards.CardColor;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.CardStack;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards;
import de.uni_hannover.hci.cardgame.gameLogic.Player.Player;

import java.util.ArrayList;

/**
 * This contains every function to execute the game and manage it.
 */
public class GameManager {
    public static CardStack cardStack_;
    public static ArrayList<int[]> visibleCards_;
    private final ArrayList<Player> players_;
    private CardColor trump_;

    // TODO init game into it's own function.
    // TODO Validate cards
    // TODO Implement attack and defense turn classes
    // TODO Implement rest of game logic
    public GameManager(int[] IDs) {
        this.players_ = new ArrayList<>();
        cardStack_ = new CardStack();
        visibleCards_ = new ArrayList<>();

        // TODO: When done with DEBUGGING merge shuffle into constructor
        cardStack_.shuffleList();
        createTrump();

        for (int id: IDs) {
            this.players_.add(new Player(id, "TestPlayer"));
        }
    }

    //TODO: Implement
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
        int returnValue = visibleCards_.size() * 2;
        if(visibleCards_.get(visibleCards_.size() - 1)[1] == -1) returnValue -= 1;
        return returnValue;
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

    //FIXME: Some params are unsure. A LOT
    /**
     * The format the the client can process.
     *
     * @param playerId id of the current player.
     * @return returns the state of the game.
     */
    public String gameBoardState(int playerId)
    {
        StringBuilder returnString = new StringBuilder();
        for (Player p: players_) {
            if (p.getId_() == playerId) {
                returnString.append(String.format("%s ", cardStack_.remainingCards()));     // StackSize
                returnString.append(String.format("%s ", trump_));                          // Trump color (Is a color not an int?)
                returnString.append(String.format("%s ", players_.size()));                 // Player count
                returnString.append(String.format("%s ", p.getId_()));                      // Player count
                returnString.append(String.format("%s ", p.getName_()));                    // Player name (Defender)
                returnString.append(String.format("%s ", p.getAmountOfHandCards()));        // Player hand cards amount (Defender)
                returnString.append(String.format("%s ", p.handCardsToString()));           // Player hand cards (Defender)
                returnString.append(String.format("%s ", p.isAttacker_()));                 // Player is attacker (Defender)
                returnString.append(String.format("%s ", p.isDefender_()));                 // Player is defender (Defender)
                /// PART MISSING?
                returnString.append(String.format("%s ", visibleCards_.size()));            // Amount of visible cards x2
                returnString.append(String.format("%s ", visibleCardsToString()));          // Visible cards
            }
        }
        return returnString.toString();
    }


    /// DEBUG FUNCTIONS
    public void printVisibleCards()
    {
        for (int[] arr:visibleCards_)
        {
            System.out.printf("%d ",arr[0]);
            System.out.printf("%d\n",arr[1]);
        }
        System.out.print("//////////////////////////////////\n");
    }
}
