/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarysearchtree;


/**
 *
 * @author Marcin
 */
public class BST_test
{
    public static void main(String[] args)
    {
        BinarySearchTree bst = new BinarySearchTree<>(5);
        //Integer[] nums = new Integer[] {6,3,4,2,12,8,5,4}; 
        bst.insert(6);
        bst.insert(3);
        bst.insert(4);
        bst.insert(2);
        bst.insert(12);
        bst.insert(8);
        bst.insert(5);
        bst.insert(4);
        bst.delete(6);
        bst.delete(5);
        StringBuilder path = bst.traverseInOrder(bst.getRoot(), new StringBuilder());
        System.out.println(path);
        
        /*BinarySearchTree bst2 = new BinarySearchTree<>();
        String[] strings = new String[] { "ZGfBh7bV8g" ,"xmY13wHWnY","1W5nQCBqPb",
        "Pk6mGvX3tT","R4aFe355s5","dqivcO6q6q","ILJZo6oaHh","X8GoeRpdPL",
        "A0WN6ngi8N","HOqI3nQD1l","6nb4PKQwW1","MSZHxVpQY8","gsxfZtv1b4",
        "9w7iBhzuBN","TdeUG6cDFI","jHCUqJR9VL","kQjj0msiDN","6wHBDklTm7",
        "RGxfYB3d9q","YfmsZ9oiO8","TyzCsfqHmo","6Ib6Z3MjEO","wq1wxvom3s",
        "3jl7XEO8tw","REsRYvDe5N","GxZF97XjCk","VmC5dDIlkU","Wk77nUMXtO",
        "PQxfHc7LyW","XBHb807RTd","g6GjrGDUWU","Y1vUHOwL0D","ounY31PVwM",
        "61RrkgKt3K","rxTaVJ5YSx","uqlzaFJM4d","x5IpVTV8iX","P1LuJ3Ahaw",
        "No7No9JvdK","biw98n4kKT","GGiJLWrlQS","JnzXbEoDvC","uPt8TwLWvK",
        "LX6Ry0qcOG","SkJp7sTkTf","SJ40VXLyrm","Jv1Rdiww8W","X0lkSGlpza",
        "UpibAsd75Q","y5CWazs7c5"
        };
        bst2.insert(strings);
        path=bst2.traverseInOrder(bst2.getRoot(), new StringBuilder());
        System.out.println(path);
        
        
        bst2.delete("REsRYvDe5N");
        path=bst2.traverseInOrder(bst2.getRoot(), new StringBuilder());
        System.out.println(path);
        
        
        BinarySearchTree bst3 = new BinarySearchTree<Integer>(new Integer[] {5,4,6,12,3,52,1,32});
        System.out.println(bst3.traverseInOrder(bst3.getRoot(), new StringBuilder()));
        
        bst3.delete(69);
        bst3.insert(69);
        bst3.insert(69);
        bst3.insert(69);
        bst3.insert(69);
        bst3.insert(68);
        bst3.insert(70);
        bst3.insert(69);
        System.out.println(bst3.traverseInOrder(bst3.getRoot(), new StringBuilder()));
        
        System.out.println(bst3.find(-1));*/
    }
}
