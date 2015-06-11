/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pascalgui2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.text.BadLocationException;
/**
 *
 * @author Marcin
 */

public class PascalGUI2 extends JFrame
{
    public PascalGUI2()
    {
        initComponents();
    }
    private void initComponents()
    {
        // Ustawienia głównego okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Trójkąt Pascala");
        setMinimumSize(new Dimension(400,300)); // bo tak
        setMaximumSize(new Dimension(WIDTH, tSize));
        setLayout(new BorderLayout());
        
        //<editor-fold defaultstate="collapsed" desc="Tworzenie elementów UI">
        topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
            controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                sizeLabel = new JLabel("Podaj rozmiar trójkąta: ");
                sizeField = new JTextField(4);
                sizeField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "run");
                sizeField.getActionMap().put("run", new AbstractAction("run")
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        if(runButton.isEnabled())
                        {
                            //System.out.println("[INFO] runButtonActionPerformed");
                            runButtonPressed();
                        }
                    }
                });
                runButton = new JButton("Utwórz");
                runButton.setEnabled(false);
            errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                errorLabel = new JLabel("");
                    errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    errorLabel.setForeground(new Color(220,0,0));
        
        outPanel = new JPanel();
        outPanel.setLayout(new BoxLayout(outPanel, BoxLayout.PAGE_AXIS));
            outputArea = new JTextArea();
            outputArea.setEditable(false);
            outputArea.setFont(new Font("Courier New", Font.PLAIN, 11));
            outputScroll = new JScrollPane(outputArea, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //</editor-fold>

        // Listenery
        sizeField.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent evt)
            {
                if(evt.getKeyCode() != KeyEvent.VK_ENTER)
                {
                    //System.out.println("[INFO] KeyReleased: NotEnter");
                    sizeCheckInput();
                }
            }
        });
        runButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                runButtonPressed();
            }
        });
        
        // <editor-fold defaultstate="collapsed" desc="Dodanie gotowych elementów do okna">
        add(topPanel, BorderLayout.PAGE_START);
            topPanel.add(controlPanel);
                controlPanel.add(sizeLabel);
                controlPanel.add(sizeField);
                controlPanel.add(runButton);
            topPanel.add(errorPanel);
                errorPanel.add(errorLabel);
        
        add(outPanel, BorderLayout.CENTER);
            outPanel.add(outputScroll);
        // </editor-fold>
            
        pack();
    }
    
    // Metody obsługi zdarzeń
    
    public void sizeCheckInput()
    {
        //System.out.println("[INFO] fired: sizeCheckInput()");
        errorLabel.setVisible(false);
        runButton.setEnabled(false);
        if(sizeField.getText().isEmpty())
        {
            return;
        }
        try
        {
            tSize = Integer.parseInt(sizeField.getText());
        }
        catch (NumberFormatException ex)
        {
            errorLabel.setText("Rozmiar musi być liczbą naturalną.");
            errorLabel.setVisible(true);
            return;
        }
        runButton.setEnabled(true);           
    }
    
    public void runButtonPressed()
    {
        //System.out.println("[VAR] tSize = " + tSize);
        try
        {
            trojkat = new TrojkatPascal(tSize);
            String result = "";
            for (int i = 0; i<=tSize; i++)
            {
                for(int j = 0; j<=i; j++)
                {
                    result += trojkat.wspolczynnik(i, j);
                    if (j != i)
                        result+=" ";
                }
                if (i != tSize)
                    result += "\n";
            }
            outputArea.setText(result);
            
        }
        catch(PascalException ex)
        {
            errorLabel.setText(ex.getMessage());
            errorLabel.setVisible(true);
            return;
        }
        System.out.println("[VAR] outputArea.getLineCount() = " + outputArea.getLineCount());
        if(outputArea.getLineCount()>0)
        {
            Dimension newSize = new Dimension();
            Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            newSize.height = Math.min(screenSize.height-topPanel.getHeight()-70,
                    (tSize+1) * (outputArea.getFont().getSize()+4)+2);
            
            int start;
            int end;
            String lastLine;
            try
            {
                start = outputArea.getLineStartOffset(tSize);
                end = outputArea.getLineEndOffset(tSize);
                lastLine = outputArea.getText(start, end-start);
            }
            catch(BadLocationException badloc) //nie występuje.
            {
                return;
            }
              
            newSize.width = Math.min(screenSize.width-10, 
                    (lastLine.length()-2) * (outputArea.getFont().getSize()-3));
            
            System.out.println("new width = " + newSize.width);
            System.out.println("new height = " + newSize.height);
            outputScroll.setPreferredSize(newSize);
            // ustaw pozycję na początek
            outputArea.setCaretPosition(0);
            pack();
            
        }
        //System.out.println("[OK] Utworzono trójkąt");
    }
    
    // END obługa zdarzeń
    
    public static void main (String[] args)
    {
        // <editor-fold defaultstate="collapsed" desc="ustawienie wyglądu na systemowy">
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(PascalGUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new PascalGUI2().setVisible(true);
            }
        });
    }
    
    // Deklaracje zmiennych
    private int tSize;
    private TrojkatPascal trojkat;
    
    // <editor-fold defaultstate="collapsed" desc="Deklaracje elementów UI">
    private JPanel topPanel;
        private JPanel controlPanel;
            private JLabel sizeLabel;
            private JTextField sizeField;
            private JButton runButton;
        private JPanel errorPanel;
            private JLabel errorLabel;
    private JPanel outPanel;
        private JScrollPane outputScroll;
            private JTextArea outputArea;
    // </editor-fold>
}
