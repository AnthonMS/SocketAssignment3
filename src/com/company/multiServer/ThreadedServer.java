package com.company.multiServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadedServer
{
    public static void main(String[] args) {
        // ServerSocket oprettet med port 8001
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(8001);
            System.out.println("Server Running...");

            while (true)
            {
                // The server is waiting for response from a client
                Socket socket = serverSocket.accept();

                // Now we are gonna make the ClientConnection class
                // This class is a runnable and handles all the server logic
                // A new Thread is created with the ClientConnection as parameter
                Runnable r = new ClientConnection(socket);
                Thread t = new Thread(r);
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
