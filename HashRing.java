/**
 * A hash ring object, built upon a circular linked list.
 * @author Elizabeth Joque
 * @date 12/9/2025
 */
public class HashRing {

    private CircularLinkedList<Bucket> hashRing;
    private int ringSize;
    private int bucketSize;

    public HashRing(int ringSize, int bucketSize) {
        // here's the constructor
        this.ringSize = ringSize;
        this.bucketSize = bucketSize;
        this.hashRing = new CircularLinkedList<Bucket>();
    }

    public boolean insertKey(int key) {
        
        if (!searchIndex(key)) {
            // hash by doing key % ringSize (this gets the hash index of the bucket)
            int index = key % ringSize;
            Bucket check = hashRing.getNode(index);

            if (check != null) {
                // a bucket exists! 
                if (!check.isFull()) {
                    check.add(key);
                    return true;
                }
                else {
                    splitBucket(check);
                    return insertKey(key);
                }
            }
            else {
                // the list is empty, so we have to create a bucket
                Bucket b = new Bucket(bucketSize,index);
                hashRing.insertNode(index,b);
                b.add(key);
                return true;
            }
        }
        return false; // key already exists
    }

    /**
     * Splits a bucket, for use when a bucket is overflown.
     * @param b The bucket to be split
     */
    private void splitBucket(Bucket b) {
        // so, need to create a new bucket
        int newIndex = -1; // store where the new bucket will go 
        Bucket new_b = null; // the actual new bucket

        int b_index = b.getIndex(); 
        int pred_index = hashRing.getPreviousIndex(b_index); // the clockwise predecessor to the original bucket b


        // if the index of the original bucket is greater than the predecessor
        if (b_index > pred_index) {
            // this bucket goes halfway between the predecessor bucket and the original bucket
            newIndex = Math.floorDiv(b_index + pred_index, 2);
            new_b = new Bucket(bucketSize, newIndex);
        }
    
        else {
            newIndex = (pred_index + Math.floorDiv(ringSize - pred_index + b_index, 2)) % ringSize;
            new_b = new Bucket(bucketSize, newIndex);
        }

        // add it to the list, in order.
        hashRing.insertNode(newIndex, new_b);

        int prev = hashRing.getPreviousIndex(newIndex);
        // have to fix keys 
        Integer[] fixKeys = b.checkKeys(ringSize,prev,newIndex);
        for (int i = 0; i < fixKeys.length; i++) {
            if (fixKeys[i] != null) {
                new_b.add(fixKeys[i]);
            }
        }
    }

    public boolean searchIndex(int key) {
        // perform the hash of key % r, and go into that bucket
        int index = key % ringSize;
        Bucket check = hashRing.getNode(index);
        if (check != null) {
            // the bucket exists!
            if (check.contains(key)) {
                return true;
            }
        }
        
        return false; // we didn't find it :(
    }

    public String printIndex() {
        String printIndex = "Consistent Hashing Index \nr = " + ringSize + "\nBucket size: " + this.bucketSize + "\nBuckets:\n" + hashRing.toString();
        return printIndex;
    }

}

