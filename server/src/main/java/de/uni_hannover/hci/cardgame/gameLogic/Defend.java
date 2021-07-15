package de.uni_hannover.hci.cardgame.gameLogic;

import de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards;

/**
 * Defend action of the bot.
 *
 * @author Yann Bernhard &lt;yann.bernhard@stud.uni-hannover.de&gt;
 * @author Sebastian Kiel &lt;sebastian.kiel@stud.uni-hannover.de&gt;
 * @author Patrick Schewe &lt;p.schewe@stud.uni-hannover.de&gt;
 * @author Robert Witteck &lt;robert.witteck@stud.uni-hannover.de&gt;
 */
public class Defend
{
    /**
     * Defend boolean.
     *
     * @param card the card
     * @return true if card was played
     */
    public static boolean defend(int card)
    {
        int[] toDefend = GameManager.visibleCards_.get(GameManager.visibleCards_.size() - 1);
        if (Cards.compareCards(GameManager.getTrump_(), toDefend[0], card) >= 0)
        {
            return false;
        }
        else if (Cards.compareCards(GameManager.getTrump_(), toDefend[0], card) < 0)
        {
            toDefend[1] = card;
            GameManager.visibleCards_.set(GameManager.visibleCards_.size() - 1, toDefend);
            return true;
        }
        return false;
    }
}
