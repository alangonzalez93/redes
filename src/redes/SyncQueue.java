/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redes;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author luciano
 */
public class SyncQueue<E> {
    
    private PriorityQueue<E> q;
    private int length;
    
    public SyncQueue(int length, Comparator comp){
        q = new PriorityQueue(length,comp);
    }
    
    public synchronized int getLength(){
        return length;
    }
    
    public synchronized boolean isEmpty(){
        return q.isEmpty();
    }
    
    public synchronized void add(E x){
        q.add(x);
    }
    
    public synchronized E remove(){
        return q.remove();
    }
    
    public synchronized E peek(){
        return q.peek();
    }
    
}
