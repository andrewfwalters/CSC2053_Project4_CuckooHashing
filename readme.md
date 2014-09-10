Project 4: Cuckoo Hashing
=========================

Write a program that implements a symbol table using cuckoo hashing in two different ways: separate chaining and open addressing. Both versions should implement the same operations:

public class CuckooSeparateChainingHashST<Key, Value> {
   public   CuckooProbingHashST()                         // create an empty hash table - use 16 as default size
   public   CuckooProbingHashST(int capacity)             // create a cuckoo probing hash table of given capacity
   public           boolean isEmpty()                     // is the symbol table empty?
   public               int size()                        // number of key-value pairs in the symbol table
   public           boolean contains(Key key)             // does a key-value pair with the given key exist in the symbol table?
   public              void put(Key key, Value val)       // insert the key-value pair into the symbol table
   public             Value get(Key key)                  // return the value associated with the given key, null if no such value
   public              void delete(Key key)               // delete the key (and associated value) from the symbol table
   public     Iterable<Key> keys()                        // return all of the keys as an Iterable
   public       static void main(String[] args)           // unit test client  
}

Similarly for the datatype  CuckooProbingHashST 

- Separate chaining approach (key-value pairs are kept outside the table, in linked lists): keys are hashed to two positions; they are placed in one or the other, depending which bin has fewer items. If both bins have the same number of items, the key-value pair goes in either bin, chosen randomly.

- Open addressing approach (key-value pairs are stored in the table): keys are hashed to two positions; they are placed in one or the other, depending which one is availble (choose one randomly, if both are available). If neither is available, delete one of the keys from the table, replacing it with the key to be inserted, then try to insert the deleted key in its alternative position. Be sure to check for cycles, so you don't get an infinite loop! If a cycle is detected, resize the table.

Your implementation should support put(), get() and contains() in constant average time. The open addressing version (CuckooProbingHashST) should support get() and contains() using at most two key comparisons. You can assume that clients will not pass key or value arguments equal to null (so, no need to check). Use the code from the textbook: SeparateChainingHashST.java and  LinearProbingHashST.java as a starting point, modifying it as described above to use cuckoo hashing instead of linear probing. 

**Using parallel arrays**

The implementation given in the text uses parallel arrays for the keys and values. Optionally, you can modify the code to use key-value objects, as you did in project 2. Note that it is best to do this before you begin. Although this is an optional step, it is strongly recommended, especially if you think you might need a bit more practice using generics.

**Implementing two hash functions**

You should implement two hash functions. Take care to ensure that the two hash functions never produce the same result (i.e., hash1(x) = hash2(x)), beccause you need two distinct alternatives for the position of each key in the table.

**Other private methods** 

Your implementation can include any number of private methods (in addition to the two hash functions mentioned above). At the very least, you should implement a resize() method. It is also recommended that you implement a dump() method that outputs the entire contents of the table (including empty positions), to help with debugging.

**More about cuckoo hashing**

If you would like to learn more about cuckoo hashing, the best place to start is Michael Mitzenmacher's blog posts:  Cuckoo hashing part1 part 2  part 3 . You can also find a great overview of applications of hashing to extremely constrained problems, such as content addressable memories used in routers, in these slides from his website or read one of  his many papers on the subject. 

**YouTubeComedySlam client**

Reuse your client from project 2 to test your code and to compare its performance with other implementations. Test your program with several subsets of the comedy slam dataset from the UCI Machine Learning Repository, such as comedy_comparisons_tiny.txt.  

**Analysis of running time**

Compare your two cuckoo hashing implementations with other implementations of symbol tables:

- CuckooSeparateChainingHashST
- CuckooProbingHashST
- ST (your own implementation from Project 2)
- RedBlackBST
- SeparateChainingHashST
- LinearProbingHashST

Use the stopwatch data type from the standard library to measure the total running time of your client with different sizes of input files (How does doubling N affect the total running time? ) 

Note about memory usage. Cuckoo hashing does not use memory in any way different than other forms of hashing, so there is no need to analyze memory usage for this project. 

**Submission**  

Submit a zipped folder containing: CuckooSeparateChainingHashST.java, CuckooProbingHashST.java, readme.txt , and your project report, which should also be printed and handed in on the due date. The report should include all of the above, plus some sample runs and screenshots of your program to demonstrate that it works well. 

This assignment was developed by M A Papalaskari. 
Copyright © 2014.