/*************************************************************************
 *  Compilation:  javac Item.java
 *  Execution:    java Item
 *  Dependencies: none
 *  Data files:   http://algs4.cs.princeton.edu/31elementary/tinyST.txt  
 *  
 *  Item class stores a key and value, each a parameterized type. The value
 *  can be modified after being initiated but not the key.
 *
 *************************************************************************/

public class Item<Key, Value> {
    
    private Key key;
    private Value value;
    
    public Item(Key k, Value v) {
         key = k;
         value = v;
    }//end constructor
    
    public Key getKey() {
        return key;
    }//end getKey
    
    public Value getValue() {
        return value;
    }//end getValue
    
    public void setValue(Value v) {
        value = v;
    }//end setValue
    
    public static void main(String[] args) {
        Item<String, Integer> test = new Item<String, Integer> ("Andrew",0);
        System.out.println(test.getKey() + ": " + test.getValue());
    }//end main
    
}//end class