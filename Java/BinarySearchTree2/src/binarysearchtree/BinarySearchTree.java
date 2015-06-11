/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarysearchtree;

import java.util.ArrayList;
/**
 * Binary Search Tree z węzłami o wartościach typu T.
 * @author Marcin Regulski
 * @param <T> typ wartości węzłów
 */
public class BinarySearchTree<T extends Comparable<T>>
{
    
    private Node<T> root = null;
    
    /**
     * Zwraca korzeń drzewa.
     * @return korzeń drzewa.
     */
    public Node<T> getRoot() { return root; }
    
    /**
     * Tworzy nowe puste drzewo binarne. (root=null)
     */
    public BinarySearchTree() {}
    
    /**
     * Tworzy nowe drzewo binarne, którego korzeń zawiera podaną wartość.
     * @param rootValue wartość roota
     */
    public BinarySearchTree(T rootValue)
    {
        root = new Node<>(rootValue);
    }
    
    /**
     * Tworzy nowe drzewo binarne z podanej tablicy wartości.
     * @param values tablica wartości do wstawienia.
     */
    public BinarySearchTree(T[] values)
    {
        insert(values);
    }
    
    /**
     * Tworzy nowe drzewo binarne o korzeniu w podanym węźle.
     * @param rootNode korzeń drzewa.
     */
    public BinarySearchTree(Node<T> rootNode)
    {
        root = rootNode;
    }
    
    /**
     * Wstawia węzeł do drzewa. Jeśli istnieje już węzeł z tą samą wartością, 
     * nic się nie dzieje, w szczególności nie zostaje dodany nowy węzeł.
     * @param nodeToInsert węzeł do wstawienia
     */
    private void insert(Node<T> nodeToInsert)
    {
        Node<T> tempNode = null;
        Node<T> currentNode = root;
        if(find(nodeToInsert.getValue())!=null)
            return;
        while(currentNode != null)
        {
            tempNode=currentNode;
            if(nodeToInsert.getValue().compareTo(currentNode.getValue())<0)
                currentNode = currentNode.getLeft();
            else 
                currentNode = currentNode.getRight();
        }
        
        nodeToInsert.setParent(tempNode);
        if(tempNode == null)
        {
            root = nodeToInsert;
        }
        else
        {
            if(nodeToInsert.getValue().compareTo(tempNode.getValue())<0)
                tempNode.setLeft(nodeToInsert);
            else
                tempNode.setRight(nodeToInsert);
        }
    }
       
    /**
     * Wstawia wartość do drzewa.
     * @param value wartość do wstawienia.
     */
    public void insert(T value)
    {
        insert(new Node<T>(value));
    }
    
    /**
     * Tworzy węzły z podanych wartości i wstawia je do drzewa.
     * @param values tablica wartości (tego samego typu!)
     */
    public void insert(T[] values)
    {
        for(int i = 0; i<values.length; i++)
            insert(new Node<>(values[i]));
    }
    
    /**
     * Usuwa węzeł zawierający podaną wartość.
     * @param valueToDelete wartość, którą chcemy usunąć z drzewa.
     */
    public void delete(T valueToDelete)
    {
        Node<T> nodeToDelete = find(valueToDelete);
        if(nodeToDelete!=null)
            delete(nodeToDelete);
    }
    
    /**
     * Usuwa z drzewa podany węzeł.
     * @param nodeToDelete węzeł do usunięcia.
     */
    private void delete(Node<T> nodeToDelete)
    {
        Node<T> x,y;
        if(nodeToDelete.getLeft() == null || nodeToDelete.getRight()==null)
            y = nodeToDelete;
        else
            y = findSuccessor(nodeToDelete);
        if (y.getLeft() != null)
            x=y.getLeft();
        else
            x=y.getRight();
        if(x!=null)
            x.setParent(y.getParent());
        if(y.getParent() == null)
            root = x;
        else
        {
            if(y==y.getParent().getLeft())
                y.getParent().setLeft(x);
            else
                y.getParent().setRight(x);
        }
        if(y!=nodeToDelete)
            nodeToDelete.setValue(y.getValue());
            
    }
    
