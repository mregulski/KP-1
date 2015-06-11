/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netnumconv.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import numtranslator.RzymArab;
import numtranslator.RzymArabException;

/**
 *
 * @author Marcin Regulski
 */
public class Converter extends Thread
{
    private Socket socket;
    private int clientNumber;
    
    public Converter(Socket socket, int clientNumber)
    {
        this.socket = socket;
        this.clientNumber = clientNumber;
        log("New connection with client #" + clientNumber +" at " + socket );
    } 
    
    @Override
    public void run()
    {
        try
        {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String response;
            out.println("Welcome to RzymArab NET edition!");
            out.println("Send 'exit' to quit");
            out.println("-----------------------------------------------------");
            while(true)
            {
                String input = in.readLine();
                if (input.isEmpty() || input.equals("EXIT"))
                {
                    break;
                }
                
                if (input.equalsIgnoreCase("kill"))
                {
                    out.println("server is kill");
                    System.exit(666);
                }
                log("Received input: " + input);
                try
                {
                    if(RzymArab.isValidRoman(input))
                        response = Integer.toString(RzymArab.r2a(input));
                    else
                        response = RzymArab.a2r(Integer.parseInt(input));
                }
                catch (RzymArabException e)
                {
                   response = e.getMessage();
                }
                catch (NumberFormatException e)
                {
                    response = "Nieprawidłowa liczba.";
                }
                
                log("\t-> response: " + response);
                out.println(response);
            }
        }
        catch(IOException io)
        {
            log("IOException while handling client #"+clientNumber);
            io.printStackTrace();
        }
        finally
        {
            try{ socket.close(); }
            catch(IOException e) {
                log("Can't close socket");
            }
            log("Connection with client#"+clientNumber+ " closed");
        }
        
    }
    
    private void log(String msg)
    {
        System.out.println(msg);
    }
}
