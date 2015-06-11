/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomapplet;

import java.awt.Color;
import java.util.Random;
import javax.swing.JPanel;

/**
 * Klasa reprezentująca pojedynczą komórkę na siatce.
 * @author Marcin
 */
public class Square extends Thread
{
    /**
     * Tworzy nową komórkę o określonych parametrach i umieszcza ją w obiekcie
     * RandomApplet. Komórka musi być potem uruchomiona z zewnątrz przez
     * wywołanie jej metody run().
     * @param randomSupplier - obiekt Random, który ma wykorzystywać komórka.
     * @param probability - prawdopodobieństwo zmiany koloru komórki na losowy w
     * każdym przebiegu pętli run() komórki.
     * @param speed - długość przerwy po każdeym przebiegu pętli run() komórki.
     * @param container - referencja do rodzica typu RandomApplet. Umożliwia
     * dodanie się komórki do siatki.
     * @param row - indeks Y komórki w siatce
     * @param col - indeks X komórki w siatce
     */
    public Square(Random randomSupplier, double probability, int speed, RandomApplet container,
            int row, int col)
    {
        randomizer = randomSupplier;
        this.probability = probability;
        this.speed = speed;
        this.container=container;
        this.row = row;
        this.col = col;
        panel = new JPanel();
        panel.setBackground(getRandomColor());

        container.getGridPanel().add(panel);
        container.revalidate();
    }
    
    /**
     * Metoda odpowiedzialna za działanie komórki. Dopóki komórka nie dostanie
     * sygnału zatrzymania, co pewien losowy czas z zakresu [0.5*speed,1.5*speed]
     * ms zmienia kolor komórki na losowy lub średnią z kolorów sąsiadów.
     */
    @Override
    public void run()
    {
        double p;
        while(keepRunning)
        {
            panel.repaint();
            p = randomizer.nextDouble();
            //System.out.println(getName()+" p="+p);
            if(p<=probability)
            {
                synchronized(panel)
                {panel.setBackground(getRandomColor());}
                //container.revalidate();
            }
            else
            {
                //keepRunning=false;
                averageColor();
            }
            try
            {
                long delay = speed/2 + randomizer.nextInt(1*speed);
                sleep(delay); 
            }
            catch(InterruptedException e)
            {
                System.out.println(getName() + " interupted.");
            }
        }
    }
    
    /**
     * Nakazuje wątkowi zakończyć pracę. Komórka skończy wykonywać swoją pętlę 
     * i wyłączy się.
     */
    public void shutdown()
    {
        keepRunning = false;
    }
    
    /**
     * @return kolor obecnej komórki
     */
    public Color getColor()
    {
        return panel.getBackground();
    }
    
    /**
     * Ustawia kolor komórki na średni kolor jej sąsiadów. Siatka traktowana
     * jest jako torus (zawijana po X i Y). Średni kolor jest wyliczany jako
     * kolor o składowych równych średnim odpowiednich składowych kolorów
     * komórek sąsiadujących z obecną bokami.
     */
    synchronized private void averageColor()
    {
        Color colors[] = new Color[4];
        Square[][] grid = container.getGrid();
        colors[0] = (row-1>0) ? grid[row-1][col].getColor() : grid[grid.length-1][col].getColor();
        colors[1] = (row+1<grid.length) ? grid[row+1][col].getColor() : grid[0][col].getColor();
        colors[2] = (col-1>0) ? grid[row][col-1].getColor() : grid[row][grid[0].length-1].getColor();
        colors[3] = (col+1<grid[0].length) ? grid[row][col+1].getColor() : grid[row][0].getColor();

        int red = 0;
        int green = 0;
        int blue = 0;
        for(int i = 0; i<4; i++)
        {
            red+=colors[i].getRed();
            green+=colors[i].getGreen();
            blue+=colors[i].getBlue();
        }
        
        red = red/4;
        green = green/4;
        blue = blue/4;
        //System.out.println("RGB=" + red + " " + green + " " + blue);
        panel.setBackground(new Color(red,green,blue));
    }
    /**
     * Tworzy losowy kolor (RGB)
     * @return obiekt Color o losowych składowych RGB.
     */
    private Color getRandomColor()
    {
        int r = randomizer.nextInt(256);
        int g = randomizer.nextInt(256);
        int b = randomizer.nextInt(256);
        return new Color(r,g,b);  
    }
    
    private final int row;
    private final int col;
    private final RandomApplet container;
    private final JPanel panel;
    private boolean keepRunning = true;
    private final Random randomizer;
    private final double probability;
    private final int speed;
}