    /**
     * Znajduje węzeł zawierający szukaną wartość.
     * @param query wartość do znalezienia.
     * @return węzeł zawierający wartość lub null jeśli taki nie istnieje.
     */
    public Node<T> find(T query)
    {
        Node<T> currentNode = root;
        while(currentNode != null)
        {
            if(currentNode.getValue().equals(query))
                return currentNode;
            else if(query.compareTo(currentNode.getValue())<0)
                currentNode = currentNode.getLeft();
            else if(query.compareTo(currentNode.getValue())>0)
                currentNode = currentNode.getRight();
        }
        return null;
    }
    
    /**
     * Znajduje minimalny element w podanym poddrzewie.
     * @param subtreeRoot korzeń poddrzewa.
     * @return węzeł z minimalną wartością w poddrzewie.
     */
    public Node<T> findMin(Node<T> subtreeRoot)
    {
        Node<T> currentNode = subtreeRoot;
        while(currentNode.getLeft() != null)
            currentNode = currentNode.getLeft();
        return currentNode;
    }
    
    /**
     * Znajduje minimalny element w całym drzewie. To samo co findMin(root).
     * @return węzeł o minimalnej wartości w całym drzewie.
     */
    public Node<T> findMin()
    { return findMin(root); }
    
    /**
     * Znajduje maksymalny element w podanym poddrzewie.
     * @param subtreeRoot korzeń poddrzewa
     * @return węzeł o maksymalnej wartośći w poddrzewie.
     */
    public Node<T> findMax(Node<T> subtreeRoot)
    {
        Node<T> currentNode = subtreeRoot;
        while(currentNode.getRight() != null)
            currentNode = currentNode.getRight();
        return currentNode;
    }
    
    /**
     * Znajduje maksymalny element w całym drzewie. To samo co findMax(root)
     * @return węzeł o maksymalnej wartości w całym drzewie.
     */
    public Node<T> findMax()
    { return findMax(root); }
    
    /**
     * Znajduje następnik podanego węzła.
     * @param node węzeł, którego następnik ma być znaleziony
     * @return następnik node
     */
    public Node<T> findSuccessor(Node<T> node)
    {
        if(node.getRight() != null)
            return findMin(node.getRight());
        Node<T> tmp = node.getParent();
        while(tmp != null && tmp.getLeft() != node)
        {
            node = tmp;
            tmp = tmp.getParent();
        }
        return tmp;
    }
    
    /**
     * Znajduje poprzednik podanego węzła.
     * @param node węzeł, którego poprzednik ma być znaleziony
     * @return poprzednik node
     */
    public Node<T> findPredecessor(Node<T> node)
       {
        if(node.getLeft()!= null)
            return findMin(node.getLeft());
        Node<T> tmp = node.getParent();
        while(tmp != null && tmp.getRight()!= node)
        {
            node = tmp;
            tmp = tmp.getParent();
        }
        return tmp;
    }     
    
    /**
     * Przechodzi drzewo w porządku InOrder i zwraca ścieżkę jako ArrayList
     * @param start Node od którego ma się zacząć przejście.
     * @param path ArrayList do którego ma być zapisana ścieżka.
     * @return elementy drzewa w porządku InOrder jako ArrayList.
     */
    public ArrayList<Node<T>> traverseInOrder(Node<T> start, ArrayList<Node<T>> path)
    {
        if(start.getLeft() != null)
            traverseInOrder(start.getLeft(), path);
        path.add(start);
        if(start.getRight() != null)
                traverseInOrder(start.getRight(), path);
        return path;
    }
    
    /**
     * Przechodzi drzewo w porządku InOrder i zwraca ścieżkę jako StringBuilder.
     * @param start Node od którego ma się zacząć przejście.
     * @param path StringBuilder do którego ma być zapisana ścieżka.
     * @return elementy drzewa w porządku InOrder jako StringBuilder.
     */
    public StringBuilder traverseInOrder(Node<T> start, StringBuilder path)
    {
        if(start.getLeft() != null)
            traverseInOrder(start.getLeft(), path);
        path.append(start + " ");
        if(start.getRight() != null)
            traverseInOrder(start.getRight(), path);
        return path;
    }
}
