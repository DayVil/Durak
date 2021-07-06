package de.uni_hannover.hci.cardgame;

import java.util.Scanner;

public class ServerConsoleInput
{
    public static Scanner sc;

    public static int getMaxPlayers ()
    {
        System.out.println("Enter Maxplayer Number <2-8 | Blank for 2>:");
        int maxplayers = -1;
        sc = new Scanner(System.in);

        do
        {
            String s = sc.nextLine();

            if(isInt(s))
            {
                maxplayers = Integer.parseInt(s);
            }
            else
            {
                if(s.equals(""))
                {
                    maxplayers = 2;
                }
            }

            if(maxplayers  < 2 || maxplayers > 8) System.out.println("Your input of " + maxplayers + " Maximum players is invalid, it has to be a number between 2 and 8!");
        }while(maxplayers  < 2 || maxplayers > 8);

        return maxplayers;
    }

    public static String getServerPassword ()
    {
        System.out.println("Enter Password <blank for none>:");
        sc = new Scanner(System.in);
        while(!sc.hasNextLine());
        return sc.nextLine();
    }

    public static int getServerPort ()
    {
        System.out.println("Enter server Port <1-65535> (Leave blank for Standard Port 8000):");
        sc = new Scanner(System.in);
        int port = -1;
        do
        {
            String s = sc.nextLine();

            if(isInt(s))
            {
                port = Integer.parseInt(s);
            }
            else
            {
                if(s.equals(""))
                {
                    port = 8000;
                }
            }

            if(port < 1 || port > 65535) System.out.println("Your input of " + port + " as server port is invalid, it has to be a number between 1 and 65535!");
        }while(port < 1 || port > 65535);
        return port;
    }

    public static boolean isInt(String toCheck)
    {
        if(toCheck == null || toCheck.length() == 0) return false;
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
