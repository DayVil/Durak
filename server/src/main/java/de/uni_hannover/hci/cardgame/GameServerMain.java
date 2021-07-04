package de.uni_hannover.hci.cardgame;

/**
 * The main class for the server.
 */
public class GameServerMain
{
    public static void main(String[] args)
    {
        // TODO: Add a server initializer

        ServerNetwork serverNetwork = new ServerNetwork();
        serverNetwork.run();

        // TODO: Add a restart option
    }
}
