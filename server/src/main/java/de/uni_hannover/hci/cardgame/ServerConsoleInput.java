package de.uni_hannover.hci.cardgame;

import java.util.Scanner;

public class ServerConsoleInput
{

    public static Scanner sc;

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
        sc = new Scanner(System.in);
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
        String portString;

        do{
            if (sc.hasNextLine())
            {
                portString = sc.nextLine();

                if (portString.equals(""))
                {
                    valid = true;
                    port = 8000;
                }
                else if (isInt(portString))
                {
                    port = Integer.parseInt(portString);
                    if (port >= 1 && port <= 65535)
                    {
                        valid = true;
                        break;
                    }
                    else
                    {
                        System.out.println("Your input of " + port + " Maximum players is invalid, it has to be a number between 1 and 65535!\n");
                    }

                }
                else
                {
                    System.out.println("Your input of " + portString + " Maximum players is invalid, it has to be a number between 1 and 65535!\n");
                }
            }
        } while (!valid);

        return String.valueOf(port);
    }

    public static boolean isInt(String toCheck)
    {
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
