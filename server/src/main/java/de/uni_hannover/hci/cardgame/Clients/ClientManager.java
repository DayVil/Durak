package de.uni_hannover.hci.cardgame.Clients;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class ClientManager
{
    public ArrayList<Client> getClientList()
    {
        return clientList;
    }

    private ArrayList<Client> clientList;
    int newID;

    public ClientManager()
    {
        newID = 0;
        clientList = new ArrayList<>();
    }

    public int addClient(BufferedWriter writer)
    {
        clientList.add(new Client(newID,writer));
        return newID++;
    }

    public void removeClient(int id)
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

    public BufferedWriter getWriter(int id)
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

    public int getClientCount()
    {
        return clientList.size();
    }
}
