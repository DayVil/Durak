package de.uni_hannover.hci.cardgame.gameLogic.Player;

import de.uni_hannover.hci.cardgame.gameLogic.Attack;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.CardStack;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards;
import de.uni_hannover.hci.cardgame.gameLogic.Defend;

import java.util.ArrayList;


/**
 * This represents one of the 8 possible players.
 */
public class Player
{
    private int id_; //is Equal to Client ID
    private String name_;
    private final ArrayList<Integer> handCards_;
    private boolean isActiveAttacker_;
    private boolean isAttacker_;
    private boolean isDefender_;
    private boolean isActive_;
    private boolean playedCard_;

    public Player(int id, String name) {
        resetFlags();

        setId_(id);
        setName_(name);
        handCards_ = new ArrayList<>();
    }

    public int getId_()
    {
        return id_;
    }

    private void setId_(int id_)
    {
        this.id_ = id_;
    }

    public String getName_() {
        return name_;
    }

    private void setName_(String name_)
    {
        this.name_ = name_;
    }

    public boolean isActiveAttacker_()
    {
        return isActiveAttacker_;
    }

    public void setActiveAttacker_(boolean activeAttacker_)
    {
        this.isActiveAttacker_ = activeAttacker_;
    }

    public boolean isAttacker_() { return isAttacker_; }

    public void setAttacker_(boolean attacker) { isAttacker_ = attacker; }

    public void setPlayedCard_(boolean played) { playedCard_ = played; }

    public boolean getPlayedCard_() { return playedCard_; }

    public boolean isDefender_()
    {
        return isDefender_;
    }

    public void setDefender_(boolean defender_) {
        isDefender_ = defender_;
    }

    public boolean isActive_() {
        return isActive_;
    }

    public int isActiveInt_() {
        if (isActive_) return 1;
        return 0;
    }

    public void setActive_(boolean active_) {
        isActive_ = active_;
    }

    private ArrayList<Integer> getHandCards_() {
        return handCards_;
    }

    public int getAmountOfHandCards()
    {
        return this.handCards_.size();
    }

    public int isAttackerInt_()
    {
        if (isActiveAttacker_)
        {
            return 1;
        }
        return 0;
    }

    public int isDefenderInt_()
    {
        if (isDefender_)
        {
            return 1;
        }
        return 0;
    }

    public void resetFlags() {
        setActiveAttacker_(false);
        setDefender_(false);
        setActive_(false);
        setPlayedCard_(false);
    }

    /**
     * Draws one Card and pops it from the card stack.
     *
     * @param stack the card stack to be drawn and popped from.
     */
    private void drawCard(CardStack stack)
    {
        if (stack == null || stack.remainingCards() == 0)
        {
            System.out.print("Stack was empty when trying to pull from stack\n");
            return;
        }
        int firstCard = stack.getFirstCard();
        handCards_.add(firstCard);
        stack.popFirstCard();
    }

    /**
     * Draws cards and pops it from the card stack.
     * @param amount amount to be drawn.
     * @param stack the stack to be drawn and popped from.
     */
    public void drawCards(int amount, CardStack stack)
    {
        for (int i = 0; i < amount; i++)
        {
            drawCard(stack);
        }
    }

    public boolean playCard(int card)
    {
        if(!isActive_) return false;

        if(isDefender_ && !isActiveAttacker_)
        {
            for (int i = 0; i < handCards_.size(); i++)
            {
                if(handCards_.get(i) == card)
                {
                    if (Defend.defend(card))
                    {
                        handCards_.remove(i);
                        setPlayedCard_(true);
                        return true;
                    }
                    return false;
                }
            }
            // Should be unreachable
            System.out.println("User tried to use card that is not on hand!");
            return false;
        }

        if(!isDefender_ && isActiveAttacker_)
        {
            for (int i = 0; i < handCards_.size(); i++)
            {
                if(handCards_.get(i) == card)
                {
                    if (Attack.attack(card))
                    {
                        handCards_.remove(i);
                        setPlayedCard_(true);
                        return true;
                    }
                    return false;
                }
            }
            // Should be unreachable
            System.out.println("User tried to use card that is not on hand!");
            return false;
        }
        System.out.println("User is neither Attacker, nor Defender OR Attacker AND Defender and is marked as Active!");
        return false;
    }

    public String handCardsToString()
    {
        StringBuilder returnString = new StringBuilder();
        for (Integer i: handCards_)
        {
            returnString.append(String.format("%s ", i));
        }
        return returnString.toString();
    }

    public String handCardsValColToString() {
        StringBuilder retStr = new StringBuilder();

        for (Integer i: handCards_) {
            retStr.append(String.format("[%s %s: %s] ", Cards.getCard(i), Cards.getColor(i), i));
        }

        return retStr.toString();
    }

    @Override
    public String toString() {
        return String.format("ID: %s\tName: %s\tCards in hand: %s", getId_(), getName_(), handCards_.toString());
    }
}
