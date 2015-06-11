/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netnumconv.client;

import java.awt.EventQueue;
import java.io.IOException;
import javax.swing.UIManager;

/**
 *
 * @author Marcin Regulski
 */
public class NetNumConvClient
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){}
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                try
                { new ClientGUI().setVisible(true); }
                catch(Exception e){}
            }
        });
    }
    
}
