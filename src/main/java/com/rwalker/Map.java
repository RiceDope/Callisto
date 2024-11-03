package com.rwalker;

import java.util.Comparator;

/**
 * Class that implements HashMaps, LinkedHashMaps
 * As standard keys are kept in the order they are inserted as well as a sorted order
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
    private Sequence<K> sortedKeys; // Stores a list of all keys sorted

    /**
     * Constructor used if no parameters are passed
     */
    public Map() {
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
    }

    /**
     * Constructor used if number of buckets and load factor are passed
     * @param buckets Default starting number of buckets (16)
     * @param loadFactor Default starting load factor (0.75)
     * @param expansionFactor Default starting expansion factor (2)
     * @param keyComp Comparator for the key in the Map (Used for more efficient lookup)
     */
    public Map(int buckets, float loadFactor, float expansionFactor, Comparator<K> keyComp){
        this.buckets = buckets; // Starting number of buckets to use
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
        this.loadFactor = loadFactor; // Set the load factor
        sortedKeys = new Sequence<>(keyComp); // Set the sorted keys list to use the comparator specified
        sortedKeys.sortOnwards();
    }

    /**
     * Returns the keyset in the order they were inserted
     * @return
     */
    public Sequence<K> keySet(){
        return keys;
    }

    /**
     * Returns the keyset in the order specified in the constructor
     * @return The keyset in a sorted order
     */
    public Sequence<K> sortedKeySet(){
        return sortedKeys;
    }

    /**
     * Replace the value for a key only if it already exists
     * @param key The key to use
     * @param entry The value to replace current value
     */
    public void replace(K key, E entry) {

        // More efficient lookup
        if (sortedKeys != null){
            if (sortedKeys.contains(key)){
                put (key, entry);
            }
        } else { // If not set
            if (keys.contains(key)) {
                put(key, entry);
            }
        }
    }

    /**
     * Replace the value held at the key
     * Only if it already exists and the entru matches current entry
     * @param key
     * @param entry
     * @param currentEntry
     */
    public void replace(K key, E entry, E currentEntry) {

        // More efficient lookup
        if (sortedKeys != null){
            if (sortedKeys.contains(key) && get(key).equals(currentEntry)){
                put (key, entry);
            }
        } else { // If not set
            if (keys.contains(key) && get(key).equals(currentEntry)) {
                put(key, entry);
            }
        }
    }

    /**
     * Puts the entry in the map if it is not already present or is mapped to null
     * Otherwise it will return the current value
     * @param key The key to use
     * @param entry The entry to use
     * @return null if put is successfull / value if already exists
     */
    public E putIfAbsent(K key, E entry) {
        if (!keyExists(key) || mappedToNull(key)) {
            put(key, entry);
            return null;
        } else {
            return get(key);
        }
    }

    /**
     * Is the key currently mapped to be null
     * @param key The key to check
     * @return true if null / false if value
     */
    public boolean mappedToNull(K key){
        E temp = get(key);
        if (temp == null){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if a key exists in the current map
     * @param key The key to be checking
     * @return A boolean value
     */
    public boolean keyExists(K key){

        // More efficient lookup
        if (sortedKeys != null){
            return sortedKeys.contains(key);
        }

        return keys.contains(key);
    }

    /**
     * Remove a specific element based on the key
     * @param key
     */
    public void remove (K key){

        // Check if the key is in the Map
        if (sortedKeys != null) { // More efficient lookup
            if (!sortedKeys.contains(key)){
                throw new IllegalArgumentException("Key not in Map");
            }
        } else if (!keys.contains(key)){ // If not set
            throw new IllegalArgumentException("Key not in Map");
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
        if (sortedKeys != null) { // More efficient lookup
            if (!sortedKeys.contains(key)) {
                throw new IllegalArgumentException("Key not in Map");
            }
        } else if (!keys.contains(key)){ // If not set
            throw new IllegalArgumentException("Key not in Map");
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
     * Put all terms from a map into this map
     * @param otherMap The map to take all terms from
     */
    public void putAll(Map<K, E> otherMap) {

        Sequence<K> otherKeys = otherMap.keySet();

        for (int i = 0; i < otherKeys.length(); i++){
            K key = otherKeys.get(i);
            put(key, otherMap.get(key));
        }
    }

    /**
     * Insert a key value pair into the HashMap will override any current entry at that key
     * @param key The key to insert with
     * @param entry The entry to insert
     */
    public void put (K key, E entry){

        // Check if we must expand the sub-array
        if (loadFactorExceeded()){
            rehash();
            internalPut(key, entry, bucketList, keys, false, buckets, true);
        } else {
            internalPut(key, entry, bucketList, keys, false, buckets, true);
        }

    }

    /**
     * Internal version of put allows for finer grained control over putting anything inside of the map
     * @param key The key to insert with
     * @param entry The entry to insert
     * @param mapToAddTo The map to add to
     * @param keysToAdd The list of keys to be used (Passed as reference)
     * @param newBuckets Are there new buckets being added (i.e. are we rehashing at the minute)
     * @param buckets How many buckets are there
     */
    private void internalPut(K key, E entry, MapEntry<K, E>[] mapToAddTo, Sequence<K> keysToAdd, boolean newBuckets, int buckets, boolean addToSorted){
        
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

                // Check if we are adding to sorted
                if (sortedKeys != null && addToSorted == true) {
                    sortedKeys.append(key);
                }

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
                // Check if we are adding to sorted
                if (sortedKeys != null && addToSorted == true) {
                    sortedKeys.append(key);
                }
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
            internalPut(keys.get(i), internalGet(keys.get(i), savedList), bucketList, keysToAdd, true, newAmountOfBuckets, false);
        }

        buckets = newAmountOfBuckets;
        keys = keysToAdd;
        
        // Adds all of the specified keys to the sorted list if it is set
        if (sortedKeys != null) {
            sortedKeys.appendAll(keysToAdd);
        }

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

    /**
     * Get the bucket that a specific term is located at
     * @param key The key to check for
     * @return The index of the bucket its contained in
     */
    public int getBucketForKey(K key) {
        return generateHashIndex(key);
    }
    
}