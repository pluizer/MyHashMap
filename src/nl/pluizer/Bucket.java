package nl.pluizer;

import java.util.LinkedList;
import java.util.List;

/**
 * Key-value pairs a divided among buckets in a HashMap. For fast insertions and searches each
 * bucket should ideally only contain one key-value pair. If a hashing functions does not "spread"
 * evenly or when there are too little buckets some key-value pairs must share the same bucket.
 * @param <K>                   the type of the key to use
 * @param <V>                   the type of the value to map
 */
class Bucket<K, V> {

    /**
     * A simple key-value tuple.
     */
    private class KeyValuePair {
        final K key; V value;

        KeyValuePair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final List<KeyValuePair> pairs = new LinkedList<>();

    private final HashMap<K, V> hashMap;

    Bucket(HashMap<K, V> hashMap) {
        this.hashMap = hashMap;
    }

    /**
     * Appends a key and value pair to this bucket.
     * @param key               the key to insert
     * @param value             the value to map
     * @return                  true if this bucket already had one or
     *                          more items(collision), false otherwise.
     */
    boolean insert(K key, V value, boolean update) {
        if (get(key) != null && !update) {
            throw new RuntimeException("Duplicate key");
        } else {
            for (KeyValuePair pair : pairs) {
                if (pair.key == key) {
                    pair.value = value;
                    break; // Bucket can only hold one unique key.
                }
            }
        }
        boolean isEmpty = pairs.isEmpty();
        pairs.add(new KeyValuePair(key, value));
        return !isEmpty;
    }

    /**
     * Retrieves a value from this bucket by its key.
     * @param key               the key that maps to the value
     * @return                  the value, or if this bucket did not hold
     *                          a value mapped to the key, null.
     */
    V get(K key) {
        for (KeyValuePair tuple : pairs) {
            if (tuple.key == key) {
                return tuple.value;
            }
        }
        return null;
    }

    /**
     * Mapped values might be in the wrong bucket once a HashMap has
     * grown. This function clears the entire bucket after placing
     * the mapped values to their new correct buckets.
     */
    void reset() {
        if (!pairs.isEmpty()) {
            List<KeyValuePair> oldValues = new LinkedList<>(pairs);
            pairs.clear();
            oldValues.forEach((tuple) -> hashMap.insert(tuple.key, tuple.value, true));
        }
    }
}
