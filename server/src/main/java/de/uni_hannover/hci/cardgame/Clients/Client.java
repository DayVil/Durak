package de.uni_hannover.hci.cardgame.Clients;

import java.io.BufferedWriter;

public class Client
{
    private final int ID_;
    private final BufferedWriter writer_;

    public Client(int id, BufferedWriter writer)
    {
        ID_ = id;
        writer_ = writer;
    }

    public int getID_()
    {
        return ID_;
    }

    public BufferedWriter getWriter_()
    {
        return writer_;
    }
}
