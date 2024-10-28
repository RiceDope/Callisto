package com.rwalker;

/**
 * Class that implements HashMaps
 * 
 * Created: 28/10/2024
 * Updated: 28/10/2024
 * 
 * @author Rhys Walker
 * 
 */

@SuppressWarnings("unchecked")
public class Map <K, E> {

    private int buckets; // The default number of buckets to be used
    private MapEntry<K, E>[] bucketList; // Essentially a bucket
    private Sequence<K> keys = new Sequence<>(); // Stores a list of all keys

    public Map(int buckets){
        this.buckets = buckets; // Starting number of buckets to use
        bucketList = new MapEntry[buckets]; // Declare a sub-array for storage
    }

    /**
     * Get an item from the Map
     * @param key The key to get the value for
     * @return The value that was stored at that key
     */
    public E get(K key){

        // Get the hashed index of the key
        int index = generateHashIndex(key);

        // Take the current MapEntry
        MapEntry<K, E> temp = bucketList[index];

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

        // Calculate the index to insert into
        int index = generateHashIndex(key);

        if (keys.contains(key)){
            // We will overwrite as it is already contained
            MapEntry<K, E> temp = bucketList[index];

            while (temp.getKey() != key){
                temp = temp.getNext();
            }

            temp.changeEntry(entry);

        } else {
            // Key does not already exist

            if (bucketList[index] == null){
                // Bucket is empty add a new entry
                bucketList[index] = new MapEntry<K, E>(key, entry);
            } else {
                // Bucket is not empty find and add to the LinkedList
                MapEntry<K, E> temp = bucketList[index];

                // While next entry is not null
                while (temp.getNext() != null){
                    temp = temp.getNext();
                }

                // When we get to the null bit set the next to be our term
                temp.setNext(new MapEntry<K, E>(key, entry));
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
    
}