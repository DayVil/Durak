package de.uni_hannover.hci.cardgame.gameLogic;

import de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards;

public class Defend
{
    public static boolean defend(int card)
    {
        int[] toDefend = GameManager.visibleCards_.get(GameManager.visibleCards_.size() - 1);
        if (Cards.compareCards(GameManager.getTrump_(), toDefend[0], card) <= 0)
        {
            return false;
        }
        else if (Cards.compareCards(GameManager.getTrump_(), toDefend[0], card) > 0)
        {
            toDefend[1] = card;
            GameManager.visibleCards_.set(GameManager.visibleCards_.size() - 1, toDefend);
            return true;
        }
        return false;
    }
}
