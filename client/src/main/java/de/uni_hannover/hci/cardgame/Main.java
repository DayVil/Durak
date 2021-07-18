package de.uni_hannover.hci.cardgame;

/**
 * This class is the Main class, It doesn't do anything besides calling the 'real' Main class withing gameClient.java
 *
 * @version 18.07.2021
 * @author Yann Bernhard &lt;yann.bernhard@stud.uni-hannover.de&gt;
 * @author Sebastian Kiel &lt;sebastian.kiel@stud.uni-hannover.de&gt;
 * @author Patrick Schewe &lt;p.schewe@stud.uni-hannover.de&gt;
 * @author Robert Witteck &lt;robert.witteck@stud.uni-hannover.de&gt;
 */
public class Main
{
    /**
     * This method is the main-method which is called on the start of the application
     *
     * @param args  The launch args
     */
    public static void main(String[] args)
    {
        gameClient.main(args);
    }
}
