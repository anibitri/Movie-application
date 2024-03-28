package structures;


import stores.MetaData;
import stores.RatingsMetaData;

@SuppressWarnings("unchecked") 
public class HashMap<K extends Comparable<K>,V> implements IMap<K,V> {

    public KeyValuePairLinkedList[] table;
    
    public HashMap() {
        /* for very simple hashing, primes reduce collisions */
        this(9000);
    }
    
    public HashMap(int size) {
        table = new KeyValuePairLinkedList[size];
        initTable();
    }

    public int find(K key) {
         //returns the number of comparisons required to find element using Linear Search.
         int hash_code = hash(key);
         int location = hash_code % table.length;
 
         ListElement<KeyValuePair<K,V>> temp = table[location].getHead();
         int comparisons = 0;
         while(temp != null) {
             comparisons++;
             if(temp.getValue().getKey().equals(key)) {
                 return comparisons;
             }
             
             temp = temp.getNext();
         }
         
         return comparisons;
    }
    
    protected void initTable() {
        for(int i = 0; i < table.length; i++) {
            table[i] = new KeyValuePairLinkedList<>();
        }
    }
    
    protected int hash(K key) {
        int code = key.hashCode();
        return Math.abs(code);    
    }
    

    public void add(K key, V value) {
        int hash_code = hash(key);
        int location = hash_code % table.length;
        
        System.out.println("Adding " + value + " under key " + key + " at location " + location);
        
        table[location].add(key,value);
    }

    public V get(K key) {
        int hash_code = hash(key);
        int location = hash_code % table.length;
        
        KeyValuePair<K,V> ptr = table[location].get(key);
        
        if (ptr == null) {
            return null;
        }

        return (V) ptr.getValue();
    }

    public boolean remove(K key) {
        int hash_code = hash(key);
        int location = hash_code % table.length;
        return table[location].remove(key);
    }

    public boolean containsKey(K key) {
        int hash_code = hash(key);
        int location = hash_code % table.length;
        
        ListElement<KeyValuePair<K,V>> temp = table[location].getHead();
        while(temp != null) {
            if(temp.getValue().getKey().equals(key)) {
                return true;
            }
            temp = temp.getNext();
        }
        return false;
    }

    public boolean isEmpty() {
        if (table.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        int size = 0;
        for (KeyValuePairLinkedList<K, V> linkedList : table) {
            size += linkedList.size();
        }
        return size;
    }

    public MetaData[] MoviesToArray() {
        //Calculate the total number of elements in the map
        int totalElements = 0;
        for (KeyValuePairLinkedList<K, V> linkedList : table) {
            totalElements += linkedList.size();
        }

        //Create an array to hold the MetaData objects
        MetaData[] dataArray = new MetaData[totalElements];

        //Traverse each bucket in the hash table
        int index = 0;
        for (KeyValuePairLinkedList<K, V> linkedList : table) {

            //Iterate through the linked list in the bucket
            ListElement<KeyValuePair<K, V>> current = linkedList.getHead();
            while (current != null) {
                
                //Add MetaData object to the array
                dataArray[index] = (MetaData) current.getValue().getValue();
                current = current.getNext();
                index++;
            }
        }

        return dataArray;
    }

    public RatingsMetaData[] RatingsToArray() {
        
        // Calculate the total number of elements in the map
        int totalElements = 0;
        for (KeyValuePairLinkedList<K, V> linkedList : table) {
            totalElements += linkedList.size();
        }

        //Create an array to hold the RatingsMetaData objects
        RatingsMetaData[] dataArray = new RatingsMetaData[totalElements];

        //Traverse each bucket in the hash table
        int index = 0;
        for (KeyValuePairLinkedList<K, V> linkedList : table) {

            //Iterate through the linked list in the bucket
            ListElement<KeyValuePair<K, V>> current = linkedList.getHead();
            while (current != null) {

                //Add RatingsMetaData object to the array
                dataArray[index] = (RatingsMetaData) current.getValue().getValue();
                current = current.getNext();
                index++;
            }
        }

        return dataArray;
    }



}
