/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kalkulator;


//TODO: IntegerOverflowException w opBUttonPressed
import java.util.logging.Level;
import javax.swing.UIManager;

/**
 *
 * @author Marcin
 */
public class Kalkulator
{
    /**
     * Parsuje string na int w podanej bazie. Zapewnia int na wyjściu.
     * @param arg - String do przetworzenia
     * @param base - baza
     * @return przetworzona liczba lub 0 w przypadku błędu
     * @throws kalkulator.IntegerOverflowException
     */
    public static int parseArg(String arg, int base) throws IntegerOverflowException
    {
        long x;
        try
        {
            x = Long.parseLong(arg, base); 
        }
        catch (NumberFormatException e)
        {
            x=0; 
        }
        if(x>Integer.MAX_VALUE || x<Integer.MIN_VALUE)
            throw new IntegerOverflowException();
        return (int)x;
    }
    
    /**
     * Dodaje do siebie 2 liczby w podanej bazie podane jako String
     * @param first - pierwsza liczba, jako String
     * @param second - druga liczba, jako String
     * @param base - baza
     * @return first+second jako int
     * @throws IntegerOverflowException jeśli wynik wykracza poza zakres int
     */
    public static int add(String first, String second, int base) throws IntegerOverflowException
    {
        int x;
        int y;
        try
        {
            x = Kalkulator.parseArg(first, base);
            y = Kalkulator.parseArg(second, base);                
        }
        catch(IntegerOverflowException of)
        {
            throw new IntegerOverflowException();
        }
        long result = x+y;
        if(result>Integer.MAX_VALUE || result<Integer.MIN_VALUE)
            throw new IntegerOverflowException();
        System.out.println(x + "+"+ y + " = " + (x+y));
        return (int) result;
    }
    
    /**
     * Odejmuje od siebie 2 liczby przedstawione jako Stringi zapisane w danej bazie.
     * @param first - pierwsza liczba
     * @param second - druga liczba
     * @param base - baza
     * @return first-second jako int
     * @throws IntegerOverflowException
     */
    public static int substract(String first, String second, int base) throws IntegerOverflowException
    {
        int x;
        int y;
        try
        {
            x = Kalkulator.parseArg(first, base);
            y = Kalkulator.parseArg(second, base);                
        }
        catch(IntegerOverflowException of)
        {
            throw new IntegerOverflowException();
        }
        long result = x-y;
        if(result>Integer.MAX_VALUE || result<Integer.MIN_VALUE)
            throw new IntegerOverflowException();
        System.out.println(x + "-"+ y + " = " + (x-y));
        return (int) result;
    }
    
    /**
     * Mnoży ze sobą dwie liczby w formie Sringów zapisane w podanej bazie.
     * @param first - pierwszy czynnik
     * @param second - drugi czynnik
     * @param base - baza
     * @return first*second jako int
     * @throws IntegerOverflowException
     */
    public static int multiply(String first, String second, int base) throws IntegerOverflowException
    {
        int x;
        int y;
        try
        {
            x = Kalkulator.parseArg(first, base);
            y = Kalkulator.parseArg(second, base);                
        }
        catch(IntegerOverflowException of)
        {
            throw new IntegerOverflowException();
        }
        long result = x*y;
        if(result>Integer.MAX_VALUE || result<Integer.MIN_VALUE)
            throw new IntegerOverflowException();
        System.out.println(x + "*"+ y + " = " + (x*y));
        return (int) result;
    }
    
    /**
     * Dzieli całkowitoliczbowo (div) liczbę w pierwszym Stringu (zapisaną w 
     * danej bazie) przez liczbę w drugim (w tej samej bazie).
     * @param first - pierwsza liczba
     * @param second - druga liczba
     * @param base - baza, w której zapisane są obie liczby
     * @return - first div second jako int
     * @throws DivideByZero - jeśli druga liczba to 0
     * @throws IntegerOverflowException - jeśli wynik przekracza zakres int
     */
    public static int divide(String first, String second, int base) throws DivideByZero, IntegerOverflowException
    {
        int x;
        int y;
        try
        {
            x = Kalkulator.parseArg(first, base);
            y = Kalkulator.parseArg(second, base);                
        }
        catch(IntegerOverflowException of)
        {
            throw new IntegerOverflowException();
        }
        if(y==0)
            throw new DivideByZero();
        long result = Math.floorDiv(x,y);
        if(result>Integer.MAX_VALUE || result<Integer.MIN_VALUE)
            throw new IntegerOverflowException();
        System.out.println(x + "/"+ y + " = " + (x/y));
        return (int) result;
    }
    
    /**
     * Zwraca resztę z dzielienia liczby w pierwszym Stringu (zapisaną w podanej
     * bazie) przez liczbę w drugim.
     * @param first - pierwsza liczba
     * @param second - druga liczba
     * @param base - baza, w której zapisane są obie liczby
     * @return - first%second jako int
     * @throws DivideByZero - jeśli druga liczba to 0
     * @throws kalkulator.IntegerOverflowException integeroferflow
     */
    public static int modulo(String first, String second, int base) throws DivideByZero, IntegerOverflowException
    {
        int x;
        int y;
        try
        {
            x = Kalkulator.parseArg(first, base);
            y = Kalkulator.parseArg(second, base);                
        }
        catch(IntegerOverflowException of)
        {
            throw new IntegerOverflowException();
        }
        if(y==0)
            throw new DivideByZero();
        long result = Math.floorMod(x, y);
        if(result>Integer.MAX_VALUE || result<Integer.MIN_VALUE)
            throw new IntegerOverflowException();
        System.out.println(x + "%"+ y + " = " + (x%y));
        if(result>0)
            return (int) result;
        else{
            return (int) -result;
            }
        
    }
    
    
    /**
     * Main, Ustawia Look Feel na systemowy a następnie tworzy GUI kalkulatora.
     * @param args - the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(Kalkulator.class.getName()).log(Level.SEVERE, null, ex );
        }
        
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new calcGUI().setVisible(true);
            }
        });
    }
    
}
