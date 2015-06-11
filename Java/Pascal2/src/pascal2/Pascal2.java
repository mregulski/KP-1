/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pascal2;

import java.util.*;

/**
 * 
 * @author Marcin
 */
public class Pascal2
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        if (args.length < 3)
        {
            System.out.println("Za mało argumentów (" + args.length + ")");
            return;
        }

        int tSize = 0;
        List params = new ArrayList();
        try
        {
            tSize = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException ex)
        {
            System.out.println("NFE");
        }
        for (int i = 1; i < args.length; i++)
        {
            try
            {
                params.add(Integer.parseInt(args[i]));
            }
            catch (NumberFormatException nfe)
            {
                continue;
            }
        }
        
        TrojkatPascal TP;
        try
        {
            TP = new TrojkatPascal(tSize);
        }
        catch (PascalException pe)
        {
            System.out.println(pe.getMessage());
            return;
        }
        //System.out.println("rows.length: " + TP.rows.length);
        
        for (int j = 1; j<args.length;j += 2)
        {
            if (j+1 >= args.length)
            {
                System.out.println(args[j] + " - za mało argumentów");
                return;
            }
            
            System.out.print(args[j] + " po " + args[j+1] + " ");
            try
            { 
                System.out.println(" = " + TP.wspolczynnik(TrojkatPascal.parseInt(args[j]), TrojkatPascal.parseInt(args[j+1])));
            }
            /*catch (NumberFormatException nfe)
            {
                System.out.println("Argument nie jest liczbą: ");
            }*/
            catch (PascalException pe)
            {
                System.out.println(pe.getMessage());
                continue;
            }
        }
    return;
    }
}
