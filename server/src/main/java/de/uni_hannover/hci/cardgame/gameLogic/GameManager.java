package de.uni_hannover.hci.cardgame.gameLogic;

import de.uni_hannover.hci.cardgame.ServerNetwork;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.CardColor;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.CardStack;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards;
import de.uni_hannover.hci.cardgame.gameLogic.Player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * This contains every function to execute the game and manage it.
 */
public class GameManager
{
    public static CardStack drawPile_;
    public static final ArrayList<int[]> visibleCards_ = new ArrayList<>();
    private static final ArrayList<Player> players_ = new ArrayList<>();
    private static final ArrayList<Player> viewers_ = new ArrayList<>();
    private static CardColor trump_;
    public static int activeId_;
    public static int firstAttacker;


    // TODO: on playCard check for total cards that are able to be played as defender might not always have 6 cards
    public static void initGameManager(int[] IDs, String[] names)
    {
        drawPile_ = new CardStack();
        activeId_ = -1;
        createTrump();

        int i = 0;
        for (int id : IDs)
        {
            players_.add(new Player(id, names[i]));
            i++;
        }

        Collections.shuffle(players_);

        for (Player p : players_)
        {
            p.drawCards(6, drawPile_);
        }

        firstAttacker = players_.get(0).getId_();
        newTurn();
    }

    public static void newTurn ()
    {
        Player[] activePlayers = getActivePlayers(firstAttacker);
        activePlayers[0].setActive_(true);
        activePlayers[0].setAttacker_(true);
        activePlayers[1].setDefender_(true);
        refreshActiveID();
        sendGameStateToAll();
        System.out.println("Send GameState to all clients");
        boolean turnEnded = false;
        boolean defWon = false;
        while (!turnEnded)
        {
            Player activePlayer = null;
            for (Player p : players_)
            {
                if (p.isActive_())
                {
                    activePlayer = p;
                }
            }

            String lastAction;
            do
            {
                lastAction = Objects.requireNonNull(activePlayer).getLastAction_();
            } while (lastAction.equals("no action"));

            System.out.printf("Action %s in new Turn\n", lastAction);

            activePlayer.setLastAction_("no action");
            System.out.println("Action wait loop broke");
            switch (lastAction)
            {
                case "pass":
                {
                    if(activePlayer.isAttacker_())
                    {
                        if (activePlayers.length > 2)
                        {
                            if (!activePlayers[2].hasSkipped_())
                            {
                                switchAttacker(activePlayers);
                            }
                            else
                            {
                                defWon = true;
                                turnEnded = true;
                            }
                        }
                        else
                        {
                            defWon = true;
                            turnEnded = true;
                        }
                        break;
                    }
                    else
                    {
                        ServerNetwork.sendMessage(activePlayer.getId_(), gameBoardStateToString(activePlayer.getId_(), false));
                    }
                }
                case "take":
                {
                    if(activePlayer.isDefender_())
                    {
                        defWon = false;
                        turnEnded = true;
                        break;
                    }
                    else
                    {
                        ServerNetwork.sendMessage(activePlayer.getId_(), gameBoardStateToString(activePlayer.getId_(), false));
                    }
                }

                default:
                {
                    int card = Integer.parseInt(lastAction);
                    if (activePlayer.playCard(card))
                    {
                        if (activePlayer.isAttacker_())
                        {
                            activePlayers[0].setActive_(false);
                            activePlayers[0].setSkipped_(false);
                            activePlayers[1].setActive_(true);
                            if (activePlayers.length > 2)   activePlayers[2].setSkipped_(false);
                            activeId_ = activePlayers[1].getId_();
                        }
                        else
                        {
                            activePlayers[0].setActive_(true);
                            activePlayers[1].setActive_(false);
                            activeId_ = activePlayers[0].getId_();

                            if (visibleCards_.size() == 6)
                            {
                                defWon = true;
                                turnEnded = true;
                            }
                        }
                    }
                    else
                    {
                        ServerNetwork.sendMessage(activePlayer.getId_(), gameBoardStateToString(activePlayer.getId_(), false));
                    }
                    break;
                }
            }
            sendGameStateToAll();
        }
        endTurn(activePlayers, defWon);
    }

    public static void endTurn(Player[] activePlayers, boolean defenseWon)
    {
        for (Player p:activePlayers)
        {
            resetPlayer(p);
        }

        if (defenseWon)
        {
            visibleCards_.clear();
            activePlayers[0].drawCards(6 - activePlayers[0].getAmountOfHandCards(), drawPile_);
            if (activePlayers.length > 2)
            {
                activePlayers[2].drawCards(6 - activePlayers[2].getAmountOfHandCards(), drawPile_);
            }
            activePlayers[1].drawCards(6 - activePlayers[1].getAmountOfHandCards(), drawPile_);
            firstAttacker = activePlayers[1].getId_();
        }
        else
        {
            activePlayers[1].takeCards();
            activePlayers[0].drawCards(6 - activePlayers[0].getAmountOfHandCards(), drawPile_);
            if (activePlayers.length > 2)
            {
                activePlayers[2].drawCards(6 - activePlayers[2].getAmountOfHandCards(), drawPile_);
                firstAttacker = activePlayers[2].getId_();
            }
            else
            {
                firstAttacker = activePlayers[0].getId_();
            }
        }
        //Check for players that are left with no cards on their hands
        if (clearPlayers())
        {
            newTurn();
        }
        //Game END
        System.out.println("The Game has now officially ended!");
    }

