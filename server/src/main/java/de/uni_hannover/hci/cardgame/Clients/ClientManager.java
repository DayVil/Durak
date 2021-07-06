package de.uni_hannover.hci.cardgame.Clients;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class ClientManager
{
    private static ArrayList<Client> clientList = new ArrayList<>();
    static int newID = 0;
    public static boolean hasAddedClient = false;

    public static int addClient(BufferedWriter writer)
    {
        clientList.add(new Client(newID,writer));
        hasAddedClient = true;
        return newID++;
    }
    public static ArrayList<Client> getClientList()
    {
        return clientList;
    }

    public static void removeClient(int id)
    {
        for(Client c: clientList)
        {
            if(c.getID_() == id)
            {
                clientList.remove(c);
                break;
            }
        }
    }

    public static BufferedWriter getWriter(int id)
    {
        for(Client c: clientList)
        {
            if(c.getID_() == id)
            {
                return c.getWriter_();
            }
        }
        return null;
    }

    public static int getClientCount()
    {
        return clientList.size();
    }
}
