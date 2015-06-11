/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netnumconv.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import javax.swing.*;

/**
 *
 * @author Marcin Regulski
 */
public final class ClientGUI extends JFrame implements ActionListener
{
    private JTextField inputField;
    private JTextArea outputField;
    private JScrollPane outputScroll;
    private BufferedReader in;
    private PrintWriter out;
    
    /**
     * Tworzy komponenty GUI i dodaje je do frame'a ClientGUI.
     */
    public void setupGUI()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("RzymArab NET edyszyn");
        getContentPane().setLayout(new BorderLayout(10,10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setMinimumSize(new Dimension(340,450));
        setPreferredSize(new Dimension(340,450));
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
        inputField = new JTextField();
        inputField.setFont(font);
        outputField = new JTextArea();
        outputField.setEditable(false);
        outputField.setFont(font);
        outputScroll = new JScrollPane(outputField);
        outputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        outputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputScroll.setPreferredSize(new Dimension(340,400));
        inputField.addActionListener(this);
        
        add(inputField, BorderLayout.PAGE_START);
        add(outputScroll, BorderLayout.CENTER); 
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println(inputField.getText().toUpperCase());
        out.println(inputField.getText().toUpperCase());
        String response;
        try
        {
            response = in.readLine();
            if(response == null || response.equals(""))
                System.exit(0);
            if(response.equals("server is kill"))
            {
                JOptionPane.showMessageDialog(rootPane, "Goodbye, have a nice day.", "Server is kill.", JOptionPane.ERROR_MESSAGE);
                System.exit(666);
            }
        }
        catch (IOException io)
        {
            response = "Error: " + io;
        }
        outputField.append(inputField.getText() + " -> " + response + "\n");
        pack();
        inputField.selectAll();
    }
    
    /**
     * Tworzy GUI i łączy się z serwerem.
     * @throws IOException 
     */
    public ClientGUI() throws IOException
    {
        setupGUI();
        connectToServer();

        // Po nawiązaniu połączenia serwer wysyła 3 wiadomości.
        for(int i = 0; i<3;i++)
            outputField.append(in.readLine() + "\n");
        
        pack();
    }
    
    /**
     * Tworzy połączenie z podanym serwerem na podanym porcie.
     * @throws IOException 
     */
    public final void connectToServer() throws IOException
    {
        String address="";
        int port=0;
        while(!isValidIP4(address))
        {
            address = JOptionPane.showInputDialog(rootPane, "Podaj adres serwera", null, JOptionPane.PLAIN_MESSAGE).trim();
        }
        while(port==0)
            try
            { port = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "Podaj port", null, JOptionPane.PLAIN_MESSAGE)); }
            catch (NumberFormatException e)
            { }
        
        Socket socket = new Socket();
        try
        {
            System.out.println("connecting...");
            socket.connect(new InetSocketAddress(address, port), 2000);
        }
        catch(SocketTimeoutException | ConnectException ex)
        {
            System.out.println("ERROR: " + ex);
            JOptionPane.showMessageDialog(rootPane, "Nie można połączyć się z serwerem", "Błąd połączenia", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }
    
    /**
     * Sprawdza czy string jest prawidłowym adresem IPv4 (bez portu)
     * @param str string do sprawdzenia
     * @return true jeśli str jest prawidłowym adresem IPv4, inaczej false
     */
    public static boolean isValidIP4(String str)
    {
        return str.matches("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }
    
}
