package de.uni_hannover.hci.cardgame;

/**
 * The main class for the server.
 */
public class GameServerMain
{
    public static void main(String[] args)
    {
        /// UNSERE KLEINE SPIELBOX
        String[] str = {"claus, steven, fabian"};
        GameManager man = new GameManager(str);
        ///


        ServerNetwork serverNetwork = new ServerNetwork();
        serverNetwork.run();
    }
}
