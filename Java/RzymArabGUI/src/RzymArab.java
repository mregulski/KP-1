/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Marcin
 */

public class RzymArab
{
    private static String[] liczbyR = 
        { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
    private static int[] liczbyA = 
        { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
    
    public static int r2a(String r) throws RzymArabException
    {
       if(r.length() < 1)     return 0; // r == "" nie działa, efekt ten sam
       if(r.startsWith("M"))  return 1000 + r2a(r.substring(1));
       if(r.startsWith("CM")) return 900  + r2a(r.substring(2));
       if(r.startsWith("D"))  return 500  + r2a(r.substring(1));
       if(r.startsWith("CD")) return 400  + r2a(r.substring(2));
       if(r.startsWith("C"))  return 100  + r2a(r.substring(1));
       if(r.startsWith("XC")) return 90   + r2a(r.substring(2));
       if(r.startsWith("L"))  return 50   + r2a(r.substring(1));
       if(r.startsWith("XL")) return 40   + r2a(r.substring(2));
       if(r.startsWith("X"))  return 10   + r2a(r.substring(1));
       if(r.startsWith("IX")) return 9    + r2a(r.substring(2));
       if(r.startsWith("V"))  return 5    + r2a(r.substring(1));
       if(r.startsWith("IV")) return 4    + r2a(r.substring(2));
       if(r.startsWith("I"))  return 1    + r2a(r.substring(1));
       throw new RzymArabException("Bardzo źle: " + r );  
    }
    
    public static String a2r (int a) throws RzymArabException
    {
        if( a<=0 || a > 3999)
            throw new RzymArabException("Liczba spoza dopuszczalnego zakresu: " + a);
        String rom = "";
        int n = a;
        char cChar;
        for(int i = 0; i<liczbyA.length;i++)
        {
            while (n>=liczbyA[i])
            {
                rom += liczbyR[i];
                n -= liczbyA[i];
            }
        }
        return rom;
    }
    
    public static boolean isValidRoman(String str)
    {
        //System.out.println("Str: " + str);
        try 
        {
            Integer.parseInt(str);
            return false;
        }
        catch(NumberFormatException nfe)
        {
            // nie jest liczbą arabską, sprawdzamy czy jest OK rzymską.
        }
        try
        {
        int attempt = r2a(str);
        //System.out.println("Attempt: " + attempt);
        //System.out.println("a2r(attempt): " + a2r(attempt));
                
        if (a2r(attempt).equals(str))
            return true;
        }
        catch(RzymArabException rae)
        {
            System.out.println(rae.getMessage());
        }
        return false;
    }    
}