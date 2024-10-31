package com.rwalker;

import java.security.KeyException;
import java.util.Comparator;

/**
 * Class that implements HashMaps, LinkedHashMaps
 * As standard keys are kept in the order they are inserted
 * 
 * Created: 28/10/2024
 * Updated: 31/10/2024
 * 
 * @author Rhys Walker
 * 
 */

@SuppressWarnings("unchecked")
public class Map <K, E> {

    private int buckets = 16; // The default number of buckets to be used
    private MapEntry<K, E>[] bucketList; // Essentially a bucket
    private float loadFactor = 0.75f; // The load factor to be used
    private float expansionFactor = 2.0f; // The factor to expand the number of buckets by
    private Sequence<K> keys = new Sequence<>(); // Stores a list of all keys

    /**
     * Constructor used if no parameters are passed
     */
    public Map() {
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
    }

    /**
     * Constructor used if number of buckets and load factor are passed
     * @param buckets Default starting number of buckets
     * @param loadFactor Default starting load factor
     */
    public Map(int buckets, float loadFactor, float expansionFactor){
        this.buckets = buckets; // Starting number of buckets to use
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
        this.loadFactor = loadFactor; // Set the load factor
    }

    /**
     * Returns the keyset in the order they were inserted
     * @return
     */
    public Sequence<K> keySet(){
        return keys;
    }

    /**
     * Returns the keyset in the order specified
     * @param comparator
     * @return
     */
    public Sequence<K> sortedKeySet(Comparator<K> comparator){
        return keys.sortCopy(comparator);
    }

    /**
     * Remove a specific element based on the key
     * @param key
     */
    public void remove (K key){

        // Check if the key is in the Map
        if (!keys.contains(key)){
            throw new IllegalArgumentException("Key not in HashMap");
        }

        /*
         * Is the key in a linkedList or in a bucket on its own.
         * Track the before and after element so that we remove correctly
         */
        int index = generateHashIndex(key);
        MapEntry<K, E> toRemove = bucketList[index];

        if (toRemove.getKey() == key){
            // This is our element now check there is nothing after

            if (toRemove.getNext() == null){

                // We can just remove
                keys.remove(keys.firstIndexOf(key)); // Remove key
                bucketList[index] = null; // Set the bucket to be null now

            } else {

                // We need to set its sucessive element to be the root of the linked list
                // in that bucket
                keys.remove(keys.firstIndexOf(key)); // Remove key
                bucketList[index] = toRemove.getNext(); // Set the buckets root to be sucessor

            }
        } else {
            // We must find the element to remove
            MapEntry<K, E> previous = toRemove;
            toRemove = toRemove.getNext();

            // Find our element
            while (toRemove.getKey() != key){
                previous = toRemove; // Set previous to be current
                toRemove = toRemove.getNext(); // Update current
            }

            // Now check if our element has a successor or not
            if (toRemove.getNext() == null){ // We need to set the next to be null if nothing follows what to remove
                previous.setNext(null);
            } else { // We need to set the next to be following element
                previous.setNext(toRemove.getNext());
            }
        }
    }

    /**
     * Get an item from the Map
     * @param key The key to get the value for
     * @return The value that was stored at that key
     */
    public E get(K key){

        // Check if they key is in the Map
        if (!keys.contains(key)){
            throw new IllegalArgumentException("Key not in HashMap");
        }

        // Otherwise continue
        return internalGet(key, bucketList);

    }

    private E internalGet(K key, MapEntry<K, E>[] mapToAddTo) {
        // Get the hashed index of the key
        int index = generateHashIndex(key);

        // Take the current MapEntry
        MapEntry<K, E> temp = mapToAddTo[index];

        // Loop adopts the let it fail approach
        // An error will be thrown when we run out of entries in the bucket
        // It will also be throws if the key does not exist
        while (true){

            if (temp.getKey() == key){
                return temp.getEntry();
            } else {
                temp = temp.getNext();
            }

        }
    }

    /**
     * Insert a key value pair into the HashMap
     * @param key The key to insert with
     * @param entry The entry to insert
     */
    public void put (K key, E entry){

        // Check if we must expand the sub-array
        if (loadFactorExceeded()){
            rehash();
            internalPut(key, entry, bucketList, keys, false, buckets);
        } else {
            internalPut(key, entry, bucketList, keys, false, buckets);
        }

    }

