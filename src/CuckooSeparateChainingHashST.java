/*******************************************************************************
 * 
 * @author Andrew Walters
 * @param <Key>
 * @param <Value> 
*******************************************************************************/

public class CuckooSeparateChainingHashST<Key, Value> {
    
    //class variables
    private int capacity;
    private SequentialSearchST<Key, Value>[] hashTable;
    
    //default constructor - use 16 as default size
    public CuckooSeparateChainingHashST() {
        this(16);
    }//end default constructor 
    
    //constructor
    public CuckooSeparateChainingHashST(int capacity) {
        this.capacity = capacity;
        hashTable = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[capacity];
        for (int i = 0; i < capacity; i++)
            hashTable[i] = new SequentialSearchST<Key, Value>();
    }//end constructor
    
    //is the symbol table empty?
    public boolean isEmpty() {
        if(size() == 0) {return true;}
        else {return false;}
    }//end isEmpty
    
    // number of key-value pairs in the symbol table
    public int size() {
        int s = 0;
        //get the size of each linked list
        for(int i = 0; i < capacity; i++) {
            s += (hashTable[i]).size();
        }
        return s;
    }//end size
    
    //does a key-value pair with the given key exist in the symbol table?
    public boolean contains(Key key) {
        return get(key) != null;
    }//end contains
    
    //insert the key-value pair into the symbol table
    public void put(Key key, Value val) {        
        int myHash;
        int myHash1 = hash1(key);
        int myHash2 = hash2(key);
        int hash1Size = (hashTable[myHash1]).size();
        int hash2Size = (hashTable[myHash2]).size();
        
        //determine which hash to use
        if(hashTable[myHash1].contains(key)) {
            myHash = myHash1;
        }
        else if(hashTable[myHash2].contains(key)) {
            myHash = myHash2;
        }
        else if(hash1Size < hash2Size) {
            myHash = myHash1;
        }
        else if(hash1Size != hash2Size) {
            myHash = myHash2;
        }
        else if(StdRandom.bernoulli()) {
            myHash = myHash1;
        }
        else {
            myHash = myHash2;
        }
        
        //add entry at the chosen hash
        hashTable[myHash].put(key, val);
        
        //double table size if average length of list >= 10
        if (size() >= 10*capacity) {
            resize(2*capacity);
        }
    }
    
    //return the value associated with the given key, null if no such value
    public Value get(Key key) {
        
        //create a temp value
        Value myVal;
        
        //search hash1 list
        if( (myVal = hashTable[hash1(key)].get(key)) != null ) {
            return myVal;
        }
        else if( (myVal = hashTable[hash2(key)].get(key)) != null ) {
            return myVal;
        }
        else { 
            return null;
        }
    }//end get
    
    //delete the key (and associated value) from the symbol table
    public void delete(Key key) {
        if(hashTable[hash1(key)].contains(key)) {
            hashTable[hash1(key)].delete(key);
        }
        else if(hashTable[hash2(key)].contains(key)) {
            hashTable[hash2(key)].delete(key);
        }
    }//end delete
    
    //return all of the keys as an Iterable
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for(int i = 0; i < capacity; i++) {
            for(Key key : hashTable[i].keys()) {
                queue.enqueue(key);
            }
        }
        return queue;
    }//end keys
    
    //first hashing method
    private int hash1(Key key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }//end hash 1
    
    //second hashing method
    private int hash2(Key key) {
        return ((key.hashCode() & 0x7fffffff) + (capacity/2)) % capacity;
    }//end hash2
    
    private void resize(int newCapacity) {
        
        //create a new table
        CuckooSeparateChainingHashST<Key, Value> temp = new CuckooSeparateChainingHashST<Key, Value>(newCapacity);
        
        //outer loop through each entry in hashTabel
        for (int i = 0; i < capacity; i++) {
            //inner loop through each pair in each entry
            for(Key key : hashTable[i].keys()) {
                temp.put(key,hashTable[i].get(key));
            }//end inner loop
        }//end outer loop
        
        this.capacity = temp.capacity;
        this.hashTable = temp.hashTable;
    }//end resize
    
    public void dump() {
        System.out.println("Cuckoo Separate Chaining Hash Symbol Table:");
        System.out.println("Capacity: " + capacity + "\n");

        //outer loop through each entry in hashTabel
        for (int i = 0; i < capacity; i++) {
            System.out.println("Hash Table Index: " + i);
            //inner loop through each pair in each entry
            for(Key key : hashTable[i].keys()) {
                System.out.println("key: "+ key + " value: " + hashTable[i].get(key));
            }//end inner loop
        }//end outer loop
    }
    
    public static void main(String[] args) {
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print keys
        for (String s : st.keys()) 
            StdOut.println(s + " " + st.get(s));
    }
}//end class