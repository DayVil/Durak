package de.uni_hannover.hci.cardgame;

import de.uni_hannover.hci.cardgame.gameLogic.GameManager;

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
        man.sandBox();


        ///

        // TODO: Add a server initializer

        ServerNetwork serverNetwork = new ServerNetwork();
        serverNetwork.run();

        // TODO: Add a restart option
    }
}
