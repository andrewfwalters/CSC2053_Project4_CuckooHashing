/*************************************************************************
 *  Compilation:  javac BinarySearchST.java
 *  Execution:    java BinarySearchST
 *  Dependencies: StdIn.java StdOut.java Item.java
 *  Data files:   http://algs4.cs.princeton.edu/31elementary/tinyST.txt  
 *  
 *  Symbol table implementation with binary search in an ordered array.
 *
 *
 *************************************************************************/

import java.util.NoSuchElementException;

public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private static final int INIT_CAPACITY = 2;
    private Object[] objects;
    private int N = 0;

    // create an empty symbol table with default initial capacity
    public BinarySearchST() {
        this(INIT_CAPACITY);
    }//end constructor   

    // create an empty symbol table with given initial capacity
    public BinarySearchST(int capacity) {
        objects = new Object[capacity];
    }//end constructor

    // resize the underlying arrays
    private void resize(int capacity) {
        assert capacity >= N;
        Object[] tempo = new Object[capacity];
        
        for (int i = 0; i < N; i++) {
            tempo[i] = objects[i];
        }//end loop
        
        objects = tempo;
    }

    // is the key in the table?
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // number of key-value pairs in the table
    public int size() {
        return N;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the value associated with the given key, or null if no such key
    public Value get(Key key) {
        if (isEmpty()) {
            return null;
        }
        int i = rank(key);
        if (i >= N) {
            return null;
        }
        Item<Key,Value> temp = (Item)objects[i];
        if (i < N && (temp.getKey()).compareTo(key) == 0) {
            return temp.getValue();
        }
        return null;
    } 

    // return the number of keys in the table that are smaller than given key
    public int rank(Key key) {
        int lo = 0, hi = N-1;
        Item<Key,Value> temp;
        while (lo <= hi) { 
            int m = lo + (hi - lo) / 2; 
            temp = (Item)objects[m];
            int cmp = key.compareTo(temp.getKey()); 
            if      (cmp < 0) hi = m - 1; 
            else if (cmp > 0) lo = m + 1; 
            else return m; 
        } 
        return lo;
    } 


    // Search for key. Update value if found; grow table if new. 
    public void put(Key key, Value val)  {
        if (val == null) { delete(key); return; }

        int i = rank(key);
        // key is already in table
        if (i < N) {
            Item<Key,Value> temp = (Item)objects[i];
            if ((temp.getKey()).compareTo(key) == 0) {
                temp.setValue(val);
                return;
            }
        }

        // insert new key-value pair
        if (N == objects.length) {
            resize(2*objects.length);
        }

        for (int j = N; j > i; j--)  {
            objects[j] = objects[j-1];
        }//end loop
        
        objects[i] = new Item<Key,Value>(key,val);
        N++;

        assert check();
    }//end put 


    // Remove the key-value pair if present
    public void delete(Key key)  {
        if (isEmpty()) return;

        // compute rank
        int i = rank(key);
        Item<Key,Value> temp = (Item)objects[i];

        // key not in table
        if (i == N || (temp.getKey()).compareTo(key) != 0) {
            return;
        }

        for (int j = i; j < N-1; j++)  {
            objects[j] = objects[j+1];
        }//end loop

        N--;
        objects[N] = null;  // to avoid loitering

        // resize if 1/4 full
        if (N > 0 && N == objects.length/4) {
            resize(objects.length/2);
        }

        assert check();
    } //end delete

    // delete the minimum key and its associated value
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
        delete(min());
    }

    // delete the maximum key and its associated value
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
        delete(max());
    }


   /*****************************************************************************
    *  Ordered symbol table methods
    *****************************************************************************/
    public Key min() {
        if (isEmpty()) return null;
        Item<Key,Value> temp = (Item)objects[0];
        return temp.getKey(); 
    }

    public Key max() {
        if (isEmpty()) return null;
        Item<Key,Value> temp = (Item)objects[N-1];
        return temp.getKey();
    }

    public Key select(int k) {
        if (k < 0 || k >= N) {
            return null;
        }
        
        Item<Key,Value> temp = (Item)objects[k];
        return temp.getKey();
    }

    public Key floor(Key key) {
        int i = rank(key);
        Item<Key,Value> temp = (Item)objects[i];
        
        if (i < N && key.compareTo(temp.getKey()) == 0) {
            return temp.getKey();
        }
        if (i == 0) {
            return null;
        }
        else {
            Item<Key,Value> tempDec = (Item)objects[i-1];
            return tempDec.getKey();
        }
    }//end floor

    public Key ceiling(Key key) {
        int i = rank(key);
        Item<Key,Value> temp = (Item)objects[i];
        
        if (i == N) {
            return null;
        }
        else {
            return temp.getKey();
        }
    }//end ceiling

    public int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        if (contains(hi)) {
            return rank(hi) - rank(lo) + 1;
        }
        else {
            return rank(hi) - rank(lo);
        }
    }//end size

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        
        if (lo == null && hi == null) {
            return queue;
        }
        if (lo == null) {
            throw new NullPointerException("lo is null in keys()");
        }
        if (hi == null) {
            throw new NullPointerException("hi is null in keys()");
        }
        if (lo.compareTo(hi) > 0) {
            return queue;
        }
        
        Item<Key,Value> temp;
        for (int i = rank(lo); i < rank(hi); i++) {
            temp = (Item)objects[i];
            queue.enqueue(temp.getKey());
        }
        
        if (contains(hi)) {
            temp = (Item)objects[rank(hi)];
            queue.enqueue(temp.getKey());
        }
        
        return queue; 
    }


   /*****************************************************************************
    *  Check internal invariants
    *****************************************************************************/

    private boolean check() {
        return isSorted() && rankCheck();
    }

    // are the items in the array in ascending order?
    private boolean isSorted() {
        int i = 0;
        Item<Key,Value> tempDec;
        Item<Key,Value> temp = (Item)objects[i];
        
        for(i = 1; i < size(); i++) {
            tempDec = temp;
            temp = (Item)objects[i];
            if ((temp.getKey()).compareTo(tempDec.getKey()) < 0) {
                return false;
            }
        }//end loop
        return true;
    }//end isSorted

    // check that rank(select(i)) = i
    private boolean rankCheck() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }//end loop
        
        Item<Key,Value> temp;
        for (int i = 0; i < size(); i++) {
            temp = (Item)objects[i];
            if ((temp.getKey()).compareTo(select(rank(temp.getKey()))) != 0) {
                return false;
            }
        }//end loop
        return true;
    }//end rankCheck


   /*****************************************************************************
    *  Test client
    *****************************************************************************/
    public static void main(String[] args) { 
        BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            if(key.compareTo("exit")==0) {
                break;
            }
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}