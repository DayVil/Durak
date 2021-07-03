package de.uni_hannover.hci.cardgame.gameLogic.Cards;

import java.util.ArrayList;
import java.util.Scanner;

public class ParsedServerMessage
{
    int drawPileHeight_;
    CardColor trumpColor_;
    ArrayList<Player> players_ = new ArrayList<>();
    ArrayList<int[]> visibleCards_ = new ArrayList<>();
    ArrayList<Integer> handCards_ = new ArrayList<>();
    Boolean wasSuccessful_;

    public int getDrawPileHeight_()
    {
        return drawPileHeight_;
    }

    public CardColor getTrumpColor_()
    {
        return trumpColor_;
    }

    public ArrayList<Player> getPlayers_()
    {
        return players_;
    }

    public ArrayList<int[]> getVisibleCards_()
    {
        return visibleCards_;
    }

    public ArrayList<Integer> getHandCards_()
    {
        return handCards_;
    }

    public Boolean getWasSuccessful_()
    {
        return wasSuccessful_;
    }

    public class Player
    {
        int id_;
        String name_;
        int handCardAmount_;
        boolean attacker_;
        boolean defender_;
        boolean active_;

        public Player(int id, String name, int handCardAmount, int attacker, int defender, int active)
        {
            id_ = id;
            name_ = name;
            handCardAmount_ = handCardAmount;
            attacker_ = (attacker == 1);
            defender_ = (defender == 1);
            active_ = (active == 1);
        }

        public int getId_()
        {
            return id_;
        }

        public String getName_()
        {
            return name_;
        }

        public int getHandCardAmount_()
        {
            return handCardAmount_;
        }

        public boolean isAttacker_()
        {
            return attacker_;
        }

        public boolean isDefender_()
        {
            return defender_;
        }

        public boolean isActive_()
        {
            return active_;
        }
    }

    public ParsedServerMessage(String serverMsg)
    {
        Scanner s = new Scanner(serverMsg);
        drawPileHeight_ = s.nextInt();
        trumpColor_ = Cards.intToCardColor(s.nextInt());
        int playerCount = s.nextInt();
        for(int i = 0; i < playerCount; i++)
        {
            int id = s.nextInt();
            String name = s.next();
            int handCardAmount = s.nextInt();
            int attacker = s.nextInt();
            int defender = s.nextInt();
            int active = s.nextInt();
            Player player = new Player(id, name, handCardAmount,attacker, defender, active);
            players_.add(player);
        }
        int visibleCardCount = s.nextInt();
        for(int i = 0; i < visibleCardCount; i++)
        {
            int[] arr = new int[2];
            arr[0] = s.nextInt();
            if(i + 1 < visibleCardCount)
            {
                arr[1] = s.nextInt();
                i++;
            }
            else
            {
                arr[1] = -1;
            }
            visibleCards_.add(arr);
        }
        int handCardCount = s.nextInt();
        for(int i = 0; i < handCardCount; i++)
        {
            handCards_.add(s.nextInt());
        }
        wasSuccessful_ = (s.nextInt() == 1);
    }

}
