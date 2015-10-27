package nl.pluizer;

/**
 * Java objects already have a handy .hashCode method so I just used
 * that. Not sure if I'm cheating now though.
 */
public class LazyHashFunction implements HashFunction {
    public int hash(Object object) {
        return object.hashCode();
    }
}
