package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.out;

public class Client {
    public static void main(String[]args){
        try{
            Socket s = new Socket("127.0.0.1", 8001); //min ip 10.200.128.41

            while (true){
                InputStream input = s.getInputStream();
                OutputStream output = s.getOutputStream();

                Scanner in = new Scanner(input);

                PrintWriter out = new PrintWriter(output, true);

                String welcome = in.nextLine();
                out.println(welcome);

                out.println("Første besked");

                System.out.println(in.nextLine());

                out.println("Anden besked");

                System.out.println(in.nextLine());

                out.println("Tredje besked");

                System.out.println(in.nextLine());

                s.close();
                System.out.println("Forbindelsen lukket.");
            }
        }
        catch (IOException ex){
            //we ignore this
        }
    }
}
