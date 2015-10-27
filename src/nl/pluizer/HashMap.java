package nl.pluizer;

import java.util.ArrayList;
import java.util.List;

/**
 * A data structure to map keys to values.
 * @param <K>   the type of the key to use
 * @param <V>   the type of the values to map
 */
public class HashMap<K, V> {

    private final List<Bucket<K, V>> buckets = new ArrayList<>();

    private int mapSize = 0;

    private final HashFunction hashFunction;

    /**
     * The default initial number of buckets to use for this
     * HashMap, deliberately kept low here to test growing.
     */
    private static final int INITIAL_BUCKET_COUNT = 4;

    /**
     * Create a new HashMap with a specific hashing function.
     * @param hashFunction      the hashing function to use
     */
    public HashMap(HashFunction hashFunction) {
        this.hashFunction = hashFunction;
        // Create the initial buckets.
        while (buckets.size() < HashMap.INITIAL_BUCKET_COUNT) buckets.add(new Bucket<>(this));
    }

    /**
     * Create a new HashMap using the default hashing function.
     */
    public HashMap() {
        this(new LazyHashFunction());
    }

    /**
     * Increases the amount of buckets by two.
     */
    private void growBuckets() {
        int bucketCount = buckets.size();
        while (buckets.size() < bucketCount*2) buckets.add(new Bucket<>(this));
        // By growing the amount of buckets the mapped indices have become invalid,
        // resetting them ...
        // (Buckets will be modified during this loop so we cannot use iteration.)
        for (int i=0; i<bucketCount; i++) {
            buckets.get(i).reset();
        }
    }

    /**
     * Returns the bucket index from a key's hash value.
     * @param key               the key to convert to an index
     * @return                  the buckets' index
     */
    private int getIndex(K key) {
        int hash = hashFunction.hash(key);
        int i = hash % buckets.size();
        return i < 0 ? i + buckets.size() : i; // Index must be positive
    }

    /**
     * Maps a key to a value. Mapping a non-unique key will result
     * in a RuntimeException.
     * @param key               the key to use
     * @param update            when inserting a non unique key update the
     *                          value (true) or throw a RuntimeException (false)
     * @param value             the value to map
     */
    public void insert(K key, V value, boolean update) {
        boolean collided = buckets.get(getIndex(key)).insert(key, value);
        mapSize++;
        // Simple naive way to test if the amount of buckets must be grown
        // by checking if this key collided with an other.
        // To prevent the amount of buckets from growing to large
        // we won't increase the amount of buckets if there are more than
        // twice as many than mapped values.
        if (collided && buckets.size() / 2 <= size()) {
            growBuckets();
        }
    }

    /**
     * Maps a key to a value. Mapping a non-unique key will result
     * in a RuntimeException.
     * @param key               the key to use
     * @param value             the value to map
     */
    public void insert(K key, V value) {
        insert(key, value, false);
    }

    /**
     * Returns the number of items mapped to a key.
     * @return                  the amount of items mapped.
     */
    public int size() {
        return mapSize;
    }

    /**
     * Returns the value mapped to key.
     * @param key               the key
     * @return                  the value of the key ot
     *                          null if there is no such key
     */
    public V get(K key) {
        return buckets.get(getIndex(key)).get(key);
    }
}
