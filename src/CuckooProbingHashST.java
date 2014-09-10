/*******************************************************************************
 * 
 * @author Andrew Walters
 * @param <Key>
 * @param <Value> 
*******************************************************************************/

public class CuckooProbingHashST<Key, Value> {
    
    //class variables
    private int capacity = 0;
    private int size = 0;
    private Object[] hashTable;
    
    //default constructor - use 16 as default size
    public CuckooProbingHashST() {
        this(16);
    }//end default constructor 
    
    //constructor
    public CuckooProbingHashST(int capacity) {
        this.capacity = capacity;
        hashTable = new Object[capacity];
    }//end constructor
    
    //is the symbol table empty?
    public boolean isEmpty() {
        if(size() == 0) {return true;}
        else {return false;}
    }//end isEmpty
    
    // number of key-value pairs in the symbol table
    public int size() {
        return size;
    }//end size
    
    //does a key-value pair with the given key exist in the symbol table?
    public boolean contains(Key key) {
        return get(key) != null;
    }//end contains
    
    //insert the key-value pair into the symbol table
    public void put(Key key, Value val) {
        put(key, val, 0);
    }
    //private helper method for put
    private void put(Key key, Value val, int depth) {
        int myHash;
        int myHash1 = hash1(key);
        int myHash2 = hash2(key);
        
        if(depth < 10) {
                depth++;
        }
        else {
            resize(capacity*2);
            put(key,val,0);
            return;
        }
        
        //determine if the key is already in the table
        if( hashTable[myHash1] != null && (((Item<Key,Value>)hashTable[myHash1]).getKey()).equals(key)) {
            ((Item<Key,Value>)hashTable[myHash1]).setValue(val);
        }
        else if( hashTable[myHash2] != null && (((Item<Key,Value>)hashTable[myHash2]).getKey()).equals(key)) {
            ((Item<Key,Value>)hashTable[myHash2]).setValue(val);
        }
        //if the key is not in the table, determine where to add it
        //CASE: both hashes are empty
        else if(hashTable[myHash1] == null && hashTable[myHash2] == null) {
            //decide which to use randomly
            if(StdRandom.bernoulli()) {
                hashTable[myHash1] = new Item<Key,Value>(key,val);
                size++;
            }
            else {
                hashTable[myHash2] = new Item<Key,Value>(key,val);
                size++;
            }
        }//END CASE: both hashes are empty
        //CASE: one hash is empty
        else if(hashTable[myHash1] == null) {
            hashTable[myHash1] = new Item<Key,Value>(key,val);
            size++;
        }
        //second hash is empty
        else if(hashTable[myHash2] == null) {
            hashTable[myHash2] = new Item<Key,Value>(key,val);
            size++;
        }//END CASE: one hash is empty
        //both hashes are already occupied
        else {
            
            //pick which item to displace
            if(StdRandom.bernoulli()) {
                Item<Key,Value> temp = new Item<Key,Value>( ((Item<Key,Value>)hashTable[myHash1]).getKey(), ((Item<Key,Value>)hashTable[myHash1]).getValue());
                hashTable[myHash1] = new Item<Key,Value>(key,val);
                put(temp.getKey(),temp.getValue(),depth);
                size++;
            }
            else {
                Item<Key,Value> temp = new Item<Key,Value>( ((Item<Key,Value>)hashTable[myHash2]).getKey(), ((Item<Key,Value>)hashTable[myHash2]).getValue());
                hashTable[myHash2] = new Item<Key,Value>(key,val);
                put(temp.getKey(),temp.getValue(),depth);
                size++;
            }
        }//END CASE: both hases are already occupied
        depth = 0;
    }//end put
    
    //return the value associated with the given key, null if no such value
    public Value get(Key key) {
        int myHash1 = hash1(key);
        int myHash2 = hash2(key);
        
        if( hashTable[myHash1] != null && (((Item<Key,Value>)hashTable[myHash1]).getKey()).equals(key)) {
                return ((Item<Key,Value>)hashTable[myHash1]).getValue();
        }
        else if( hashTable[myHash2] != null && (((Item<Key,Value>)hashTable[myHash2]).getKey()).equals(key)) {
            return ((Item<Key,Value>)hashTable[myHash2]).getValue();
        }
        else { 
            return null;
        }
    }//end get
    
    //delete the key (and associated value) from the symbol table
    public void delete(Key key) {
        int myHash1 = hash1(key);
        int myHash2 = hash2(key);
        if(((Item<Key,Value>)hashTable[myHash1]).getKey() == key) {
            hashTable[myHash1] = null;
        }
        else if(((Item<Key,Value>)hashTable[myHash2]).getKey() == key) {
            hashTable[myHash2] = null;
        }
    }//end delete
    
    //return all of the keys as an Iterable
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        //loop through array
        for(int i = 0; i < capacity; i++) {
            //add any non-null entry to the queue
            if(hashTable[i] != null) {
                queue.enqueue( ( (Item<Key,Value>) hashTable[i]).getKey() );
            }
        }
        return queue;
    }//end keys
    
    //first hashing method
    private int hash1(Key key) {
        return ((key.hashCode() * 7) & 0x7fffffff) % capacity;
    }//end hash 1
    
    //second hashing method
    private int hash2(Key key) {
        int hash = ((key.hashCode() * 3) & 0x7fffffff) % capacity;
        if(hash == hash1(key))
            hash = (hash+1)%capacity;
        return hash;
    }//end hash2
    
    private void resize(int newCapacity) {
        //create a new table
        CuckooProbingHashST<Key, Value> temp = new CuckooProbingHashST<Key, Value>(newCapacity);
        
        //add every Item to the new table
        for(Key key : keys()) {
            temp.put(key,get(key));
        }//end inner loop
        
        this.capacity = temp.capacity;
        this.size = temp.size;
        this.hashTable = temp.hashTable;
    }//end resize
    
    public void dump() {
        System.out.println("Hash Table with capacity " + capacity);
        for(int i = 0; i < capacity; i++) {
            if(hashTable[i] != null)
                System.out.println(i + ": " + ((Item<Key,Value>) hashTable[i]).getKey() + "\t" + ((Item<Key,Value>) hashTable[i]).getValue());
        }
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