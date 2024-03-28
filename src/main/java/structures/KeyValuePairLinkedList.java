package structures;
import structures.KeyValuePair;
public class KeyValuePairLinkedList<K extends Comparable<K>,V> {

    protected ListElement<KeyValuePair<K,V>> head;
    protected int size;
    
    public KeyValuePairLinkedList() {
        head = null;
        size = 0;
    }
    
    public void add(K key, V value) {
        this.add(new KeyValuePair<K,V>(key,value));
    }

    public void add(KeyValuePair<K,V> kvp) {
        ListElement<KeyValuePair<K,V>> new_element = 
                new ListElement<>(kvp);
        new_element.setNext(head);
        head = new_element;
        size++;
    }
    
    public int size() {
        return size;
    }
    
    public ListElement<KeyValuePair<K,V>> getHead() {
        return head;
    }
    
    public KeyValuePair<K,V> get(K key) {
        ListElement<KeyValuePair<K,V>> temp = head;
        
        while(temp != null) {
            if(temp.getValue().getKey().equals(key)) {
                return temp.getValue();
            }
            
            temp = temp.getNext();
        }
        
        return null;
    }

    public boolean remove(K key) {
        ListElement<KeyValuePair<K, V>> current = head;
        ListElement<KeyValuePair<K, V>> previous = null;

        while (current != null) {
            if (current.getValue().getKey().equals(key)) {
                if (previous == null) {
                    head = current.getNext(); //remove head of lsit
                } else {
                    previous.setNext(current.getNext());
                }
                size--;
                return true;
            }

            previous = current;
            current = current.getNext();
        }
        return false;
    }


    public boolean containsKey(K key) {
        ListElement<KeyValuePair<K,V>> temp = head;
            
        while(temp != null) {
            if(temp.getValue().getKey().equals(key)) {
                return true;
            }
            
            temp = temp.getNext();
        }
        
        return false;
    }
    

    
    
}
