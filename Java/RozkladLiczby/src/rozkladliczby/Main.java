/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rozkladliczby;

/**
 *
 * @author Marcin
 */
public class Main
{
    public static void main(String[] args)
    {
        int[] n = new int[args.length];
        int max = 0;
        for(int i = 0; i < args.length; i++)
        {
            //System.out.println("[DBG] args[i]: " + args[i]);
            try
            {
                n[i] = Integer.parseInt(args[i]);
                if(n[i] > max)
                    max = n[i];
               // System.out.println("[DBG] n[" + i + "]: " + n[i] + "(" + max + ")");
            }
            catch(OutOfMemoryError oome)
            {
                System.out.println("OUT OF MEMORY");
                return;
            }
            catch (NumberFormatException e)
            {
                //System.out.println("ERROR");
                continue;
            }
        }
        RozkladLiczby RL;
        try
        {
            RL = new RozkladLiczby(max);
        }
        catch(IllegalArgumentException iae)
        {
            System.out.println(iae.getMessage());
            return;
        }
        int[] czynniki;
        
        for(int i = 0; i < n.length; i++)
        {
            if(n[i] == 0)
                System.out.println(args[i] + " - nieprawidłowy parametr");
            else
            {
                try
                {
                    czynniki = RL.czynniki(n[i]);
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    continue;
                }
                System.out.print(n[i] + " = ");
                for(int j = 0; j < czynniki.length; j++)
                {
                    System.out.print(czynniki[j]);
                    if(j != czynniki.length-1)
                        System.out.print("*");
                }
                System.out.println();
            }
                    
        }
    }
}
