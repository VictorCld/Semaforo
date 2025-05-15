package com.example.simulation.datastructure;

import java.io.Serializable;

public class Node<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public  T data;
    public Node<T> next;

    public Node(T data){
        this.data = data;
        this.next = null;
    }
}