    public static boolean clearPlayers()
    {
        for (Player p : players_)
        {
            if (p.getAmountOfHandCards() == 0)
            {
                viewers_.add(p);
                ////////////////////////////////////////////
                //Didn't test it yet, might break the for-loop
                players_.remove(p);
                ////////////////////////////////////////////
            }
        }

        return players_.size() >= 2;
    }

    //Will be used from the server
    public static void takeAction(String action, int id)
    {
        for (Player p : players_)
        {
            System.out.printf("Searching for id %d found %d\n", id, p.getId_());
            if (p.getId_() == id)
            {
                if (activeId_ == id)
                {
                    p.setLastAction_(action);
                    System.out.printf("Found player and set action %s for player\n", action);
                }
                break;
            }
        }
    }

    public static Player[] getActivePlayers(int idFirstPlayer)
    {
        Player[] returnArray;
        if (players_.size() < 3)    returnArray = new Player[2];
        else                        returnArray = new Player[3];
        int index = 0;
        for (Player p : players_)
        {
            if (p.getId_() == idFirstPlayer)  break;
            index++;
        }
        returnArray[0] = players_.get(index++);

        if (index == players_.size())
        {
            index = 0;
        }
        returnArray[1] = players_.get(index++);


        if (players_.size() >= 3)
        {
            if (index == players_.size())
            {
                index = 0;
            }
            returnArray[2] = players_.get(index);
        }

        return returnArray;
    }

    public static Player[] switchAttacker(Player[] activePlayers)
    {
        activePlayers[0].setActive_(false);
        activePlayers[0].setAttacker_(false);
        activePlayers[0].setSkipped_(true);

        Player helper = activePlayers[0];
        activePlayers[0] = activePlayers[2];
        activePlayers[2] = helper;

        activePlayers[0].setActive_(true);
        activePlayers[0].setAttacker_(true);
        activeId_ = activePlayers[0].getId_();

        return activePlayers;
    }

    public static CardColor getTrump_ ()
    {
        return trump_;
    }

    private static void createTrump()
    {
        trump_ = Cards.getColor(drawPile_.getLastCard());
    }

    public static int countVisibleCards()
    {
        if (visibleCards_.size() == 0) return 0;

        int returnValue = visibleCards_.size() * 2;

        if (visibleCards_.get(visibleCards_.size() - 1)[1] == -1)
        {
            returnValue--;
        }

        return returnValue;
    }

    private static void refreshActiveID()
    {
        for (Player p : players_)
        {
            if (p.isActive_())
            {
                activeId_ = p.getId_();
                break;
            }
        }
    }

    private static void resetPlayer (Player player)
    {
        player.setActive_(false);
        player.setAttacker_(false);
        player.setDefender_(false);
        player.setSkipped_(false);
        player.setLastAction_("no action");
    }

    private static String visibleCardsToString()
    {
        StringBuilder returnString = new StringBuilder();
        for (int[] arr : visibleCards_)
        {
            returnString.append(String.format("%d ", arr[0]));
            if(arr[1] < 11) continue;
            returnString.append(String.format("%d ", arr[1]));
        }
        return returnString.toString();
    }

    /**
     * The format the the client can process.
     *
     * @param playerId id of the current player.
     * @return returns the state of the game.
     */
    public static String gameBoardStateToString(int playerId, boolean wasSuccessfull)
    {
        // As per String convention specified in ServerNetwork.java this would be the correct approach to generate the String

        StringBuilder returnString = new StringBuilder();

        returnString.append(String.format("%s ", drawPile_.remainingCards()));                 // StackSize
        returnString.append(String.format("%s ", Cards.getColorInt(trump_)));                   // Trump color as Int
        returnString.append(String.format("%s ", players_.size()));                             // Player count

        for (Player p: players_)
        {
            returnString.append(String.format("%s ", p.getId_()));                              // Player id
            returnString.append(String.format("%s ", p.getName_()));                            // Player name
            returnString.append(String.format("%s ", p.getAmountOfHandCards()));                // Player hand cards amount
            returnString.append(String.format("%s ", p.isAttackerInt_()));                      // Player is attacker as Int
            returnString.append(String.format("%s ", p.isDefenderInt_()));                      // Player is defender as Int
            returnString.append(String.format("%s ", p.isActiveInt_()));                        // Player is active
        }

        returnString.append(String.format("%s ", countVisibleCards()));                         // Amount of visible cards
        returnString.append(String.format("%s", visibleCardsToString()));                       // Visible cards

        for (Player p : players_)
        {
            if (p.getId_() == playerId)
            {
                returnString.append(String.format("%s ", p.getAmountOfHandCards()));            // specified players hand cards amount
                returnString.append(String.format("%s", p.handCardsToString()));                // specified players hand cards
            }
        }

        if (wasSuccessfull) returnString.append(String.format("%s", 1));
        else returnString.append(String.format("%s", 0));                                       // Was successful
        return returnString.toString();
    }

    public static void sendGameStateToAll()
    {
        for (Player p : players_)
        {
            ServerNetwork.sendMessage(p.getId_(), gameBoardStateToString(p.getId_(), true));
        }
    }


}
