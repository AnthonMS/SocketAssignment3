package com.company.oldStuff;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server
{
    public static void main(String[] args)
    {
        try
        {
            // ServerSocket oprettes og port 8001 angives som den der skal lyttes på
            ServerSocket ss = new ServerSocket(8001);
            System.out.println("Server kører...");

            while (true)
            {
                // Så længe der ikke oprettes en forbindelse, venter servern her
                // Så snart der anmodes om en forbindelse accepteres den med accept()
                Socket s = ss.accept();
                System.out.println("Klient forbundet...");

                /*
                Vi har behov for at kommunikere med klienten. Vi opretter derfor
                en input og en output stream, og binder hver især til Socket'ens
                inout og output stream.
                Sockets kører i full-duplex og der er dermed tovejs kommunikation til rådighed
                 */
                InputStream input = s.getInputStream();
                OutputStream output = s.getOutputStream();

                /*
                Til at læse input streamen med, bruger vi her en scanner.
                Den kunne lige så godt have været en BufferReader
                (Hvis forbindelsen der modtages fra lukker efter sig,
                ellers stopper den aldrig med at læse)
                 */

                Scanner in = new Scanner(input);
                // Når vi skriver til output streamen bruger vi her en PrintWriter
                PrintWriter out = new PrintWriter(output, true);


                out.println("Velkommen!");

                boolean done = false;
                while (!done && in.hasNextLine())
                {
                    // Her starter scanneren arbejde
                    String stream = in.nextLine();
                    if (stream.equals("Close now"))
                    {
                        done = true;
                    }
                    else
                    {
                        // Når vi skriver, sender vi en linj emed PrintWriter
                        System.out.println("From client: " + stream);
                    }
                }

                s.close();
                System.out.println("Forbindelsen blev lukket!");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
