package de.uni_hannover.hci.cardgame;

/**
 * The main class for the server.
 */
public class GameServerMain
{
    public static void main(String[] args)
    {
        /// UNSERE KLEINE SPIELBOX
        String[] str = {"claus", "steven", "fabian"};
        System.out.println(str.length);
        GameManager man = new GameManager(str);
        man.initGame();
        ///


        ServerNetwork serverNetwork = new ServerNetwork();
        serverNetwork.run();
    }
}
