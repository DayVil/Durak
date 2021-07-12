package de.uni_hannover.hci.cardgame;

import java.util.Scanner;

/**
 * Controls the server console input.
 *
 * @author Yann Bernhard &lt;yann.bernhard@stud.uni-hannover.de&gt;
 * @author Sebastian Kiel &lt;sebastian.kiel@stud.uni-hannover.de&gt;
 * @author Patrick Schewe &lt;patrick.schewe@stud.uni-hannover.de&gt;
 * @author Robert Witteck &lt;robert.witteck@stud.uni-hannover.de&gt;
 */
public class ServerConsoleInput
{
    /**
     * Scanner to read from std. in.
     */
    public static Scanner sc;

    /**
     * Gets maximum number of players for the server. Limited for 2 to 8 players.
     *
     * @return the maximum number of players
     */
    public static int getMaxPlayers()
    {
        System.out.println("Enter Maxplayer Number <2-8 | Blank for 2>:");
        int maxplayers = -1;
        sc = new Scanner(System.in);
        do
        {
            String s = sc.nextLine();
            if (isInt(s))
            {
                maxplayers = Integer.parseInt(s);
            }
            else
            {
                if (s.equals(""))
                {
                    maxplayers = 2;
                }
            }
            if (maxplayers < 2 || maxplayers > 8)
                System.out.println("Your input of " + maxplayers + " Maximum players is invalid, it has to be a number between 2 and 8!");
        }
        while (maxplayers < 2 || maxplayers > 8);
        return maxplayers;
    }

    /**
     * Get server password.
     *
     * @return the server password
     */
    public static String getServerPassword()
    {
        System.out.println("Enter Password <blank for none>:");
        sc = new Scanner(System.in);
        while (!sc.hasNextLine());
        return sc.nextLine();
    }

    /**
     * Gets server port. Standard port is 8000.
     *
     * @return the server port
     */
    public static int getServerPort()
    {
        System.out.println("Enter server Port <1-65535> (Leave blank for Standard Port 8000):");
        sc = new Scanner(System.in);
        int port = -1;
        do
        {
            String s = sc.nextLine();

            if (isInt(s))
            {
                port = Integer.parseInt(s);
            }
            else
            {
                if (s.equals(""))
                {
                    port = 8000;
                }
            }
            if (port < 1 || port > 65535)
                System.out.println("Your input of " + port + " as server port is invalid, it has to be a number between 1 and 65535!");
        }
        while (port < 1 || port > 65535);
        return port;
    }

    /**
     * Checks if string value is an integer.
     *
     * @param toCheck String to check
     * @return true if it is integer.
     */
    public static boolean isInt(String toCheck)
    {
        if (toCheck == null || toCheck.length() == 0)
            return false;
        char[] chars = toCheck.toCharArray();
        for (char character : chars)
        {
            if (character < '0' || character > '9')
            {
                return false;
            }
        }
        return true;
    }
}
