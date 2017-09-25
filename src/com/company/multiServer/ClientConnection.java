package com.company.multiServer;

import com.sun.org.apache.xpath.internal.SourceTree;

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

    private int counter = 0;

    private String name = "Guest";
    public static ArrayList<String> messageArray = new ArrayList<String>();


    public ClientConnection(Socket s) throws SocketException, IOException
    {
        this.socket = s;
        counter = messageArray.size();
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
                System.out.println("Client connected from: " +  socket.getLocalAddress().getHostAddress());

                // Now we are gonna handle if the user want's to close the connection
                done = false;
                while (!done && in.hasNextLine())
                {
                    String stream = in.nextLine();
                    if (stream.equals("!#!exit"))
                    {
                        done = true;
                    }
                    else if (stream.contains("NAME:")
                            || stream.contains("Name:"))
                    {
                        setName(stream);
                    }
                    else if (stream.contains("PUT:")
                            || stream.contains("Put"))
                    {
                        PUTmethod(stream);
                    }
                    else if (stream.contains("COUNT:")
                            || stream.contains("Count:"))
                    {
                        System.out.println("RECIEVED: " + stream);
                        int currentCount = messageArray.size();
                        // check if new messages
                        if (currentCount > counter)
                        {
                            // currentCount is larger than counter
                            int newMessages = currentCount - counter;
                            writeClient.println("SERVER: Array Count = " + currentCount + " - " + newMessages + " New messages.");
                            counter = currentCount;
                        }
                        writeClient.println("SERVER: Array Count = " + currentCount);
                    }
                    else if (stream.contains("GET:")
                            || stream.contains("Get:"))
                    {
                        GETmessage(stream);
                    }
                    else if (stream.equals("HELP")
                            || stream.contains("Help"))
                    {
                        System.out.println("RECIEVED: " + stream);
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
        messageArray.add(name + ": " + tempMessage);
        System.out.println("Message added to array...");
        writeClient.println("SERVER: Message added...");
    }

    private void GETmessage(String stream)
    {
        System.out.println("RECIEVED: " + stream);
        String tempNumber = stream.substring(4);
        try {
            int tempInt = Integer.parseInt(tempNumber);
            tempInt = tempInt - 1;
            //String gottenMessage = "";
            try
            {
                String gottenMessage = messageArray.get(tempInt);
                System.out.println("Message to send ot client: " + gottenMessage);
                writeClient.println("SERVER: Message " + tempInt + " - " + gottenMessage);
            }
            catch (IndexOutOfBoundsException ex)
            {
                //ex.printStackTrace();
                System.out.println("Could'nt send message, Number out of bound...");
                writeClient.println("SERVER_ERROR: Not valid number...");
            }


        }
        catch (NumberFormatException e)
        {
            System.out.println("Client tries - " + stream);
            if (tempNumber.equals("ALL")
                    || tempNumber.equals("All")
                    || tempNumber.equals("all"))
            {
                writeClient.println("SERVER: Trying to get all messages...");
            } else
            {
                writeClient.println("SERVER_ERROR: Not a valid number...");
            }
        }
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