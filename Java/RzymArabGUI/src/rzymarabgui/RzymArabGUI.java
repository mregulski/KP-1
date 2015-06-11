/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rzymarabgui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.*;

/**
 *
 * @author Marcin
 */
public class RzymArabGUI extends JFrame
{
    public RzymArabGUI()
    {
        initComponents();
    }
    
    private void initComponents()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Konwerter liczb rzymskich");
        setLayout(new BorderLayout());
        
        setMinimumSize(new Dimension(350,150));
        //setPreferredSize(new Dimension(350,150));
        
        // Stwórz elementy GUI
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            inputLabel = new JLabel("Podaj liczbę: ");
            inputField = new JTextField();
            inputField.setMaximumSize(new Dimension(100,40));
            inputField.getActionMap().put("convert", new AbstractAction()
            {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                convertButtonPressed();
            }
            });
            inputField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"convert");
            convertButton = new JButton("Konwertuj");
            
        outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.PAGE_AXIS));
            outputArea = new JTextArea();
           
            outputArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            outputArea.setEditable(false);
            outputScroll = new JScrollPane(outputArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            outputScroll.setPreferredSize(new Dimension(200,100));
            
        
        // Listenery
        convertButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                convertButtonPressed();
            }
        });
            
        add(inputPanel, BorderLayout.PAGE_START);
            inputPanel.add(inputLabel);
            inputPanel.add(inputField);
            inputPanel.add(convertButton);
        add(outputPanel, BorderLayout.CENTER);
            outputPanel.add(outputScroll);
        pack();
    }
    
    private void convertButtonPressed()
    {
        try
        {
            numTranslator = Runtime.getRuntime().exec("RzymArab.exe " + inputField.getText());
        }
        catch (Exception ex)
        {
            outputArea.append(ex.getMessage()+"\n");
            return;
        }
        BufferedReader output = new BufferedReader(new InputStreamReader(numTranslator.getInputStream()));
        String line;
        try
        {
            while((line=output.readLine())!=null)
                outputArea.append(line + "\n");
        }
        catch (IOException ex)
        {
            outputArea.append(ex.getMessage()+"\n");
            return;
        }
        numTranslator.destroy();
        outputArea.revalidate();
        //outputArea.append("Killed: " + !numTranslator.isAlive()+"\n");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(RzymArabGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new RzymArabGUI().setVisible(true);
            }
        });
    }
    
    // Deklaracje zmiennych
    Process numTranslator;
    
    // <editor-fold defaultstate="collapsed" desc="Deklaracje elementów UI">
    private JPanel inputPanel;
        private JLabel inputLabel;
        private JTextField inputField;
        private JButton convertButton;
    private JPanel outputPanel;
        private JScrollPane outputScroll;
        private JTextArea outputArea;
    // </editor-fold>
    
}   