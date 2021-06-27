package de.uni_hannover.hci.cardgame;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class GameLogic
{
    ServerSocket serverSocket;
    int clientNumber = 0;

    private void incClientNumber(){
        this.clientNumber++;
    }
    private void decClientNumber(){
        this.clientNumber--;
    }

    void run()
    {


        try {
            serverSocket = new ServerSocket(8000);
            System.out.printf("Server started at " + new Date() + '\n');
        } catch (IOException ex) {
            System.out.printf(ex.toString());
        }
        waitingForClients(3);
        // TODO: startingGame();
    }

    void waitingForClients(int maxNumber)
    {
        while(clientNumber < maxNumber)
        {
            try
            {
                Socket socket = serverSocket.accept();
                InetAddress inetAddress = socket.getInetAddress();

                System.out.printf("Client: " + clientNumber + "\n\thost name: " + inetAddress.getHostName() + "\n\tIP address " + inetAddress.getHostAddress() + "\n\n");

                ClientHandler task = new ClientHandler(socket, clientNumber++);
                new Thread(task).start();

            }
            catch (IOException ex)
            {
                System.out.printf(ex.toString());
            }
        }
    }


    class ClientHandler implements Runnable
    {
        private Socket socket;
        private int clientNumber;

        ClientHandler(Socket socket, int clientNumber)
        {
            this.socket = socket;
            this.clientNumber = clientNumber;
            System.out.printf("ClientNumber: %d\n", clientNumber);
        }

        @Override
        public void run()
        {
            try
            {
                System.out.printf("In Run %d\n", clientNumber);

                InetAddress inetAddress = socket.getInetAddress();

                BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter outputBuffer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                while (true)
                {
                    System.out.printf("In Loop %d\n", clientNumber);
                    String Line = inputBuffer.readLine();
                    System.out.printf("Nach inputFromClient %d <%s>\n", clientNumber, Line);
                    System.out.printf("Got Message %s\n", Line);

                    if (Line.equals("disconnect")) break;

                    outputBuffer.write("logged in\n");
                    outputBuffer.flush();
                }
                socket.close();
                decClientNumber();

            }
            catch (IOException e)
            {
                System.err.println(e);
            }
        }
    }
}
