package de.uni_hannover.hci.cardgame;

import de.uni_hannover.hci.cardgame.Clients.ClientManager;
import de.uni_hannover.hci.cardgame.gameLogic.ExecuteGame;
import de.uni_hannover.hci.cardgame.gameLogic.GameManager;

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
    int maxPlayerCount = 2;
    int clients_ = 0;

    void run()
    {
        try
        {
            serverSocket = new ServerSocket(8000);
            System.out.print("Server started at " + new Date() + '\n');
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        clientManager = new ClientManager();

        waitingForClients(maxPlayerCount);
        int[] IDs = new int[maxPlayerCount];
        for(int i = 0; i < maxPlayerCount; i++)
        {
            IDs[i] = clientManager.getClientList().get(i).getID_();
        }

        System.out.println("We Are FULL");

        ExecuteGame ex = new ExecuteGame();
        ex.runGame(IDs);
        // TODO: Add restart option
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
        while(clients_ < maxNumber)
        {
            try
            {
                Socket socket = serverSocket.accept();
                clients_++;
                InetAddress inetAddress = socket.getInetAddress();

                System.out.print("host name: " + inetAddress.getHostName() + "\n\tIP address " + inetAddress.getHostAddress() + "\n\n");

                socketHandler task = new socketHandler(socket);
                new Thread(task).start();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
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
                clients_ = clientManager.getClientCount();
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
                            clients_ = clientManager.getClientCount();
                            // TODO: Start a bot player in its place
                            break;
                        }

                        if(line.equals("Gimme Gamestate"))
                        {
                            sendMessage(id, "12 " +    //StackSize         DONE
                                    "1 " +                  //TrumpColor        DONE
                                    "2 " +                  //PlayerCount       DONE
                                    "1 " +                  //Player ID         DONE
                                    "Werner " +             //PLayerName        DONE
                                    "3 " +                  //PlayerHandCards   DONE
                                    "0 " +                  //IsAttacker        DONE
                                    "1 " +                  //IsDefender        DONE
                                    "0 " +                  //IsActive          DONE
                                    "25 " +                 //PlayerID          DONE
                                    "Sebastian " +          //PLayerName        DONE
                                    "5 " +                  //PlayerHandCards   DONE
                                    "1 " +                  //IsAttacker        DONE
                                    "0 " +                  //IsDefender        DONE
                                    "1 " +                  //IsActive          DONE
                                    "10 " +                 //VisibleCardsCount DONE
                                    "12 " +                 //VisibleCard       DONE
                                    "13 " +                 //VisibleCard       DONE
                                    "25 " +                 //VisibleCard       DONE
                                    "26 " +                 //VisibleCard       DONE
                                    "43 " +                 //VisibleCard       DONE
                                    "44 " +                 //VisibleCard       DONE
                                    "59 " +                 //VisibleCard       DONE
                                    "60 " +                 //VisibleCard       DONE
                                    "55 " +                 //VisibleCard       DONE
                                    "56 " +                 //VisibleCard       DONE
                                    "10 " +                 //MyHandCardCount   DONE
                                    "14 " +                 //HandCard          DONE
                                    "15 " +                 //HandCard          DONE
                                    "16 " +                 //HandCard          DONE
                                    "17 " +                 //HandCard          DONE
                                    "18 " +                 //HandCard          DONE
                                    "19 " +                 //HandCard          DONE
                                    "20 " +                 //HandCard          DONE
                                    "21 " +                 //HandCard          DONE
                                    "22 " +                 //HandCard          DONE
                                    "23 " +                 //HandCard          DONE
                                    "0");                   //WasSuccessful     DONE

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
                e.printStackTrace();
                System.err.println("Something went wrong");
            }
        }
    }
}
