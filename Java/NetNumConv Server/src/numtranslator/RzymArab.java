/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numtranslator;
import java.util.*;
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
       throw new RzymArabException();
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
    
    public static boolean isValidRomanOld(String str)
    {       
        // nie sprawdza kolejności
        /* liczba wystąpień kazdej prawidłowej cyfry
         * - I, 1 - V, 2 - X, 3 - L, 4 - C, 5 - D, 6 - M
         */
        int[] counts = new int[7];
        
        //System.out.println("Start: isValidRoman(" + str + ")");
        
        /* Sprawdzenie długości. Najdłuższa liczba:
         * 3888 -> MMMDCCCLXXXVIII: 15 znaków
         */
        if(str.length()<0 || str.length()>15)
            return false;
        
        //System.out.println("\tLength check: passed");
        
        //int occurenceCount = 0;
        String[] onlyOnce = { "V", "L", "D" };
        char currentChar='\0';
        char lastChar;
        int index = 0;
        boolean unsafe=false;
        for(int i = 0; i < str.length(); i++)
        {
            lastChar = currentChar;
            
            currentChar = str.charAt(i);
            
            //czy jest nielegalny znak?
            //System.out.println("\tcurrentChar: " + currentChar);
            
            if (!isCharInStrArr(currentChar, liczbyR))
            {
                //System.out.println("\t\tIllegal character: " + currentChar);
                return false;               
            }
            
            //System.out.println("\tCharacters check: passed");
            
            index = getIndex(currentChar);
            //Sprawdzenie kolejności.
            //I V X L C D M
            //0 1 2 3 4 5 6
            System.out.println("\tcurrentChar: "+ currentChar + "|index:" + index);
            System.out.println("\t\tlastchar: " + lastChar +"|lastindex: " +getIndex(lastChar));
            if(index > getIndex(lastChar))
            {   
                unsafe = true;
            }
            if(unsafe && index < getIndex(lastChar))
            
            //System.out.println("\tCurrent char: " + currentChar + " index: " + index
                    //+ " counts[index]: " + counts[index]);
                     
            counts[index]++;
            //Czy cufra występuje więcej razy niż norma przewiduje?
            if(isCharInStrArr(currentChar, onlyOnce) && counts[index] >= 2)
                return false;
            if ( counts[index] > 3)
                return false;        
        }
        return true;
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
    
    private static boolean isCharInStrArr(char val, String[] arr)
    {
        String chr = Character.toString(val);
        
        //System.out.println("\t\t\tchr: " + chr);
        
        for(int i = 0; i < arr.length; i++)
        {
            //System.out.println("\t\t\tarr[" + i + "]: " + arr[i] + " " + chr.equals(arr[i]));
            if (chr.equals(arr[i]))
                return true;
        }
        return false;
    }
    
    private static int getIndex(char chr) throws IllegalArgumentException
    {
        switch (chr)
        {
            case 'I':
                return 0;
            case 'V':
                return 1;
            case 'X':
                return 2;
            case 'L':
                return 3;
            case 'C':
                return 4;
            case 'D':
                return 5;
            case 'M':
                return 6;
            default:
                return 0;
                //throw new IllegalArgumentException("(getIndex) neprawidłowy argument: " + chr);
        }
    }
    
}