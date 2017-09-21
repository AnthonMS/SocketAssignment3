package com.company.multiServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientConnection implements Runnable
{
    private boolean done;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Scanner in;
    private PrintWriter writeClient;

    private String name = "Guest";
    public static ArrayList<String> messageArray = new ArrayList<String>();


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
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                in = new Scanner(inputStream);
                // Når vi skriver til output streamen bruger vi her en PrintWriter
                writeClient = new PrintWriter(outputStream, true);

                // Send Welcome to the client. Make it print it out
                writeClient.println("Welcome!");

                // Now we are gonna handle if the user want's to close the connection
                done = false;
                while (!done && in.hasNextLine())
                {
                    String stream = in.nextLine();
                    if (stream.equals("!#!exit"))
                    {
                        done = true;
                    }
                    else if (stream.contains("NAME:"))
                    {
                        setName(stream);
                    }
                    else if (stream.contains("PUT:"))
                    {
                        PUTmethod(stream);
                    }
                    else if (stream.contains("COUNT:"))
                    {

                    }
                    else if (stream.contains("GET:"))
                    {

                    }
                    else if (stream.equals("Help"))
                    {
                        String helpMes = "SERVER_HELP: set nick - NAME: , put message - PUT: , array count - COUNT: , get message - GET: , close - !#!exit";
                        writeClient.println(helpMes);
                    }
                    else // Show error message
                    {
                        System.out.println("RECIEVED: " + stream + " - FROM " + name);
                        String errorMes = "SERVER_ERROR: " + stream;
                        String errorMes2 = " Is not a valid command, type 'Help'´for help";
                        writeClient.println(errorMes + errorMes2);

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

    private void setName(String stream)
    {
        System.out.println("RECIEVED: " + stream);
        String tempName = stream.substring(5);
        tempName = trimCustom(tempName);
        name = tempName;
        System.out.println("Name sat as " + name);
        writeClient.println("SERVER: Your name is now: " + name);
    }


    private void PUTmethod(String stream)
    {
        System.out.println("RECIEVED: " + stream);
        String tempMessage = stream.substring(4);
        messageArray.add(tempMessage);
        System.out.println("Message added to array...");
    }



    public static String trimCustom(final String s) {
        final StringBuilder sb = new StringBuilder(s);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0)))
            sb.deleteCharAt(0); // delete from the beginning
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1)))
            sb.deleteCharAt(sb.length() - 1); // delete from the end
        return sb.toString();
    }
}