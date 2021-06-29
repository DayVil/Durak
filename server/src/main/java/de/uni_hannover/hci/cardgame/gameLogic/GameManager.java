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
    private ArrayList<Player> players_;
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

    private String handCardsAmountOfPLayerToString(int ID) {
        for (Player p: players_) {
            if (p.getId_() == ID) {
                return String.format("%d ", p.getAmountOfHandCards());
            }
        }
        return "-1";
    }

    private String handCardsToString(int ID)
    {
        StringBuilder returnString = new StringBuilder();
        for (Player p: players_)
        {
            if(p.getId_() == ID)
            {
                for (Integer i: p.getHandCards_())
                {
                    returnString.append(String.format("%d ", i));
                }
            }
        }
        return returnString.append("-1").toString();
    }

    /**
     * The format the the client can process.
     *
     * @param playerId id of the current player.
     * @return returns the state of the game.
     */
    public String gameBoardState(int playerId)
    {
        String returnString = "drawPileHeight: {12}; trump: {3}; Player list: " +
                "[id :{2}, name: {yann}, handCardNumber: {4}, attacker: {false}, defender: {false}; " +
                "id :{1}, name: {patrick}, handCardNumber: {3}, attacker: {true}, defender: {false}; " +
                "id :{0}, name: {robert}, handCardNumber: {7}, attacker: {false}, defender: {true}]; " +
                "Visible Cards: [{13,15}; {27,32}; {49,50}; {52,-1};]; " +
                "Hand cards: [{11}; {12}; {14}]";

        return returnString;
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
