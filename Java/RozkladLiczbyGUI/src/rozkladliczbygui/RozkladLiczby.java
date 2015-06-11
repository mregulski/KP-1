/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rozkladliczbygui;

/**
 *
 * @author Marcin
 */
public class RozkladLiczby {
    public int[] sito;
    public RozkladLiczby(int n) throws IllegalArgumentException
    {
        if (n < 2)
            throw new IllegalArgumentException("Liczba nie ma czynników pierwszych");
        sito = new int[n+1];
        for (int a = 0; a <= n; a++)
        {
            sito[a] = a;
        }
        
        for (int i = 2; i*i <= n; i++)
        {
            if(sito[i] == i)
                for(int j = i; i*j <= n; j++)
                    if(sito[i*j] > i)
                        sito[i*j] = i;
        }
    }
    
    public int[] czynniki(int m) throws IllegalArgumentException
    {
        if(m < 2 || m >= sito.length)
            throw new IllegalArgumentException("Argument spoza zakresu: " + m);
        
        int curNum = m;
        int i = 0;
        
        while (curNum >= 2)
        {
            curNum = curNum/sito[curNum];
            i++;

        }
        
        int[] czynniki = new int[i];
        curNum = m;
        i = 0;
        
        while (curNum >= 2)
        {
            czynniki[i] = sito[curNum];
            curNum = curNum/sito[curNum];
            i++;
        }
        
        return czynniki;
    }
    
    public static String rozkladString(int n, int[] rozklad)
    {
        String result = "";
        result += n + " = ";
        for(int j = 0; j < rozklad.length; j++)
        {
            result += rozklad[j];
            if(j != rozklad.length-1)
                result += "*";
        }
        return result;
    }
}