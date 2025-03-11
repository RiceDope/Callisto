package com.rwalker;

import java.util.Comparator;
import java.util.Iterator;

import com.rwalker.sequenceStrategies.SequenceStrategies;

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
public class Map <K, E> implements ModernCollections<MapEntry<K, E>>, Iterable<MapEntry<K, E>>{

    // Default values benchmarked simply in results 030225-1430
    private int buckets = 256;
    private MapEntry<K, E>[] bucketList;
    private float loadFactor = 1.0f; 
    private float expansionFactor = 2.0f;
    private Set<K> keys = new Set<>();
    private Sequence<K> sortedKeys;
    private Comparator<K> keyComp; // Comparator for sorted keys

    /**
     * Constructor used if no parameters are passed
     */
    public Map() {
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
    }

    /**
     * Allows for specification of the comparator for sorted keys
     * @param keyComp The comparator for sortedKeys
     */
    public Map(Comparator<K> keyComp) {
        bucketList = new MapEntry[buckets];
        sortedKeys = new Sequence<>(keyComp, SequenceStrategies.DEFAULT);
        this.keyComp = keyComp;
        sortedKeys.sortOnwards();
    }

    public Map(int buckets) {
        this.buckets = buckets; // Starting number of buckets to use
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
    }

    public Map(int buckets, Comparator<K> keyComp) {
        this.buckets = buckets; // Starting number of buckets to use
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
        sortedKeys = new Sequence<>(keyComp, SequenceStrategies.DEFAULT);
        this.keyComp = keyComp;
        sortedKeys.sortOnwards();
    }

    public Map(float loadFactor) {
        this.loadFactor = loadFactor; // Set the load factor
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
    }

    public Map(float loadFactor, Comparator<K> keyComp) {
        this.loadFactor = loadFactor; // Set the load factor
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
        sortedKeys = new Sequence<>(keyComp, SequenceStrategies.DEFAULT);
        this.keyComp = keyComp;
        sortedKeys.sortOnwards();
    }

    public Map(Float expansionFactor) {
        this.expansionFactor = expansionFactor; // Set the expansion factor
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
    }

    public Map(Float expansionFactor, Comparator<K> keyComp) {
        this.expansionFactor = (float) expansionFactor; // Set the expansion factor
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
        sortedKeys = new Sequence<>(keyComp, SequenceStrategies.DEFAULT);
        this.keyComp = keyComp;
        sortedKeys.sortOnwards();
    }

    public Map(int buckets, float loadFactor) {
        this.buckets = buckets; // Starting number of buckets to use
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
        this.loadFactor = loadFactor; // Set the load factor
    }

    public Map(int buckets, Float expansionFactor) {
        this.buckets = buckets; // Starting number of buckets to use
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
        this.expansionFactor = expansionFactor; // Set the expansion factor
    }

    public Map(float loadFactor, Float expansionFactor) {
        this.loadFactor = loadFactor; // Set the load factor
        this.expansionFactor = expansionFactor; // Set the expansion factor
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
    }

    public Map(int buckets, float loadFactor, Comparator<K> keyComp) {
        this.buckets = buckets; // Starting number of buckets to use
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
        this.loadFactor = loadFactor; // Set the load factor
        sortedKeys = new Sequence<>(keyComp, SequenceStrategies.DEFAULT); // Set the sorted keys list to use the comparator specified
        this.keyComp = keyComp;
        sortedKeys.sortOnwards();
    }

    public Map(int buckets, Float expansionFactor, Comparator<K> keyComp) {
        this.buckets = buckets; // Starting number of buckets to use
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
        this.expansionFactor = expansionFactor; // Set the expansion factor
        sortedKeys = new Sequence<>(keyComp, SequenceStrategies.DEFAULT); // Set the sorted keys list to use the comparator specified
        this.keyComp = keyComp;
        sortedKeys.sortOnwards();
    }

    public Map(float loadFactor, Float expansionFactor, Comparator<K> keyComp) {
        this.loadFactor = loadFactor; // Set the load factor
        this.expansionFactor = expansionFactor; // Set the expansion factor
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
        sortedKeys = new Sequence<>(keyComp, SequenceStrategies.DEFAULT); // Set the sorted keys list to use the comparator specified
        this.keyComp = keyComp;
        sortedKeys.sortOnwards();
    }

    /**
     * Constructor used if number of buckets, load factor and expansion factor are passed
     * @param buckets Default starting number of buckets (16)
     * @param loadFactor Default starting load factor (0.75)
     * @param expansionFactor Default starting expansion factor (2)
     */
    public Map(int buckets, float loadFactor, float expansionFactor){
        this.buckets = buckets; // Starting number of buckets to use
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
        this.loadFactor = loadFactor; // Set the load factor
    }

    /**
     * Constructor used if number of buckets, load factor, expansion factor and Comparator are passed
     * @param buckets Default starting number of buckets (16)
     * @param loadFactor Default starting load factor (0.75)
     * @param expansionFactor Default starting expansion factor (2)
     * @param keyComp Comparator for the key in the Map (Used for more efficient lookup)
     */
    public Map(int buckets, float loadFactor, float expansionFactor, Comparator<K> keyComp){
        this.buckets = buckets; // Starting number of buckets to use
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
        this.loadFactor = loadFactor; // Set the load factor
        sortedKeys = new Sequence<>(keyComp, SequenceStrategies.DEFAULT); // Set the sorted keys list to use the comparator specified
        this.keyComp = keyComp;
        sortedKeys.sortOnwards();
    }

    /**
     * How many mappings exist in the current context
     * @return Number of mappings
     */
    public int size() {
        return keys.size();
    }

