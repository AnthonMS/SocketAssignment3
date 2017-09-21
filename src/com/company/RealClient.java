package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RealClient
{
    public static void main(String[]args)
    {
        try{
            Socket s = new Socket("127.0.0.1", 8001);

            while (true){
                InputStream inputStream = s.getInputStream();
                OutputStream outputStream = s.getOutputStream();

                Scanner streamScanner = new Scanner(inputStream);

                PrintWriter out = new PrintWriter(outputStream, true);

                String welcome = streamScanner.nextLine();
                System.out.println(welcome);

                Scanner inputScanner = new Scanner(System.in);
                System.out.println("Write something: ");

                boolean done = false;
                while (!done && inputScanner.hasNextLine())
                {
                    // Her starter scanneren arbejde
                    String stream = inputScanner.nextLine();
                    if (stream.equals("Close now"))
                    {
                        done = true;
                        out.println(stream);
                    }
                    else
                    {
                        // Når vi skriver, sender vi en linj emed PrintWriter
                        out.println(stream);
                        System.out.println(stream);
                    }
                }


                s.close();
                System.out.println("Forbindelsen lukket.");
            }
        }
        catch (IOException ex){
            //we ignore this
        }
    }
}
