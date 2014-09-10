/**********************************************************************
 *  Cuckoo Hashing
 **********************************************************************/

Name: Andrew Walters
Operating system: Win7
Compiler:
Text editor / IDE: Netbeans
Hours to complete assignment (optional):

/**********************************************************************
 *  Describe your hashing functions 
 *  
 **********************************************************************/
In Separate Chaining, I used a simple modulus and an offset modulus, ensuring that the hash functions would never be equal. Due to concerns of cycles in the probing implementation, the hashcode of each key was multiplied by a prime number prior to the modulus operation to ensure that cycles were less frequent. This required hash2 to implement a check and offset to prevent duplicate hash codes.



/**********************************************************************
 *  Describe how you implemented CuckooSeparateChainingHashST 
 *  
 **********************************************************************/
Separate Chaining was implemented using an array of linked lists (SequentialSearchST). In the put function, the list with the smaller size was used, or a random choice was made if they were equal. Once the average list size surpassed 10, the table size was doubled.

    
    
/**********************************************************************
 *  Describe how you implemented CuckooProbingHashST
 *  
 **********************************************************************/
Probing was implemented with an array of generic objects cast as a key-value object called Item. The primary hurdle with probing was cycle prevention. I depth counter was used in recursive calls of put that limited the depth to 10 calls. Once 10 items had been replaced the table was doubled and rehashed.


/**********************************************************************
 *  What were the main challenges in adapting the hashing algorithms 
 *  that we studied to the cuckoo hashing approach?
 **********************************************************************/




/**********************************************************************
 *  Analysis of running time 
 *  
 *  Compare your two cuckoo hashing implementations with other 
 *  implementations of symbol tables:
 *   1) CuckooSeparateChainingHashST
 *   2) CuckooProbingHashST
 *   3) ST (your own implementation from Project 2)
 *   4) RedBlackBST
 *   5) SeparateChainingHashST
 *   6) LinearProbingHashST
 **********************************************************************/


 N                time for each implementation (seconds)
(file size)		(1)		(2)		(3)		(4)		(5)
------------------------------------------------------------------------
CuckooSeparateChainingHashST
256K			1.309	1.237	1.265	1.239	1.243
512K			2.423	2.43	2.356	2.386	2.288
CuckooProbingHashST
256K			0.884	0.982	0.865	0.859	0.873
512K			1.539	1.378	1.38	1.428	1.39
BinarySearchST
256K			1.164	1.084	1.077	1.104	1.055
512K			1.728	1.703	1.734	1.72	1.721
RedBlackBST
256K			1.037	0.969	0.991	0.968	0.971
512K			1.55	1.619	1.585	1.541	1.568
SeparateChainingHashST
256K			0.899	0.876	0.871	0.849	0.83
512K			1.411	1.331	1.371	1.387	1.343
LinearProbingHashST
256K			0.86	0.792	0.809	0.809	0.806
512K			1.341	1.278	1.304	1.278	1.25


Discussion:
The hash tables clearly outperformed the tree data structures with the lone exception of my implementation of CuckooSeparateChainingHashST. This could be the result of some inefficient functions, such as size() which sums the size of each linked list instead of tracking the size in a variable. All of the data structures scale at approximately the same rate. The Cuckoo implementations were both slower than their non-Cuckoo equivalents, which could be a result of the implementation rather than the efficiency of the data structure.




/**********************************************************************
 *  Known bugs / limitations.
 **********************************************************************/
none



/**********************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include required readings and lectures, but do
 *  include any help from people (including instructor, TAs,
 *  helpdesk, classmates, and friends) and attribute them by name.
 **********************************************************************/
none
    

/**********************************************************************
 *  Describe any serious problems you encountered.                    
 **********************************************************************/
implementing resize in probing



/**********************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **********************************************************************/




