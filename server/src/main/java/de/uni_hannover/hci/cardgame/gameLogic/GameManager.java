package de.uni_hannover.hci.cardgame.gameLogic;

import de.uni_hannover.hci.cardgame.gameLogic.Cards.CardColor;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.CardStack;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards;
import de.uni_hannover.hci.cardgame.gameLogic.Player.Player;

import java.util.ArrayList;

//TODO add player
/**
 * This contains every function to execute the game and manage it.
 */
public class GameManager
{
    public static CardStack cardStack_;
    public static ArrayList<int[]> visibleCards_ = new ArrayList<>();
    private ArrayList<Player> players_;
    private CardColor trump_;

    // TODO init game into it's own function.
    // TODO Validate cards
    // TODO Implement attack and defense turn classes
    // TODO Implement rest of game logic
    public GameManager(int[] IDs)
    {
        this.players_ = new ArrayList<>();
        cardStack_ = new CardStack();
        // TODO: When done with DEBUGGING merge shuffle into constructor
        cardStack_.shuffleList();
        trump_ = createTrump();

        for (int i = 0; i < IDs.length; i++)
        {
            this.players_.add(new Player(IDs[i], "BHGUJZGDFUZGSAUJDGFSAUDGAUIJSGDAUS"));
        }
    }

    //TODO: Implement
    public void initGame() {
        /// SANDBOX
        System.out.println(this.players_);
        Player play = this.players_.get(1);
        play.drawCards(52, cardStack_);
        System.out.println(cardStack_);
        System.out.println(trump_);
        /*
        System.out.println(play);
        play.setAttacker_(true);
        printVisibleCards();
        System.out.println(play);
        play.playCard(45);
        printVisibleCards();
        System.out.println(play);
        play.setAttacker_(false);
        play.setDefender_(true);
        play.playCard(22);
        printVisibleCards();
        System.out.println(play);
        play.setAttacker_(true);
        play.setDefender_(false);
        play.playCard(35);
        printVisibleCards();
        System.out.println(play);
        play.setAttacker_(false);
        play.setDefender_(true);
        play.playCard(55);
        printVisibleCards();
        System.out.println(play);
        */
        ///
    }

    private CardColor createTrump()
    {
        CardColor trump = Cards.getColor(cardStack_.getLastCard());
        return trump;
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

    private String handCardsOfPLayerToString(int ID)
    {
        for (Player p: players_)
        {
            if(p.getId_() == ID)
            {
                return String.format("%d ",p.getAmountOfHandCards());
            }
        }
        return "-1";
    }

    private String handCardsToString(int ID)
    {
        StringBuilder returnString = new StringBuilder();
        returnString.append("");
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
        return returnString.toString();
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



    // DEBUG FUNCTIONS
    public void printVisibleCards()
    {
        for (int[] arr:visibleCards_)
        {
            System.out.printf("%d ",arr[0]);
            System.out.printf("%d\n",arr[1]);
        }
        System.out.printf("//////////////////////////////////\n");
    }
}
