/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netnumconv.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Marcin Regulski
 */
public class NetNumConvServer
{

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException
    {
        System.out.println("Server is running.");
        int clientNumber = 0;
        int port;
        ServerSocket listener;
        try
        {
            port = Integer.parseInt(args[0]);
        }
        catch (IllegalArgumentException e)
        { 
            port = 6969; 
            System.out.println("Invalid port number: " + args[0]+ ". Using default ("+port+").");
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            port = 6969; 
            System.out.println("No port number specified. Using default ("+port+").");
        }
        listener = new ServerSocket(port);
        System.out.println("Listening at port #"+port);
        try
        {
            while (true)
            {                
                new Converter(listener.accept(), clientNumber++).start();
            }
        }
        catch(IOException IO)
        {
            IO.printStackTrace();
        }
        finally
        {
            listener.close();
        }
    }
    
}
