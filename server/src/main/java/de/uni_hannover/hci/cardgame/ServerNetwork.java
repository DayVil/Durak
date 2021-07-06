package de.uni_hannover.hci.cardgame;

import de.uni_hannover.hci.cardgame.Clients.ClientManager;
import de.uni_hannover.hci.cardgame.gameLogic.GameManager;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerNetwork
{
    ServerSocket serverSocket;
    String serverPassword;
    public static int maxPlayerCount;

    void run()
    {
        //  This method getStartingArgs() has not been tested but it shouldn't fail. server can't start if this is not answered correctly
        maxPlayerCount = ServerConsoleInput.getMaxPlayers();
        serverPassword = ServerConsoleInput.getServerPassword();
        try
        {
            serverSocket = new ServerSocket(ServerConsoleInput.getServerPort());
            System.out.print("Server started at " + new Date() + '\n');
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        waitingForClients(maxPlayerCount);
        System.out.println("All clients found");
        int[] IDs = new int[maxPlayerCount];
        String[] names = new String[maxPlayerCount];
        for(int i = 0; i < maxPlayerCount; i++)
        {
            IDs[i] = ClientManager.getClientList().get(i).getID_();
            names[i] = "Player_" + Integer.toString(i);
        }

        System.out.println("We Are FULL");

        //ExecuteGame ex = new ExecuteGame();
        //ex.runGame(IDs);

        GameManager.initGameManager(IDs, names);
    }

    public static boolean sendMessage(int clientID, String msg)
    {
        BufferedWriter bufferOut =  ClientManager.getWriter(clientID);
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
        while(ClientManager.getClientCount() < maxNumber)
        {
            try
            {
                System.out.println("Server waiting for clients" + String.format("%d", ClientManager.getClientCount()));
                Socket socket = serverSocket.accept();
                InetAddress inetAddress = socket.getInetAddress();

                System.out.print("host name: " + inetAddress.getHostName() + "\n\tIP address " + inetAddress.getHostAddress() + "\n\n");

                System.out.println("Starting new client task");
                socketHandler task = new socketHandler(socket);
                new Thread(task).start();
                while (!ClientManager.hasAddedClient)
                {
                    System.out.println("Server waiting for client added");
                    // Waiting for new thread to add Client
                }
                ClientManager.hasAddedClient = false;
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

                int id = ClientManager.addClient(outputBuffer);
                while (true)
                {
                    //System.out.printf("Waiting for message from Client: %d\n", id);
                    String line = inputBuffer.readLine();
                    if(line != null)
                    {
                        System.out.printf("Got Message %s from Client %d\n", line, id);

                        if(line.equals("disconnect"))
                        {
                            ClientManager.removeClient(id);
                            // TODO: Start a bot player in its place
                            break;
                        }

                        if(!loggedIn && line.length() > "Password: ".length() + serverPassword.length())
                        {
                            String p = line.substring(0, "Password: ".length() + serverPassword.length());
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
                        if(GameManager.activeId_ == id && (line.equals("take") || line.equals("pass") || line.matches("^(1[1-9]|[2-5]\\d?|6[0-2])$")))
                        {
                            GameManager.takeAction(line, id);
                            System.out.printf("Client is accessing gameLogic with %s", line);
                        }
                    }
                }
                socket.close();
                ClientManager.removeClient(id);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.err.println("Something went wrong");
            }
        }
    }
}
