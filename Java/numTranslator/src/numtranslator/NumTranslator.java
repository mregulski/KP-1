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
public class NumTranslator
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //osobno parametry int i str
        List<Integer> paramsInt = new ArrayList<Integer>();
        List<String> paramsStr = new ArrayList<String>();
        for (int i = 0; i < args.length; i++)
        {
            try
            {
                //System.out.println("\t\tisValid? " + args[i] + " " +  RzymArab.isValidRoman(args[i]));
                if (RzymArab.isValidRoman(args[i].toUpperCase()))
                {
                    paramsStr.add(args[i].toUpperCase());
                    continue;
                }
                paramsInt.add(Integer.parseInt(args[i]));
                
            }
            catch(NumberFormatException nfe)
            {
                System.out.println("Nieprawidłowy parametr: " + args[i]);
                continue;
            }
        }
        int argInt;
        for (int j = 0; j < paramsInt.size(); j++)
            try
            {
                argInt = paramsInt.get(j);
                System.out.println(argInt + "->" + RzymArab.a2r(argInt));
            }
            catch(RzymArabException rae)
            {
                System.out.println(rae.getMessage());
                continue;
            }
        
        
        String argStr;
        for (int k = 0; k < paramsStr.size(); k++)
        {
            try
            {
                argStr = paramsStr.get(k);
                System.out.println(argStr + " -> " + RzymArab.r2a(argStr));
            }
            catch (RzymArabException rae)
            {
                System.out.println(rae.getMessage());
                continue;
            }
        }
        
         
        //listNums();
        return;

    }
    
    public static void listNums()
    {
        String str;
        for (int i = 1; i<4000; i++)
        {
            try
            {
                str = RzymArab.a2r(i);
                System.out.println(str.length());
            }
            catch(RzymArabException rae)
            {
                System.out.println(rae.getMessage());
                return;
            }    
        }
    }
    
}
