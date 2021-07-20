package de.uni_hannover.hci.cardgame.gameLogic;

import static de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards.getCard;
import static de.uni_hannover.hci.cardgame.gameLogic.GameManager.visibleCards_;

/**
 * Attack action of the player.
 *
 * @version 18.07.2021
 * @author Yann Bernhard &lt;yann.bernhard@stud.uni-hannover.de&gt;
 * @author Sebastian Kiel &lt;sebastian.kiel@stud.uni-hannover.de&gt;
 * @author Patrick Schewe &lt;p.schewe@stud.uni-hannover.de&gt;
 * @author Robert Witteck &lt;robert.witteck@stud.uni-hannover.de&gt;
 */
public class Attack
{
    /**
     * Tries to play a given Card for the offense
     *
     * @param card  the id of card
     * @return      true if card was played
     */
    public static boolean attack(int card)
    {
        // No card is visible, first card will be played
        if (visibleCards_.size() == 0)
        {
            visibleCards_.add(new int[]{card, -1});
            return true;
        }

        // at least 2 cards are visible, new attack card will be played
        for (int[] intArr : visibleCards_)
        {
            for (int visibleCard : intArr)
            {
                if (getCard(visibleCard) == getCard(card))
                {
                    visibleCards_.add(new int[]{card, -1});
                    return true;
                }
            }
        }
        return false;
    }
}
