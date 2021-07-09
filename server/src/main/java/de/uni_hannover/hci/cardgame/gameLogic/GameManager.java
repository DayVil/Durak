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
    public static int firstAttacker;


    // TODO: on playCard check for total cards that are able to be played as defender might not always have 6 cards
    public static void initGameManager(int[] IDs, String[] names)
    {
        drawPile_ = new CardStack();
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

            System.out.printf("newTurn waiting for action ID %d\n", Objects.requireNonNull(activePlayer).getId_());
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
                        if (activePlayers.length > 2 && !activePlayers[2].hasSkipped_() && activePlayers[2].getAmountOfHandCards() != 0)
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
                        ServerNetwork.sendMessage(activePlayer.getId_(), gameBoardStateToString(activePlayer.getId_(), false));
                    }
                    break;
                }
                case "take":
                {
                    if(activePlayer.isDefender_())
                    {
                        throwIn(activePlayers);

                        defWon = false;
                        turnEnded = true;
                    }
                    else
                    {
                        ServerNetwork.sendMessage(activePlayer.getId_(), gameBoardStateToString(activePlayer.getId_(), false));
                    }
                    break;
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
                            if (activePlayers.length > 2 && activePlayers[2].getAmountOfHandCards() != 0)   activePlayers[2].setSkipped_(false);
                        }
                        else
                        {
                            if (activePlayer.getAmountOfHandCards() == 0 || visibleCards_.size() == 6)
                            {
                                defWon = true;
                                turnEnded = true;
                            }
                            else
                            {
                                activePlayers[0].setActive_(true);
                                activePlayers[1].setActive_(false);
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
            if (activePlayer.getAmountOfHandCards() == 0)
            {
                switchAttacker(activePlayers);
            }
            sendGameStateToAll();
        }
        endTurn(activePlayers, defWon);
    }

    public static void endTurn(Player[] activePlayers, boolean defenseWon)
    {
        for (Player p:activePlayers)
        {
            p.resetFlags();
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
                if (activePlayers[2].getId_() == firstAttacker)
                {
                    firstAttacker = activePlayers[0].getId_();
                }
                else
                {
                    firstAttacker = activePlayers[2].getId_();
                }
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

    private static void throwIn(Player[] players)
    {
        players[0].setActive_(true);
        players[1].setActive_(false);

        while(visibleCards_.size() < 6)
        {
            sendGameStateToAll();
            if (players[0].getAmountOfHandCards() == 0)     switchAttacker(players);
            System.out.printf("ThrowIN waiting for action ID %d\n", players[0].getId_());
            String lastAction;
            do
            {
                lastAction = Objects.requireNonNull(players[0]).getLastAction_();
            } while (lastAction.equals("no action"));
            players[0].setLastAction_("no action");

            if(lastAction.equals("pass"))
            {
                if (players.length > 2 && !players[2].hasSkipped_())
                {
                    switchAttacker(players);
                }
                else
                {
                   break;
                }
            }
            else if (lastAction.equals("take"))
            {
                ServerNetwork.sendMessage(players[0].getId_(), gameBoardStateToString(players[0].getId_(), false));
            }
            else
            {
                int card = Integer.parseInt(lastAction);
                if (!players[0].playCard(card))
                {
                    ServerNetwork.sendMessage(players[0].getId_(), gameBoardStateToString(players[0].getId_(), false));
                }
            }
        }
    }

    private static boolean clearPlayers()
    {
        int index = 0;
        for (Player p : players_)
        {
            if (p.getAmountOfHandCards() == 0)
            {
                if (firstAttacker == p.getId_())
                {
                    if (index + 1 == players_.size())
                    {
                        index = 0;
                        firstAttacker = players_.get(index).getId_();
                    }
                    else
                    {
                        firstAttacker = players_.get(index + 1).getId_();
                    }
                }
                viewers_.add(p);
            }
            index++;
        }

        for (Player p : viewers_)
        {
            players_.remove(p);
        }

        return players_.size() >= 2;
    }

    public static void takeAction(String action, int id)
    {
        for (Player p : players_)
        {
            System.out.printf("Searching for id %d found %d\n", id, p.getId_());
            if (p.getId_() == id)
            {
                if (p.isActive_())
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

    public static void switchAttacker(Player[] activePlayers)
    {
        if (activePlayers[2].getAmountOfHandCards() == 0)
        {
            activePlayers[2].setSkipped_(true);
            activePlayers[0].setSkipped_(true);
            return;
        }
        activePlayers[0].setActive_(false);
        activePlayers[0].setAttacker_(false);
        activePlayers[0].setSkipped_(true);

        Player helper = activePlayers[0];
        activePlayers[0] = activePlayers[2];
        activePlayers[2] = helper;

        activePlayers[0].setActive_(true);
        activePlayers[0].setAttacker_(true);
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
        return visibleCards_.size() * 2;
    }

    private static String visibleCardsToString()
    {
        StringBuilder returnString = new StringBuilder();
        for (int[] arr : visibleCards_)
        {
            returnString.append(String.format("%d ", arr[0]));
            returnString.append(String.format("%d ", arr[1]));
        }
        return returnString.toString();
    }

    /**
     * The format that the client can process.
     *
     * @param playerId id of the current player.
     * @return returns the state of the game.
     */
    public static String gameBoardStateToString(int playerId, boolean wasSuccessful)
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

        if (wasSuccessful) returnString.append(String.format("%s", 1));
        else returnString.append(String.format("%s", 0));                                       // Was successful
        return returnString.toString();
    }

    public static void sendGameStateToAll()
    {
        for (Player p : players_)
        {
            ServerNetwork.sendMessage(p.getId_(), gameBoardStateToString(p.getId_(), true));
        }
        for (Player p : viewers_)
        {
            ServerNetwork.sendMessage(p.getId_(), gameBoardStateToString(p.getId_(), true));
        }
    }


}
