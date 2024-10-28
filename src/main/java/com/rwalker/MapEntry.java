package com.rwalker;

/**
 * Class deals with the general representation of a Entry into a Map
 * Has functionality to deal with collisions and next terms
 * 
 * Created: 28/10/2024
 * Updated: 28/10/2024
 * 
 * @author Rhys Walker
 * 
 */

public class MapEntry <K, E> {

    private K key; // Stores the specific key
    private E entry; // Stores the entry at that location
    private MapEntry<K, E> nextMap; // Used for linked list in a given bucket
    
    public MapEntry(K key, E entry){

        this.key = key;
        this.entry = entry;
        this.nextMap = null;

    }

    public K getKey(){
        return key;
    }

    public E getEntry(){
        return entry;
    }

    public void changeEntry(E entry){
        this.entry = entry;
    }

    public void setNext(MapEntry<K, E> next){
        this.nextMap = next;
    }

    public MapEntry<K, E> getNext(){
        return nextMap;
    }
}