package de.uni_hannover.hci.cardgame.gameLogic.Player;

import de.uni_hannover.hci.cardgame.gameLogic.Attack;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.CardStack;
import de.uni_hannover.hci.cardgame.gameLogic.Defend;
import de.uni_hannover.hci.cardgame.gameLogic.GameManager;

import java.util.ArrayList;


/**
 * This represents one of the 8 possible players.
 *
 * @version 18.07.2021
 * @author Yann Bernhard &lt;yann.bernhard@stud.uni-hannover.de&gt;
 * @author Sebastian Kiel &lt;sebastian.kiel@stud.uni-hannover.de&gt;
 * @author Patrick Schewe &lt;p.schewe@stud.uni-hannover.de&gt;
 * @author Robert Witteck &lt;robert.witteck@stud.uni-hannover.de&gt;
 */
public class Player
{
    private final int id_; //is equal to Client ID

    /**
     * The username of the player
     */
    private String name_;
    private final ArrayList<Integer> handCards_;

    /**
     * Truth Value if player is attacker
     */
    private boolean isAttacker_;

    /**
     * Truth Value if player is defender
     */
    private boolean isDefender_;

    /**
     * Truth Value if player is active
     */
    private boolean isActive_;

    /**
     * Truth Value if player is a bot
     */
    private boolean isBot_;

    /**
     * Truth Value if player has skipped
     */
    private boolean skipped_;

    /**
     * The String representation of the last action
     */
    private volatile String lastAction_;

    /**
     * The constructor of the player
     *
     * @param id        The id of the player
     * @param name      The username of the player
     */
    public Player(int id, String name)
    {
        id_ = id;
        name_ = name;
        setBot_(false);
        resetFlags();
        handCards_ = new ArrayList<>();
    }

    /**
     * Setter for isBot_
     *
     * @param bot_ Truth value if player is bot
     */
    public void setBot_ (boolean bot_)
    {
        isBot_ = bot_;
    }

    /**
     * Getter for isBot_
     *
     * @return Truth value if player is bot
     */
    public boolean isBot_ ()
    {
        return isBot_;
    }

    /**
     * Getter for lastAction_
     *
     * @return The String representation of the last action
     */
    public String getLastAction_ ()
    {
        return lastAction_;
    }

    /**
     * Setter for lastAction_
     *
     * @param lastAction    The String representation of the last action
     */
    public void setLastAction_ (String lastAction)
    {
        System.out.printf("Action: %s, ID: %d\n", lastAction, id_);
        lastAction_ = lastAction;
    }

    /**
     * Getter for id_
     *
     * @return The id of the player
     */
    public int getId_()
    {
        return id_;
    }

    /**
     * Getter for name_
     *
     * @return The username of the player
     */
    public String getName_()
    {
        return name_;
    }

    /**
     * Setter for name_
     *
     * @param name  The new username of the the player (only accessed by bots)
     */
    public void setName_(String name)
    {
        name_ = name;
    }

    /**
     * Getter for isAttacker_
     *
     * @return Truth value if player is attacker
     */
    public boolean isAttacker_()
    {
        return isAttacker_;
    }

    /**
     * Setter for isAttacker_
     *
     * @param attacker  Truth value if player is attacker
     */
    public void setAttacker_(boolean attacker)
    {
        isAttacker_ = attacker;
    }

    /**
     * Setter for skipped_
     *
     * @param played    Truth value if player has skipped
     */
    public void setSkipped_ (boolean played)
    {
        skipped_ = played;
    }

    /**
     * Getter for skipped_
     *
     * @return  Truth value if player has skipped_
     */
    public boolean hasSkipped_ ()
    {
        return skipped_;
    }

    /**
     * Getter for isDefender_
     *
     * @return  Truth value if player is defender
     */
    public boolean isDefender_()
    {
        return isDefender_;
    }

    /**
     * Setter for isDefender_
     *
     * @param defender_ Truth value if player is defender
     */
    public void setDefender_(boolean defender_)
    {
        isDefender_ = defender_;
    }

    /**
     * Getter for isActive_
     *
     * @return  Truth value if player is active
     */
    public boolean isActive_()
    {
        return isActive_;
    }

    /**
     * Getter for isActive_ as an int
     *
     * @return  Truth value if player is active as an int, 1 if true, 0 if false
     */
    public int isActiveInt_()
    {
        if (isActive_)  return 1;
                        return 0;
    }

    /**
     * Setter for isActive_
     *
     * @param active_   Truth value if player is active
     */
    public void setActive_(boolean active_)
    {
        isActive_ = active_;
    }

    /**
     * Getter for handCards_
     *
     * @return  The Arraylist of handcards of the player
     */
    public ArrayList<Integer> getHandCards_ ()
    {
        return handCards_;
    }

    /**
     * Getter for handCards_.size()
     *
     * @return  amount of handcards in a players hand
     */
    public int getAmountOfHandCards()
    {
        return this.handCards_.size();
    }

    /**
     * Getter for isAttacker_ as an int
     *
     * @return  Truth value if player is attacker as an int, 1 if true, 0 if false
     */
    public int isAttackerInt_()
    {
        if (isAttacker_)    return 1;
                            return 0;
    }

    /**
     * Getter for isDefender_ as an int
     *
     * @return  Truth value if player is defender as an int, 1 if true, 0 if false
     */
    public int isDefenderInt_()
    {
        if (isDefender_)
        {
            return 1;
        }
        return 0;
    }

    /**
     * Resets Attacker, Defender, Active and skipped boolean to false, sets lastAction to 'no action'
     */
    public void resetFlags()
    {
        setAttacker_(false);
        setDefender_(false);
        setActive_(false);
        setSkipped_(false);
        setLastAction_("no action");
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

    /**
     * Takes all visible cards from the middle
     */
    public void takeCards()
    {
        for (int[] intArr : GameManager.visibleCards_)
        {
            for (int card : intArr)
            {
                if (card != -1) handCards_.add(card);
            }
        }
        GameManager.visibleCards_.clear();
    }

    /**
     * Tries to play a clicked card from the handcards of the player
     *
     * @param card  The id of the card to play
     * @return      Truth value if player was able to play card or not
     */
    public boolean playCard(int card)
    {
        if(!isActive_) return false;

        if(isDefender_ && !isAttacker_)
        {
            for (int i = 0; i < handCards_.size(); i++)
            {
                if(handCards_.get(i) == card)
                {
                    if (Defend.defend(card))
                    {
                        handCards_.remove(i);
                        return true;
                    }
                    return false;
                }
            }
            // Should be unreachable
            System.out.println("User tried to use card that is not on hand!");
            return false;
        }

        if(!isDefender_ && isAttacker_)
        {
            for (int i = 0; i < handCards_.size(); i++)
            {
                if(handCards_.get(i) == card)
                {
                    if (Attack.attack(card))
                    {
                        handCards_.remove(i);
                        setSkipped_(false);
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

    /**
     * The toString method of only a players handcards
     *
     * @return A complete String representation of all cards in the hand of the player
     */
    public String handCardsToString()
    {
        StringBuilder returnString = new StringBuilder();
        for (Integer i: handCards_)
        {
            returnString.append(String.format("%s ", i));
        }
        return returnString.toString();
    }

    /**
     * The toString method of the player.
     *
     * @return A complete String representation of all relevant information of the player
     */
    @Override
    public String toString()
    {
        return String.format("ID: %s\tName: %s\tCards in hand: %s", getId_(), getName_(), handCards_.toString());
    }
}
