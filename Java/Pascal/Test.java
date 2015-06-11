import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.NegativeArraySizeException;
import java.lang.NumberFormatException;
import java.lang.System;
import java.util.* ;
public class Test
{
    public static void main(String[] args)
    {
        WierszPascal wiersz;
        if(args.length==0)
        {
            return;
        }
        int n,m;
        try
        {
            n = Integer.parseInt(args[0]);
            if (n>66)
                System.out.println("Uwaga: wysoki numer wiersza. Wartosci moga byc bledne");
            wiersz = new WierszPascal(n);
        }
        catch(NumberFormatException ex)
        {
            System.out.println("Nieprawidlowy numer wiersza (" + args[0] + ")");
            return;
        }
        catch(NegativeArraySizeException ex)
        {
            System.out.println("Nieprawidlowy numer wiersza (" + args[0] + ")");
            return;
        }

        for (int i = 1; i < args.length; i++)
        {
            try
            {
                m = Integer.parseInt(args[i]);
                System.out.println(m + ": " + wiersz.wspolczynnik(m));
            }
            catch(NumberFormatException ex)
            {
                System.out.println("Nieprawidlowy numer wspolczynnika (" + args[i] + ")");
                return;
            }
            catch (ArrayIndexOutOfBoundsException ex)
            {
                System.out.println("Numer wspolczynnika poza zakresem");
            }
            /*
            for (int j = 0; j <= n; j++)
            {
                System.out.print(wiersz.wspolczynnik(j) + " ");
            }
            System.out.print("\r\n");
            */
        }
    }
}

class WierszPascal
{
    private long[] row;
    public WierszPascal(int n)
    {   
        row = new long[n+1];
        row[0] = 1;
        for (int i=1; i<=n; i++)
            for(int j = i; j>0; j--)
                row[j] += row[j-1];
    }
    
    public long wspolczynnik (int m)
    {
        /*
        if (m>(row.length+1) || m<0)
        {
            System.out.println("Wiersz " + row.length + " nie ma " + m+1 + "-tego elementu");
        }
        */
        return row[m];
            
        
    }
    
}
