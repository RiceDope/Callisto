package com.rwalker;

import java.util.Iterator;

import com.rwalker.sequenceStrategies.SequenceStrategies;

public class ManualTest {
    public static void main(String[] args){
        
        Sequence<Integer> testing = new Sequence<>(8, SequenceStrategies.RINGBUFFER);
        testing.add(10);
        testing.add(20);
        testing.add(30);
        testing.add(40);
        testing.add(50);
        testing.add(60);
        testing.add(70);
        testing.dequeue();
        testing.dequeue();
        testing.dequeue();
        testing.add(80);
        testing.add(90);
        testing.add(100);


        Iterator<Integer> it = testing.iterator();
        while(it.hasNext()) {
            it.next();
            it.remove();
        }
        
    }
}   