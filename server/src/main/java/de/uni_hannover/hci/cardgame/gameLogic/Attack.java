package de.uni_hannover.hci.cardgame.gameLogic;

import static de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards.getCard;
import static de.uni_hannover.hci.cardgame.gameLogic.GameManager.visibleCards_;

public class Attack
{
    public static boolean attack (int card)
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
                if(getCard(visibleCard) == getCard(card))
                {
                    visibleCards_.add(new int[]{card, -1});
                    return true;
                }
            }
        }
        return false;
    }
}
