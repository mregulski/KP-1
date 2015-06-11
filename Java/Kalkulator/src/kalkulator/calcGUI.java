/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kalkulator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Klasa okna kalkulatora.
 * Zawiera wszystkie komponenty, oraz metody do ich obsługi.
 *
 * @author Marcin Regulski
 */
public class calcGUI extends JFrame
{
    
    /**
     * Tworzy wszystkie komponenty GUI, listenery oraz ustawia layout okna.
     * @author Marcin Regulski
     */
    public calcGUI()
    {
        setupWindow();
        createComponents();
        initLayout();
    }
    
    /**
     * Ustawia właściwości okna i tworzy listener na zmianę rozmiaru okna.
     */
    private void setupWindow()
    {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Kalkulator"); 
        //setResizable(false);
        
        addComponentListener(new ComponentAdapter()
        {
            /**
             * Wykrywa zmianę rozmiaru okna.
             * 
             * @param e - zdarzenie wywołujące metodę. Nie jest używane, potrzebne tylko dla zgodności z interfejsem ComponentListener.
             */
            @Override
            public void componentResized(java.awt.event.ComponentEvent e)
            {
                System.out.println("resize");
                int size = outputBot.getHeight();
                
                /*System.out.println(size);
                Font fnt = new Font(Font.SANS_SERIF, Font.PLAIN, size-20);
                
                StyledDocument doc = outputBot.getStyledDocument();*/
//formResized();
            }

        });
        // bez tego nie wykrywa entera
        getRootPane().setFocusable(true);
        
        /*getRootPane().addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                System.out.println("key: #" + e.getKeyCode() + " ("+ e.getKeyChar()+")");
            }
        });*/
        
    }
    
    
    /** 
     * Tworzy komponenty, dodaje akcje do przycisków (+obsługa klawiatury), 
     * ustawia LayoutManagery na kontenerach i foramtowanie obu 
     * wyjściowych JTextPane'ów
     */
    private void createComponents()
    {
        modeButton = new JButton("Dec>Bin");
        modeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int oldbase = base;
                if(base==10)
                {
                    base=2;
                    for(int i=2; i<numButtons.length; i++)
                    {
                        numButtons[i].setEnabled(false);
                        numButtons[i].setVisible(false);
                    }
                    modeButton.setText("->Dec");
                }
                else if(base==2)
                {
                    base=10;
                    for(int i=2; i<numButtons.length; i++)
                    {
                        numButtons[i].setEnabled(true);
                        numButtons[i].setVisible(true);
                    }
                    modeButton.setText("->Bin");
                }
                System.out.println("base:" + base);
                convertOutput(base, oldbase);
            }
        });
            
        clearButton = new JButton("reset");
        clearButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                resetDisplayFull();
            }
        });
        
        infoButton = new JButton("Info");
        infoButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(getRootPane(), calcGUI.MESSAGE, 
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        flipButton = new JButton("+/-");
        flipButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                flipSign();
            }
        });
        
        numButtons = new JButton[10];
        numActions = new Action[10];
        for (int i = 0; i < numActions.length; i++)
        {
            String buttonName = Integer.toString(i);
            int number = i;
            numActions[i] = new AbstractAction(buttonName)
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    numButtonPressed(e);
                }
            };
            numButtons[i] = new JButton(numActions[i]);
            numButtons[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(numVKcodes[i], 0), "keyPressed");
            numButtons[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(numpadVKcodes[i], 0), "keyPressed");
            numButtons[i].getActionMap().put("keyPressed", numActions[i]);
        }
        
        
        opButtons = new JButton[6];
        opActions = new Action[6];
        for(int i = 0; i < opButtons.length; i++)
        {
            String op = operations[i];
            opActions[i] = new AbstractAction(op)
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    opButtonPressed(e);
                }
            };
            opButtons[i] = new JButton(opActions[i]);
            
            opButtons[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(opVKcodes[i], 0), "keyPressed");
            
            opButtons[i].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(opSecondaryKS[i], "keyPressed");
            opButtons[i].getActionMap()
                    .put("keyPressed", opActions[i]);
        }
        
        
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        numPanel = new JPanel(new GridLayout(4,3,5,5));
        opPanel = new JPanel(new GridLayout(4,2,5,5));
        
        outputTop = new JTextPane();
        outputTop.setEditable(false);
        SimpleAttributeSet ats = new SimpleAttributeSet();
        StyleConstants.setAlignment(ats, StyleConstants.ALIGN_RIGHT);
        outputTop.setParagraphAttributes(ats, true);
        outputTop.setFont(new Font("Courier New", Font.PLAIN, 24));
        outputTop.setPreferredSize(new Dimension(100, (outputTop.getFont().getSize()+6)));
        
        outputBot = new JTextPane();
        outputBot.setEditable(false);
        StyleConstants.setAlignment(ats, StyleConstants.ALIGN_RIGHT);
        outputBot.setParagraphAttributes(ats, true);
        outputBot.setFont(new Font("Courier New", Font.PLAIN, 24));
        outputBot.setPreferredSize(new Dimension(100, (outputTop.getFont().getSize()+7)));
        
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 2, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(3, 5, 5, 5));
    }
    
    /**
     * Dodaje do okna wszystkie komponenty w odpowiednie miejsca, 
     * wywołuje pack() i zapisuje wyjściowy rozmiar jako najmniejszy dozwolony.
     */ 
    private void initLayout()
    {

        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);

        topPanel.add(outputTop);
        topPanel.add(outputBot);
        
        // numerki w kolejności 'kalkulatorowej'
        numPanel.add(numButtons[7]);
        numPanel.add(numButtons[8]);
        numPanel.add(numButtons[9]);
        numPanel.add(numButtons[4]);
        numPanel.add(numButtons[5]);
        numPanel.add(numButtons[6]);
        numPanel.add(numButtons[1]);
        numPanel.add(numButtons[2]);
        numPanel.add(numButtons[3]);
        numPanel.add(numButtons[0]);
        numPanel.add(clearButton);
        for (int i = 0; i<6; i++)
        {
            opPanel.add(opButtons[i]);
        }
        opPanel.add(flipButton);
        opPanel.add(modeButton);
        opPanel.add(infoButton);
        bottomPanel.add(numPanel);
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(opPanel);
        
    pack();
    minSize = getSize();
    setMinimumSize(minSize);
    }
    

    private void formResized()
    {
       /* int size = outputBot.getHeight();
        outputBot.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, size-20));
        outputTop.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, size-20));*/
        
    }
    
    /**
     * Konwertuje zawartość obu paneli (góra/dół) na nową bazę
     * @param newBase - baza docelowa
     * @param oldBase - obecna baza
     */
    public void convertOutput(int newBase, int oldBase)
    {
        Document docB = outputBot.getDocument();
        Document docT = outputTop.getDocument();
        int n=0;
        String newText;
        String op="";
        System.out.println("base"+oldBase+" -> "+"base"+newBase);
        // bottom Output
        if(outputBot.getText().length()>0)
        {
            try{
                n = Integer.parseInt(docB.getText(0, docB.getLength()), oldBase);
            }
            catch(BadLocationException bl) {}
            newText = Integer.toString(n, newBase);
            num2 = newText;
            clear(outputBot);
            try{
            docB.insertString(0, newText, null);}
            catch(BadLocationException bl) {}
        }
        
        // top Output
        if(outputTop.getText().length()>1)
        {
            try{
                n = Integer.parseInt(docT.getText(0, docT.getLength()-1), oldBase);
                op = docT.getText(docT.getLength()-1,docT.getLength());
            }
            catch(BadLocationException bl) {}
            newText = Integer.toString(n, newBase);
            System.out.println("newText: top: " + newText);
            num1=newText;
            newText += op;
            clear(outputTop);
            try{
            docT.insertString(0, newText, null);}
            catch(BadLocationException bl) {}
        }
    }
    
    /**
     * Metoda obsługująca przyciski numeryczne. Dopisuje liczbę (zależnie od przycisku)
     * do dolnego pola tekstowego.
     * @param evt - przycisk
     */
    private void numButtonPressed(ActionEvent evt)
    {
        if(isCalculated)
        {  
            clear(outputBot);
            isCalculated = false;
        }
        if(evt.getActionCommand().matches("\\d"));
        {
            addDigitToBot(evt.getActionCommand());
        }
    }
    
    /**
     * Metoda obsługująca przyciski operacji. Dopisuje symbol do odpowiedniego
     * pola tekstowego lub uruchamia odpowiednią metodę z klasy Kalkulator
     * @param evt - event naciśniecia przycisku
     * @throws IllegalArgumentException 
     */
    private void opButtonPressed(ActionEvent evt)
    {
        if(outputBot.getText().length()==0 && evt.getActionCommand().equals("-"))
        {
            outputBot.setText("-");
            return;
        }
        if(outputBot.getText().length()>0)
            opCount++;
        if(curOp==null && outputBot.getText().length()>0)
        {
            curOp=evt.getActionCommand(); 
            setNumber(1);
            moveBotToTop(curOp);
        }
        else
        {
            setNumber(2);
            moveBotToTop("");
        }
        
        System.out.println("opButtonPressed: " + evt.getActionCommand() + "| opCount: " + opCount);
        if(evt.getActionCommand().equals("=") /*|| evt.getActionCommand().equals("\n") */||  opCount>1)
        {      
            moveBotToTop("=");
            int result=0;
            System.out.println("curOp: " + curOp);
            System.out.println("num1: " + num1 + "| num2:" + num2);
            try
            {
                switch(curOp)
                {

                    case "+":
                        result=Kalkulator.add(num1, num2, base);
                        break;
                    case "-":
                        result=Kalkulator.substract(num1, num2, base);
                        break;
                    case "*":
                        result=Kalkulator.multiply(num1, num2, base);
                        break;
                    case "/":
                        result=Kalkulator.divide(num1, num2, base);
                        break;
                    case "%":
                        result=Kalkulator.modulo(num1, num2, base);
                        break;
                    case "=": case "\n":
                        result=Kalkulator.add(num1, "0", base);
                    default:
                        break;
                }
            }
            catch (IntegerOverflowException overflow)
            {
                resetDisplay();
                            try 
                            {outputBot.getDocument().insertString(0, "<przekroczony zakres>", null);}
                            catch(BadLocationException bl){System.out.println("BADLOC");}
            }
            catch(DivideByZero dbz)
            {
                JOptionPane.showMessageDialog(getRootPane(), 
                                    "Nie można dzielić przez 0", 
                                    "Dzielenie przez 0", JOptionPane.ERROR_MESSAGE);
                resetDisplayFull();
                return;
            }
            
            try{ 
                outputBot.getDocument().insertString(0, Integer.toString(result, base), null); }
            catch(BadLocationException bl) {}
            
            resetDisplay();
        }
    }
    
    /**
     * Resetuje stan kalkulatora, zostawia tylko wynik ostatniej operacji na 
     * dolnym panelu.
     */
    private void resetDisplay()
    {
        curOp = null;
        isCalculated = true;
        num2 = null;
        opCount = 0;
        clear(outputTop);
        requestFocus();
    }
    
    /**
     * Pełny reset stanu kalkulatora
     
     */
    private void resetDisplayFull()
    {
        resetDisplay();
        clear(outputBot);
        num1=null;
    }
    
    /**
     * Czyści zawartość podanego obiektu JTextPane
     * @param textPane - JTextPane do wyczyszczenia
     */
    private void clear(JTextPane textPane)
    {
        Document doc = textPane.getDocument();
        try
        { doc.remove(0,doc.getLength()); }
        catch(BadLocationException bl) {}
    }
    
    /**
     * Ustawia zmienną przechowującą argumenty dla operacji kalkulatora.
     * @param which - wybiera zmienną do ustawienia (num1 lub num2)
     */
    private void setNumber(int which)
    {
        Document bottomDoc = outputBot.getDocument();
        Document topDoc = outputTop.getDocument();
        try
        {
            if(which==1)
                num1 = bottomDoc.getText(0, bottomDoc.getLength());
            else if(which==2)
                num2 = bottomDoc.getText(0, bottomDoc.getLength());
        }
        catch (BadLocationException e)
        {}
    }
    
    /**
     * Dodaje podaną cyfrę do dolnego panelu.
     * @param digit - cyfra do dodania
     */
    private void addDigitToBot(String digit)
    {
        Document bottomDoc = outputBot.getDocument();
        try
        {
            /*if(bottomDoc.getLength()>0)
                Integer.parseInt(bottomDoc.getText(0, bottomDoc.getLength()));*/
            bottomDoc.insertString(bottomDoc.getLength(), digit, null);
        }
        catch(BadLocationException bl) {}//not thrown
        catch(IllegalArgumentException iae) {}
    }
    
    /**
     * Zmienia znak liczby w dolnym panelu.
     */
    private void flipSign()
    {
        String txt = outputBot.getText();
        if(txt.length()>0)
        {
            try
            {   
                if(txt.substring(0, 1).equals("-"))
                    outputBot.setText(txt.substring(1));
                else outputBot.getDocument().insertString(0, "-", null);
            }
            catch(BadLocationException bl) {};
        }
    }
    
    /**
     * Dodaje zawartość dolnego panelu do górnego i czyści dolny.
     * @param operation - symbol do dopisania na końcu górnego pola. Albo
     * obecna operacja, albo pusty String.
     */
    private void moveBotToTop(String operation)
    {
        Document bottomDoc = outputBot.getDocument();
        Document topDoc = outputTop.getDocument();
        try
        {
            topDoc.insertString(topDoc.getLength(), bottomDoc.getText(0, bottomDoc.getLength())+operation, null);
            bottomDoc.remove(0, bottomDoc.getLength());
        }
        catch(BadLocationException bl)
        {}
    }
    
    //<editor-fold desc="Deklaracje zmiennych" defaultstate="collapsed">
    
    
    /**
     * Tablica przechowująca przyciski z liczbami.
     */
    private JButton[] numButtons; //0-9
    /**
     * Tablica przechowująca akcje do przycisków liczb.
     */
    private Action[] numActions;
    /**
     * Kody liczb (górna część klawiatury) do generowania akcji
     */
    private final int[] numVKcodes = { KeyEvent.VK_0, KeyEvent.VK_1, 
        KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5,
        KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9 };
    /**
     * Kody liczb (klawiatra numeryczna) do generowania akcji.
     */
    private final int[] numpadVKcodes = { KeyEvent.VK_NUMPAD0, 
        KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3, 
        KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5,KeyEvent.VK_NUMPAD6, 
        KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9 };
    
    
    /**
     * Tablica przechowująca przyciski operacji
     */
    private JButton[] opButtons;
    private final String operations[] = {"+","-","*","/","%","="};
    /**
     * Akcje przycisków operacji.
     */
    private Action[] opActions;
    /**
     * Kody przycisków operacji (numpad) do generowania akcji.
     */
    private final int[] opVKcodes = { KeyEvent.VK_ADD, KeyEvent.VK_SUBTRACT, 
        KeyEvent.VK_MULTIPLY, KeyEvent.VK_DIVIDE, 0, KeyEvent.VK_EQUALS };
    /**
     * Obiekty Keystroke odpowiadające kolejnym operacjom (inne niż w opVKcodes)
     * do generowania akcji.
     */
    private final KeyStroke[] opSecondaryKS = {
        KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, KeyEvent.SHIFT_DOWN_MASK), // "+"
        KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), // "-"
        KeyStroke.getKeyStroke(KeyEvent.VK_8, KeyEvent.SHIFT_DOWN_MASK), // "*"
        KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0),// "/"
        KeyStroke.getKeyStroke(KeyEvent.VK_5, KeyEvent.SHIFT_DOWN_MASK), //"%"
        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), // "="
    };
    
    private JButton modeButton;
    private JButton infoButton;
    private JButton clearButton;
    private JButton flipButton;
    
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel numPanel;
    private JPanel opPanel;
    private JTextPane outputTop;
    private JTextPane outputBot;
    
    // Stan aplikacji.
    private int opCount=0;
    private boolean isCalculated = false;
    private String curOp = null;
    private String num1="";
    private String num2="";
    
    /**
     * Rozmiar, poniżej którego aplikacja jest zamykana.
     * Ustawiany przez initLayout()}.
     */
    private Dimension minSize;
    /**
     * Tryb (baza) kalkulatora. Domyślnie 10, może być zmieniona na 2 i z powrotem.
     */
    private int base = 10;
    
    private static final String MESSAGE="<html><h1>Kalkulator</h1>"
            + "<p>Autor: Marcin Regulski</p>"
            + "<p>Mały program, który potrafi: dodawać, odejmować, mnożyć,<br>"
            + "dzielić (liczby całkowite) i zwracać resztę z dzielenia.</p>"
            + "<p>Może pracować w trybie dziesiętnym i binarnym oraz konwertować<br>"
            + "liczby z jednego systemu  na drugi.</p></html>";
    //</editor-fold>
}
