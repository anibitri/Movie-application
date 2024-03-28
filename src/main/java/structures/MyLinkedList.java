package structures;

/**
* A generic implementation of the IList iterface, that uses LinkElements.
*/

import structures.IList;

public class MyLinkedList<E> implements IList<E> {
    
    MyLinkedListElement<E> head;
    private int count;
    
    public MyLinkedList() {
        this.head = null;
    }
    
    // INCOMPLETE.
    public boolean isEmpty() {
        // Returns whether the list is empty.
        if (head == null) {
            return true;
        }
        return false;
    }
    
    // INCOMPLETE.
    public boolean add(E element) {
        // Adds an element to the head of the list.
        MyLinkedListElement<E> temp = new MyLinkedListElement<>(element);
        
        //Check if the list is empty, if not point the new link to head
        if (!isEmpty()) {
            temp.setNext(head);
        }
        //Update head and increase count
        head = temp;
        count++;
        return true;
    }
    
    // INCOMPLETE.
    public int size() {
        // Returns the number of elements in stored in this list.
        return count;
    }
    
    // INCOMPLETE.
    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }
        StringBuilder ret = new StringBuilder("[");
        for (int i=0;i<count;i++) {
            ret.append(this.get(i)).append(", ");
        }
        ret.deleteCharAt(ret.length()-1);
        ret.setCharAt(ret.length()-1, ']');
        return ret.toString();
    }
    
    
    // INCOMPLETE.
    public boolean addToTail(E element) {
        // Adds element to tail of the list
        MyLinkedListElement<E> temp = new MyLinkedListElement<>(element);
        
        //Check if empty, then update head
        if (isEmpty()) {
            head = temp;
        }

        //Point the new element to null
        temp.setNext(null);

        MyLinkedListElement<E> ptr = head;
        while (ptr.getNext() != null) {
            ptr = ptr.getNext();
        }

        //ptr = tail when iteration is finished
        //Point tail element to new element
        ptr.setNext(temp);

        count++;
        return true;
    }
    
    // INCOMPLETE.
    public E removeFromHead() {
        // Removes and returns the head element
        if (isEmpty()) {
            return null;
        }
        MyLinkedListElement<E> temp = head;
        head = head.getNext();
        count--;
        return temp.getValue();
    }
    
    public E removeFromTail() {
        // Removes and returns the tail element
        MyLinkedListElement<E> ptr = head;
        while (ptr.getNext().getNext() != null) {
            ptr = ptr.getNext();
        }
        // ptr = second to last element
        // store tail element to be returned later
        MyLinkedListElement<E> last = ptr.getNext();
        // point second to last element to null
        ptr.setNext(null);

        count--;
        return last.getValue();
    }
    
    
    public E get(int index) {
        if (isEmpty() || index >= size()) {
            return null;
        }
        // Gets the element at index in the list
        MyLinkedListElement<E> ptr = head;
        for (int i = 0; i < index; i++) {
            ptr = ptr.getNext();
        }
        return ptr.getValue();
    }

    public int indexOf(E element) {
        // Gets the index of element in the list
        MyLinkedListElement<E> ptr = head;
        int i=0;
        while (ptr != null) {
            if (element.equals(ptr.getValue())) {
                return i;
            }
            i++;
            ptr = ptr.getNext();
        }
        return -1;
    }

    public E set(int index, E element) {
        if (isEmpty()) {
            return null;
        }

        // Sets element at index in the list
        MyLinkedListElement<E> ptr = head;
        MyLinkedListElement<E> prev = null;

        for (int i = 0; i < index; i++) {
            prev = ptr;
            ptr = ptr.getNext();
        }

        E ret = ptr.getValue();

        MyLinkedListElement<E> newLink = new MyLinkedListElement<>(element);
        newLink.setNext(ptr.getNext());
        if (prev != null) {
            prev.setNext(newLink);
        } else {
            head = newLink;
        }

        return ret;
    }
    
    public void clear() {
        // Clears the list
        head = null;
        count = 0;
    }
    
    public boolean contains(E element) {
        // Returns whether the element exists in the list
        return indexOf(element) != -1;
    }

    public boolean remove(E element) {
        MyLinkedListElement<E> ptr = head;
        MyLinkedListElement<E> prev = null;

        count--;
        
        while (ptr != null) {
            if (ptr.getValue().equals(element)) {
                if (prev == null) {
                    head = ptr.getNext();
                } else {
                    prev.setNext(ptr.getNext());
                }

                return true;
            }

            prev = ptr;
            ptr = ptr.getNext();
        }

        
        return false;
    }
}
