package de.uni_hannover.hci.cardgame;

import de.uni_hannover.hci.cardgame.gameLogic.GameManager;
import de.uni_hannover.hci.cardgame.gameLogic.Player.Player;

/**
 * The main class for the server.
 */
public class GameServerMain
{
    public static void main(String[] args)
    {
        /// SANDBOX
        int[] IDs = {0, 1, 2};
        GameManager man = new GameManager(IDs);
        man.initGame();


        ///

        ServerNetwork serverNetwork = new ServerNetwork();
        serverNetwork.run();
    }
}
