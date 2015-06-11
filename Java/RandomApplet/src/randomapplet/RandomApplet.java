package randomapplet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.*;

/**
 * Applet/aplikacja z losowo zmieniającymi się kolorkami na siatce.
 * @author Marcin
 */
public class RandomApplet extends JApplet
{
    /**
     * Punkt wejścia przy uruchamianiu jako aplikacja, nie applet.
     * @param args argumenty linii poleceń
     * <ul>
     * <li>args[0] (int) - liczba wierszy
     * <li>args[1] (int) - liczba kolumn
     * <li>args[2] (double, [0..1])- prawdopodobieństwo zmiany koloru komórki na losowy
     * <li>args[3] (int)- szybkość symulacji (0-100)
     * </ul>
     */
    public static void main(String[] args)
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(RandomApplet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                randomizer = new Random();
                JFrame frame = new JFrame("Kolorki");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setMinimumSize(new Dimension(400, 400));
                initParams(args);
                frame.add(new RandomApplet().createComponents());
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
    
    /**
     * Inicjuje aplet RandomApplet.
     */
    @Override
    public void init()
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(RandomApplet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        randomizer = new Random();
        initParams();
        
        /* Create and display the applet */
        try
        {
            java.awt.EventQueue.invokeAndWait(new Runnable()
            {
                @Override
                public void run()
                {
                    add(createComponents());
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**
     * Czyta argumenty z tagów <param> w HTMLu apletu. W przypadku błędu/braku 
     * zostawia wartości na domyślne. Dla probability i speed jeśli podana
     * wartosć przekracza dopuszcalny zakres ustawia parametr odpowiednio na 
     * dolną lub górną granicę. W przypadku uruchomienia jako programu jako
     * aplikacji, wykorzystywana jest metoda initParams(String[]).
     */
    private void initParams()
    {
        try { rows = Integer.parseInt(getParameter("rows")); } 
        catch(NumberFormatException nfe){}
        try { cols = Integer.parseInt(getParameter("cols")); } 
        catch(NumberFormatException nfe){}
        System.out.println(getParameter("rows"));
        try { 
            probability = Double.parseDouble(getParameter("probability")); 
            probability = (probability>1) ? 1 : probability;
            probability = (probability<0) ? 0 : probability;
        } 
        catch(NumberFormatException | NullPointerException nfe){ probability = 0.3; }
        
        try { 
            speed = Integer.parseInt(getParameter("speed")); 
            speed = (speed>1000) ? 1000 : speed;
            speed = (speed<100) ? 100 : speed;
        } 
        catch(NumberFormatException nfe){}
        //statusLabel.setText("X: " + cols + " Y:" + cols + " p:" + probability +"k: "+speed);
    }
    
    /**
     * Przetwarza argumenty z linii poleceń. Oprócz tego działa jak initParams().
     * @param args - lista argumentów przekazana z main(). 
     * @throws IllegalArgumentException - jeśli liczba arguemntów != 4
     */
    private static void initParams(String[] args) throws IllegalArgumentException
    {
        if(args.length!=4)
        {
            System.out.println("not enough parameters");
            return;
        }
        try { rows=Integer.parseInt(args[0]); }
        catch(NumberFormatException nfe) {}
        try { cols=Integer.parseInt(args[1]); }
        catch(NumberFormatException nfe){}
        try{ 
            probability = Double.parseDouble(args[2]);
            probability = (probability>1) ? 1 : probability;
            probability = (probability<0) ? 0 : probability;
        }
        catch(NumberFormatException nfe){}
        try{
            speed = Integer.parseInt(args[3]);
            speed = (speed>1000) ? 1000 : speed;
            speed = (speed<100) ? 100 : speed;
        }
        catch(NumberFormatException nfe) {}
        System.out.printf("Rows: %d | Cols: %d | Speed: %d | Probability: %f\n", rows,cols,speed,probability);
    }
    
    /**
     * Metoda tworząca wszystkie elementy GUI, wpsólna dla init() i main().
     * @return JPanel zawierający całe GUI programu, do umieszczenia w JFrame 
     * lub JApplet w zależności od metody uruchomienia.
     */
    private JPanel createComponents()
    {
        JPanel GUI = new JPanel(new BorderLayout());
        //GUI.setBorder(BorderFactory.createLineBorder(Color.yellow,4));
        topPanel = new JPanel();
        //topPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        buttonsPanel = new JPanel();
        //buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        mainPanel = new JPanel();
        //mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        runButton = new JButton();
        stopButton = new JButton();
        statusLabel = new JLabel("Naciśnij Start!");

        
        runButton.setText("Start");
        runButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                runButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                stopButtonActionPerformed(evt);
            }
        });
        
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.add(runButton);
        buttonsPanel.add(stopButton);
        buttonsPanel.setAlignmentX(LEFT_ALIGNMENT);
        
        statusLabel.setText("X= " + cols + " | Y= " + cols + " | p=" + probability +" | k= "+speed);
        
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(buttonsPanel);
        topPanel.add(statusLabel);
        topPanel.add(new JSeparator());
        topPanel.setBorder(BorderFactory.createEmptyBorder(4,4,2,4));
        GUI.add(topPanel, java.awt.BorderLayout.PAGE_START);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(2,4,4,4));
        mainPanel.setMinimumSize(new Dimension(200,200));
        System.out.printf("rows: %d, cols: %d",rows,cols);
        mainPanel.setLayout(new GridLayout(rows, cols));
        GUI.add(mainPanel, java.awt.BorderLayout.CENTER);
        return GUI;
    }

    /**
     * Obsługa przycisku START.
     * @param evt 
     */
    private void runButtonActionPerformed(ActionEvent evt)                                          
    {   
        runButton.setEnabled(false);
        createGrid(rows, cols);
        stopButton.setEnabled(true);
        statusLabel.setText("Symulacja włączona.");
    }                                         
    
    /**
     * Obsługa przycisku STOP. 
     * @param evt 
     */
    private void stopButtonActionPerformed(ActionEvent evt)                                           
    {                                               
        stopButton.setEnabled(false);
        resetGrid();
        runButton.setEnabled(true);
        statusLabel.setText("Symulacja zatrzymana.");
    }                                          
    /**
     * Tworzy siatkę Square'ów o wymiarach <rows>x<cols> i umieszcza ją w mainPanel
     * @param rows - liczba wierszy
     * @param cols - liczba kolumn
     */
    private void createGrid(int rows, int cols)
    {
        //resetGrid();
        
        grid = new Square[rows][cols];
        mainPanel.setLayout(new GridLayout(rows, cols));
        for(int i = 0; i<rows; i++)
            for(int j = 0; j<cols; j++)
            {
                grid[i][j] = new Square(randomizer, probability, speed, this, i, j);
                
                //System.out.println(grid[i][j].getId() + ": " +grid[i][j].isAlive());
            }
        for(int i = 0; i<rows; i++)
            for(int j = 0; j<cols; j++)
                grid[i][j].start();
        System.out.println("Grid Created.");
        mainPanel.revalidate();
        System.out.println("Main Panel revalidated.");
    }
    
    /**
     * Zatrzymuje wszystkie wątki komórek i usuwa komórki z siatki.
     */
    private void resetGrid()
    {
        if(grid!=null)
        {
            for (Square[] row : grid)
                for (Square square : row)
                {
                    square.shutdown();
                }
            mainPanel.removeAll();
        }
        //checkThreads();
        
    }
    
    /** 
     * Zwraca obecną tablicę komórek.
     * @return 2-wymiarowa tablica (rows x cols) komórek
     */
    public Square[][] getGrid()
    {
        return grid;
    }
    
    /**
     * Zwraca głowny panel apletu.
     * @return główny panel apletu (zawierający komórki)
     */
    public JPanel getGridPanel()
    {
        return mainPanel;
    }
    
    private static Random randomizer;
    private Square[][] grid;
    private static int rows=10;
    private static int cols=10;
    private static double probability=.3;
    private static int speed=10;
    
    private JLabel statusLabel;
    private JPanel topPanel;
    private JPanel buttonsPanel;
    private JPanel mainPanel;
    private JButton runButton;
    private JButton stopButton;
}
