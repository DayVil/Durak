package de.uni_hannover.hci.cardgame;

import java.util.Scanner;

public class ServerConsoleInput
{

    public static Scanner sc = new Scanner(System.in);

    public static String[] getStartingArgs ()
    {
        String[] returnArgs = new String[3];

        returnArgs[0] = getMaxPlayers();

        returnArgs[1] = getServerPassword();

        returnArgs[2] = getServerPort();

        return returnArgs;
    }

    public static String getMaxPlayers ()
    {
        System.out.println("Please put in the number of the maximum amount of players that you want to have in your game.\nNote: It has to be a number between 2 and 8.\n");
        boolean valid = false;
        int maxplayers = -1;
        do{
            if (sc.hasNextInt())
            {
                maxplayers = sc.nextInt();
                if (maxplayers >= 2 && maxplayers <= 8)
                {
                    valid = true;
                }
                else        //It has been an int but was not a valid int
                {
                    System.out.println("Your input of " + maxplayers + " Maximum players is invalid, it has to be a number between 2 and 8!\n");
                }
            }
            else        //It was not an int but something else
            {
                System.out.println("Your input of " + sc.nextLine() + " Maximum players is invalid, it has to be a number between 2 and 8!\n");
            }

        } while (!valid);

        return String.valueOf(maxplayers);
    }

    public static String getServerPassword ()
    {
        System.out.println("Please put in the password that you want to use for your server!\n");
        sc = new Scanner(System.in);
        boolean valid = false;
        String returnPassword = "";
        do{
            if (sc.hasNextLine())
            {
                returnPassword = sc.nextLine();
                valid = true;
            }
        } while (!valid);
        return returnPassword;
    }

    public static String getServerPort ()
    {
        System.out.println("Please put in the number of the Port your server should use (Leave blank for Standard Port of 8000).\nNote: It has to be a number between 1 and 65535.\n");
        sc = new Scanner(System.in);
        boolean valid = false;
        int port = 8000;

        do{
            if (sc.hasNextLine())
            {
                if (sc.hasNextInt())
                {
                    port = sc.nextInt();
                    if (port >= 1 && port <= 65535)
                    {
                        valid = true;
                    }
                    else
                    {
                        System.out.println("Your input of " + port + " Maximum players is invalid, it has to be a number between 1 and 65535!\n");
                    }
                }
                else
                {
                    port = 8000;
                    valid = true;
                }
            }
        } while (!valid);

        return String.valueOf(port);
    }
}
