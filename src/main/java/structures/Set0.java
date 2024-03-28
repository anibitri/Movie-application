package structures;
import java.util.Iterator;

import structures.ISet;

/**
* A generic implementation of the IList iterface.
*/
public class Set0<E> implements ISet<E>, Iterable<E> {
    
    private MyArrayList1<E> array = new MyArrayList1<>();

    // COMPLETE.
    public boolean add(E element) {
        // Adds element to the list when it does not already exist.
        // Returns true on success and false otherwise.

        //check if the set contains the element by using the methods from MyArrayList
        if (!contains(element)) {
            //if it doesnt add the element to the arraylist and return true
            array.add(element);
            return true;
        }
        //else return false
        return false;
    }
    
    // COMPLETE.
    public String toString() {
        // Returns a string representation of this Set object.

        //check if the arraylist is empty by using the methods from the arraylist class
            if (array.isEmpty()) {
                return "[]";
            }

        //if its not use the toString() method from the arraylist class
        return array.toString();
    }
    
    public void clear() {
        array.clear();
    }

    public boolean contains(E element) {
        return array.contains(element);
    }

    public boolean isEmpty() {
        return array.isEmpty();
    }

    public boolean remove(E element) {
        return array.remove(element);
    }

    public int size() {
        return array.size();
    }
    
    /// Iterator implementation
    @Override
    public Iterator<E> iterator() {
        return array.iterator();
    }


}
