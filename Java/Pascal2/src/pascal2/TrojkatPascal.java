/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal2;

/**
 *
 * @author Marcin
 */
public class TrojkatPascal
{

    private long[][] rows;

    public TrojkatPascal(int n) throws PascalException
    {
        if (n < 0)
        {
            throw new PascalException("Rozmiar nie może być ujemny (" + n + ")");
        }
        if (n >= 67)
        {
            throw new PascalException("Zbyt duży rozmiar (" + n + ")");
        }
        rows = new long[n + 1][];
        for (int i = 0; i <= n; i++)
        {
            rows[i] = WierszPascal(i);
        }
    }

    public long[] WierszPascal(int m)
    {
        long[] row = new long[m + 1];
        row[0] = 1;
        for (int i = 1; i <= m; i++)
        {
            for (int j = i; j > 0; j--)
            {
                row[j] += row[j - 1];
            }
        }
        return row;
    }

    public long wspolczynnik(int n, int m) throws PascalException
    {
        if (n > rows.length || n<0)
        {
            throw new PascalException("Argument spoza zakresu: " + n);
        }
        if (m > n || m < 0)
        {
            throw new PascalException("Argument spoza zakresu: " + m);
        }
        return rows[n][m];
    }
    
    public static int parseInt(String str) throws PascalException
    {
        int result = 0;
        try
        {
            result = Integer.parseInt(str);
        }
        catch (NumberFormatException e)
        {
            throw new PascalException("Argument nie jest liczbą: " + str);
        }
        return result;
    }
}
