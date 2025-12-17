Project Title: Consistent Hashing Algorithm
Author: Elizabeth Joque
Date: 12/17/2025

Description:
The ConsistentHasher class contains the main method, which allows the user to run a simulation of inserting integer keys in a consistent hash index. 
The user must provide two arguments upon the start of the program: the size of the hash ring, and the size of each bucket. 
The HashRing class stores the hash ring in which all buckets containing keys are contained. This is built on a circular linked list. 
The CircularLinkedList provides an implementation of a doubly linked list that is a closed loop (head points to tail, tail points to head).
The Bucket class defines a bucket, which stores a list of keys and its own index. 

How to run:
The code is stored within four Java files. Compile all files, and run ConsistentHasher to run the simulation.

Test cases:
- Insert first key. This should create a new bucket of the bucket size given at the start of the program. 
- Trivial insertion. Hash onto ring, and there's space in the nearest bucket, so the key is put into that bucket.
- Insertion that triggers a split. Hash onto ring, and there's not space in the nearest bucket. This should result in the bucket being split, and keys from the old bucket put into the new bucket. The new bucket is halfway between old bucket and predecessor.

