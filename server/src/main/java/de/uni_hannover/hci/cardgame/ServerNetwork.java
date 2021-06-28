package de.uni_hannover.hci.cardgame;

import de.uni_hannover.hci.cardgame.Clients.ClientManager;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerNetwork
{
    ServerSocket serverSocket;
    ClientManager clientManager;
    String serverPassword = "TollerServer";

    void run()
    {
        try
        {
            serverSocket = new ServerSocket(8000);
            System.out.printf("Server started at " + new Date() + '\n');
        }
        catch (IOException ex)
        {
            System.out.printf(ex.toString());
        }

        clientManager = new ClientManager();

        waitingForClients(3);

        // TODO: startingGame();
    }

    public boolean sendMessage(int clientID, String msg)
    {
        BufferedWriter bufferOut =  clientManager.getWriter(clientID);
        try
        {
            bufferOut.write(msg + "\n");
            bufferOut.flush();
        }
        catch(IOException e)
        {
            System.out.printf("Error: could not send message %s to client %d", msg, clientID);
            return false;
        }
        return true;
    }

    void waitingForClients(int maxNumber)
    {
        while(clientManager.getClientCount() < maxNumber)
        {
            try
            {
                Socket socket = serverSocket.accept();
                InetAddress inetAddress = socket.getInetAddress();

                System.out.print("host name: " + inetAddress.getHostName() + "\n\tIP address " + inetAddress.getHostAddress() + "\n\n");

                socketHandler task = new socketHandler(socket);
                new Thread(task).start();
            }
            catch (IOException ex)
            {
                System.out.print(ex.toString());
            }
        }
    }

    class socketHandler implements Runnable
    {
        private boolean loggedIn;
        private final Socket socket;

        socketHandler(Socket socket)
        {
            this.loggedIn = false;
            this.socket = socket;
        }

        @Override
        public void run()
        {
            try
            {
                InetAddress inetAddress = socket.getInetAddress();

                BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter outputBuffer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                int id = clientManager.addClient(outputBuffer);
                while (true)
                {
                    //System.out.printf("Waiting for message from Client: %d\n", id);
                    String line = inputBuffer.readLine();
                    if(line != null)
                    {
                        System.out.printf("Got Message %s from Client %d\n", line, id);

                        if(line.equals("disconnect"))
                        {
                            clientManager.removeClient(id);
                            // TODO: Start a bot player in its place
                            break;
                        }

                        if(line.equals("Gimme Gamestate"))
                        {
                            sendMessage(id, "12 " +    //StackSize
                                    "1 " +                  //TrumpColor
                                    "2 " +                  //PlayerCount
                                    "1 " +                  //Player ID
                                    "Werner " +             //PLayerName
                                    "3 " +                  //PlayerHandcards
                                    "0 " +                  //IsAttacker
                                    "1 " +                  //IsDefender
                                    "25 " +                 //PlayerID
                                    "Sebastian " +          //PLayerName
                                    "5 " +                  //PlayerHandcards
                                    "1 " +                  //IsAttacker
                                    "0 " +                  //IsDefender
                                    "5 " +                  //VisibleCardsCount
                                    "12 " +                 //VisibleCard
                                    "13 " +                 //VisibleCard
                                    "25 " +                 //VisibleCard
                                    "26 " +                 //VisibleCard
                                    "43 " +                 //VisibleCard
                                    "5 " +                  //MyHandCardCount
                                    "14 " +                 //HandCard
                                    "15 " +                 //HandCard
                                    "16 " +                 //HandCard
                                    "17 " +                 //HandCard
                                    "18 " +                 //HandCard
                                    "1");                   //WasSuccessful

                        }

                        if(!loggedIn && line.length() > 10 + serverPassword.length())
                        {
                            // TODO: give user to gamelogic for processing
                            String p = line.substring(0, 10 + serverPassword.length());
                            if(p.equals("Password: " + serverPassword))
                            {
                                loggedIn = true;
                                sendMessage(id, "logged in");
                            }
                            else
                            {
                                sendMessage(id, "failed");
                            }
                        }
                    }
                }
                socket.close();
                clientManager.removeClient(id);
            }
            catch (IOException e)
            {
                System.err.println(e);
            }
        }
    }
}
