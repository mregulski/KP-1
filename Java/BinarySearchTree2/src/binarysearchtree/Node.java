/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarysearchtree;

/**
 * Klasa reprezentująca węzeł drzewa binarnego o wartościach typu T.
 * @author Marcin Regulski
 * @param <T> typ wartości w drzewie. Musi implementować Comparable. T powinien
 * mieć użyteczną metodę toString(), ponieważ jest ona używana przez toString() 
 * węzła.
 */
public class Node<T extends Comparable<T>>
{
        private T value;
        private Node<T> left = null;
        private Node<T> right = null;
        private Node<T> parent = null;
        
        public Node<T> getLeft() { return left; }
        public Node<T> getRight() { return right; }
        public Node<T> getParent() { return parent; }
        public void setParent(Node<T> parent) { this.parent = parent; }
        public void setLeft(Node<T> left) { this.left = left; }
        public void setRight(Node<T> right) { this.right = right; }
        
        public T getValue() { return value; }   
        protected void setValue(T value) { this.value = value; } 
        
        /**
         * Tworzy obiekt Node z podanego obiektu. Obiekt musi implementować
         * interfejs Comparable (dla swojego typu).
         * @param value dowolny obiekt implementujący Comparable.
         */
        public Node(T value)
        {
            this.value = value;
        }
        
        /**
         * @return wynik metody toString() wartości węzła (value).
         */
        @Override
        public String toString()
        {
            return value.toString();
        }
}
