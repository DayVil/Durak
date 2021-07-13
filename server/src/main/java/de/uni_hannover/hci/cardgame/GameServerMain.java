package de.uni_hannover.hci.cardgame;

/**
 * The main class for the server.
 *  @author Yann Bernhard &lt;yann.bernhard@stud.uni-hannover.de&gt;
 *  @author Sebastian Kiel &lt;sebastian.kiel@stud.uni-hannover.de&gt;
 *  @author Patrick Schewe &lt;p.schewe@stud.uni-hannover.de&gt;
 *  @author Robert Witteck &lt;robert.witteck@stud.uni-hannover.de&gt;
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
