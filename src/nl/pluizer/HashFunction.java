package nl.pluizer;

/**
 * An interface with one method that converts any Object
 * to an integer hash.
 */
public interface HashFunction {
    int hash(Object object);
}
