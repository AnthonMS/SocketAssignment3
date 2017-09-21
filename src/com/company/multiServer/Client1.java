package com.company.multiServer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client1
{
    private static Socket socket;
    private static PrintStream printStream;
    private static PrintWriter printWriter;
    private static Scanner scanServerOutput;
    public static void main(String args[])
    {
        // Scanner to accept user input
        Scanner userInput = new Scanner(System.in);
        // Socket to connect to
        // Make it so the userInput is needed for this ip and port
        try
        {
            socket = new Socket("127.0.0.1", 8001);
            //System.out.println("Enter something: ");

            // Print out welcome from the Server
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            scanServerOutput = new Scanner(inputStream);
            System.out.println(scanServerOutput.nextLine());


            while (true)
            {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                scanServerOutput = new Scanner(inputStream);

                // Accept input from client
                String stringInput = userInput.nextLine();

                // printStream object to pass the string to the server
                printWriter = new PrintWriter(outputStream, true);
                printWriter.println(stringInput);

                if (scanServerOutput.hasNextLine())
                {
                    //while (scanServerOutput.hasNextLine()) {
                        System.out.println(scanServerOutput.nextLine());
                    //}
                }

                //String tempString = scanServerInput.nextLine();
                //System.out.println(tempString);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