    /**
     * Returns the keyset in the order they were inserted
     * @return
     */
    public Set<K> keySet(){
        return keys;
    }

    /**
     * Returns the keyset in the order specified in the constructor
     * @return The keyset in a sorted order
     */
    public Sequence<K> sortedKeySet(){
        return sortedKeys;
    }

    public void clear() {
        bucketList = new MapEntry[256]; // Initial amount of buckets is reset on clear
        keys = new Set<>();
        if (sortedKeys != null) {
            sortedKeys = new Sequence<>(keyComp);
        }
    }

    /**
     * Replace the value for a key only if it already exists
     * @param key The key to use
     * @param entry The value to replace current value
     */
    public void replace(K key, E entry) {

        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        // TODO: Null support needs to be added

        if (keyExists(key)){
            put (key, entry);
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
        if (keyExists(key) && get(key).equals(currentEntry)){
            put(key, entry);
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
     * Does the value held match given value
     * @param key Ket to use
     * @param entry Entry to compare
     * @return boolean true if same / false if not
     */
    public boolean isEquals(K key, E entry){
        if (get(key).equals(entry)){
            return true;
        }

        return false;
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
        //TODO: Now we are using a set do we need to use sorted. Is it acctually faster.
        return keys.contains(key);
    }

    /**
     * Returns true if the specific entry exists in the map. This is to say a full MapEntry Key and Value
     * @param e The MapEntry object to check if exists
     * @return boolean true if exists / false if not
     */
    public boolean contains(MapEntry<K, E> e) {
        return keyExists(e.getKey()) && get(e.getKey()).equals(e.getEntry());
    }

    /**
     * Removes the given key if it's current value matches entry
     * @param key Key to remove
     * @param entry Entry to compare
     */
    public void remove(K key, E entry) {
        if (isEquals(key, entry)) {
            remove(key);
        }
    }

    /**
     * Remove a specific element based on the key
     * @param key
     */
    public void remove (K key){

        if (!keyExists(key)){
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
                keys.remove(key); // Remove key
                if (sortedKeys != null) {
                    sortedKeys.remove(sortedKeys.firstIndexOf(key));
                }
                bucketList[index] = null; // Set the bucket to be null now

            } else {

                // We need to set its sucessive element to be the root of the linked list
                // in that bucket
                keys.remove(key); // Remove key
                if (sortedKeys != null) {
                    sortedKeys.remove(sortedKeys.firstIndexOf(key));
                }
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

            keys.remove(key);
            if (sortedKeys != null) {
                sortedKeys.remove(sortedKeys.firstIndexOf(key));
            }
        }
    }

    /**
     * Get an item from the Map
     * @param key The key to get the value for
     * @return The value that was stored at that key
     */
    public E get(K key){

        if (!keyExists(key)){
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
     * Get the MapEntry for a specific key
     * @param key The key to get the entry for
     * @return The MapEntry for the key
     */
    private MapEntry<K, E> getMapEntry(K key) {

        if (!keyExists(key)){
            throw new IllegalArgumentException("Key not in Map");
        }

        int index = generateHashIndex(key);
        MapEntry<K, E> temp = bucketList[index];

        while (temp != null) {
            if (temp.getKey().equals(key)) {
                return temp;
            }
            temp = temp.getNext();
        }

        return null;
    }

    /**
     * Put all terms from a map into this map
     * @param otherMap The map to take all terms from
     */
    public void putAll(Map<K, E> otherMap) {

        Set<K> otherKeys = otherMap.keySet();

        for (K key : otherKeys){
            put(key, otherMap.get(key));
        }
    }

    /**
     * Is the map empty
     * @return boolean true if empty / false if not
     */
    public boolean isEmpty() {
        return keys.isEmpty();
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
    private void internalPut(K key, E entry, MapEntry<K, E>[] mapToAddTo, Set<K> keysToAdd, boolean newBuckets, int buckets, boolean addToSorted){
        
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
                keysToAdd.add(key);

                // Check if we are adding to sorted
                if (sortedKeys != null && addToSorted == true) {
                    sortedKeys.add(key);
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
                keysToAdd.add(key);
                // Check if we are adding to sorted
                if (sortedKeys != null && addToSorted == true) {
                    sortedKeys.add(key);
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
        Set<K> keysToAdd = new Set<>(keys.size());

        // Loop over keySet and add to the new bigger array
        for (K key : keys) {
            internalPut(key, internalGet(key, savedList), bucketList, keysToAdd, true, newAmountOfBuckets, false);
        }

        buckets = newAmountOfBuckets;
        keys = keysToAdd;
        
        // Adds all of the specified keys to the sorted list if it is set
        if (sortedKeys != null) {
            sortedKeys.addAll(keysToAdd);
        }

    }

    /**
     * Should we be expanding the number of buckets
     * @return boolean true if we should expand
     */
    private boolean loadFactorExceeded() {

        if (keys.size() >= buckets*loadFactor){
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
        for (K key : keys){
            sb.append("{ ");
            sb.append(key);
            sb.append(" : ");
            sb.append(get(key));
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

        if (index > bucketList.length || index < bucketList.length) {
            throw new IllegalArgumentException("Index out of bounds");
        }

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

    public Iterator<MapEntry<K, E>> iterator() {
        return new MapIterator();
    }

    private class MapIterator implements Iterator<MapEntry<K, E>> {
        private Iterator<K> keyIterator = keys.iterator();

        @Override
        public boolean hasNext() {
            return keyIterator.hasNext();
        }

        @Override
        public MapEntry<K, E> next() {
            K key = keyIterator.next();
            return getMapEntry(key);
        }
    }
    
}