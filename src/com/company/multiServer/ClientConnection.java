package com.company.multiServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ClientConnection implements Runnable
{
    private Socket socket;

    public ClientConnection(Socket s) throws SocketException, IOException
    {
        this.socket = s;
    }


    @Override
    public void run() {
        try {
            try {
                // To communicate with the client,
                // we need to specify input & output stream.
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                Scanner in = new Scanner(inputStream);
                // Når vi skriver til output streamen bruger vi her en PrintWriter
                PrintWriter writeClient = new PrintWriter(outputStream, true);

                // Send Welcome to the client. Make it print it out
                writeClient.println("Welcome!");

                // Now we are gonna handle if the user want's to close the connection
                boolean done = false;
                while (!done && in.hasNextLine())
                {
                    String stream = in.nextLine();
                    if (stream.equals("!#!exit"))
                    {
                        done = true;
                    }
                    else
                    {
                        System.out.println("RECIEVED: " + stream);
                        writeClient.println("SERVER: " + stream);
                    }
                }
            }
            finally {
                socket.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
