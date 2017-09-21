package com.company.oldStuff;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ClientConnection implements Runnable
{
    private Socket s;

    public ClientConnection(Socket s) throws SocketException, IOException
    {
        this.s = s;
    }

    @Override
    public void run()
    {
        try {
            try {
                /*
                Vi har behov for at kommunikere med serveren. Vi opretter derfor en input og en output stream,
                og binder hver især til Socket'ens
                input og output stream.
                Socket kører i full-duplex og der er dermed tovejs kommunikation
                til rådighed
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

                System.out.println("Velkommen!");

                /*
                Vi ønsker at have kontrol over hvornår der skal lukkes for forbindelsen
                Når brugeren skriver luk ned, skal der lukkes ned
                 */
                boolean done = false;
                while (!done && in.hasNextLine())
                {
                    String stream = in.nextLine();
                    if (stream.equals("Luk ned"))
                    {
                        done = true;
                    }
                    else
                    {
                        System.out.println(stream);
                    }
                }
            }
            finally
            {
                s.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
