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
        System.out.println(man.gameBoardState(1));
        ///


        ServerNetwork serverNetwork = new ServerNetwork();
        serverNetwork.run();
    }
}