    /**
     * Internal version of put allows for finer grained control over putting anything inside of the map
     * @param key The key to insert with
     * @param entry The entry to insert
     * @param mapToAddTo The map to add to
     * @param keysToAdd The list of keys to be used
     * @param newBuckets Are there new buckets being added (i.e. are we rehashing at the minute)
     * @param buckets How many buckets are there
     */
    private void internalPut(K key, E entry, MapEntry<K, E>[] mapToAddTo, Sequence<K> keysToAdd, boolean newBuckets, int buckets){
        
        // Calculate the index to insert into
        int index;
        if (!newBuckets){
            // Hash function for normal operation
            index = generateHashIndex(key);
        } else {
            // Hash function for while we are inserting
            index = generateHashIndexInsertion(key, buckets);
        }
        

        if (keysToAdd.contains(key)){
            // We will overwrite as it is already contained
            
            MapEntry<K, E> temp = mapToAddTo[index];

            while (!temp.getKey().equals(key)){
                temp = temp.getNext();
            }

            temp.changeEntry(entry);

        } else {
            // Key does not already exist

            if (mapToAddTo[index] == null){
                // Bucket is empty add a new entry
                mapToAddTo[index] = new MapEntry<K, E>(key, entry);
                keysToAdd.append(key);

            } else {
                // Bucket is not empty find and add to the LinkedList
                MapEntry<K, E> temp = mapToAddTo[index];

                // While next entry is not null
                while (temp.getNext() != null){
                    temp = temp.getNext();
                }

                // When we get to the null bit set the next to be our term
                temp.setNext(new MapEntry<K, E>(key, entry));
                keysToAdd.append(key);
            }
        }
    }

    /**
     * Calculate the correct index to insert into from the key
     * @param entry The key to be inserted
     * @return int being the array index
     */
    private int generateHashIndex(K key){

        // Work out index in respect to the number of buckets
        int index = key.hashCode() % buckets;
        // Guard to stop negative index
        if (index < 0){
            index = index*-1;
        }
        return index;
    }

    /**
     * Calculate the correct index to insert into from the key
     * @param entry The key to be inserted
     * @param nB The number of buckets
     * @return int being the array index
     */
    private int generateHashIndexInsertion(K key, int nB){

        // Work out index in respect to the number of buckets
        int index = key.hashCode() % nB;
        // Guard to stop negative index
        if (index < 0){
            index = index*-1;
        }
        return index;
    }

    /**
     * Rehash the array
     */
    private void rehash() {
        
        int newAmountOfBuckets = (int) Math.ceil((buckets * expansionFactor)); // calculate the new number of buckets
        MapEntry<K, E>[] savedList = bucketList; // save the old array of entries
        bucketList = new MapEntry[newAmountOfBuckets]; // create the new larger array

        // As we are creating a new Map to expand new keys are needed
        Sequence<K> keysToAdd = new Sequence<>(keys.length());

        // Loop over keySet and add to the new bigger array
        for (int i = 0; i < keys.length(); i++) {
            internalPut(keys.get(i), internalGet(keys.get(i), savedList), bucketList, keysToAdd, true, newAmountOfBuckets);
        }

        buckets = newAmountOfBuckets;
        keys = keysToAdd;

    }

    /**
     * Should we be expanding the number of buckets
     * @return boolean true if we should expand
     */
    private boolean loadFactorExceeded() {

        if (keys.length() >= buckets*loadFactor){
            return true;
        } else {
            return false;
        }

    }

    /**
     * Returns the Map as a String
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        // Opening bracket
        sb.append("[");

        // Loop over and add all terms
        for (int i = 0; i < keys.length(); i++){
            sb.append("{ ");
            sb.append(keys.get(i));
            sb.append(" : ");
            sb.append(get(keys.get(i)));
            sb.append(" }, ");
        }

        // Closing bracket
        sb.append("]");

        // Remove last comma
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.deleteCharAt(sb.lastIndexOf(" "));
        
        return sb.toString();

    }

    /**
     * Get a string representation of the linked list in a certain bucket
     * @param index The index in the array that holds the buckets
     * @return A string representation of the bucket
     */
    public String getBucket(int index){
        StringBuilder sb = new StringBuilder();
        MapEntry<K, E> bucket = bucketList[index];
        
        sb.append("[");

        // Loop over all but the last item
        while(bucket.hasNext()){
            sb.append("{ ");
            sb.append(bucket.getKey());
            sb.append(" : ");
            sb.append(bucket.getEntry());
            sb.append("}, ");
            bucket = bucket.getNext(); // Next item
        }

        // Just append the last item
        sb.append("{ ");
        sb.append(bucket.getKey());
        sb.append(" : ");
        sb.append(bucket.getEntry());
        sb.append("}]");

        return sb.toString();
    }
    
}