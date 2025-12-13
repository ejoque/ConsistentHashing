/**
 * A bucket object stores information about itself, including the size of the bucket, an array of keys in the bucket, and its hash index.
 * @author Elizabeth Joque
 * @date 12/6/2025
 */
public class Bucket {
    private int bucketSize;
    private Integer[] keys;
    private int bucketIndex; 

    /**
     * Constructor for a bucket
     * @param bucketSize The number of keys the bucket can hold
     * @param bucketIndex The hash index of the bucket (given when created)
     */
    public Bucket(int bucketSize, int bucketIndex) {
        this.bucketSize = bucketSize;
        keys = new Integer[this.bucketSize];
        this.bucketIndex = bucketIndex;
    }

    /**
     * Returns a string representation of the bucket
     * @return Information about the bucket, including its index and the keys stored inside of it
     */
    @Override
    public String toString() {
        String info = "Bucket[" + this.bucketIndex + "]: ";
        for (Integer k : keys) {
            if (k != null) {
                info += k + ",";
            }
        }
        return info;
    }

    public boolean isFull() {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Inserts the given key into the bucket. If the bucket is full, it doesn't do anything. 
     * @param key
     */
    public void add(int key) {
        // put it in the bucket
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == null) {
                keys[i] = key;
                break;
            }
        }
    }

    /**
     * Checks whether or not the bucket contains the given search 
     * @param key An integer
     * @return True if the key is in the bucket, false if not. 
     */
    public boolean contains(int key) {
        for (Integer k : keys) {
            if (k != null && k.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * To be used on overflow (when a bucket is full) - returns keys to be rehashed when the bucket is full, removes them from the bucket they were in
     * @return
     */
    public Integer[] checkKeys(int ringSize, int pred_index, int new_index) {
        Integer[] invalidKeys = new Integer[this.bucketSize];
        
        // do the modulo, then compare to the bucket's index.  
        for (int i = 0; i < keys.length; i++) {
            int hash = keys[i] % ringSize;
            if (pred_index < hash && hash <= new_index) {
                invalidKeys[i] = keys[i];
                keys[i] = null;
            }
        }

        return invalidKeys;
    }

    public int getIndex() {
        return bucketIndex;
    }
}

