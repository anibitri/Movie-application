package structures;

import structures.IList;

public class LinkedList<E> implements IList<E> {
    
    ListElement<E> head;
    
    public LinkedList() {
        this.head = null;
    }

    public E get(int index) {
        if (isEmpty() || index >= size()) {
            return null;
        }
        // Gets the element at index in the list
        ListElement<E> ptr = head;
        for (int i = 0; i < index; i++) {
            ptr = ptr.getNext();
        }
        return ptr.getValue();
    }

    public int indexOf(E element) {
        // Gets the index of element in the list
        ListElement<E> ptr = head;
        int i = 0;
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
        ListElement<E> ptr = head;
        ListElement<E> prev = null;

        for (int i = 0; i < index; i++) {
            prev = ptr;
            ptr = ptr.getNext();
        }

        E ret = ptr.getValue();

        ListElement<E> newLink = new ListElement<>(element);
        newLink.setNext(ptr.getNext());
        if (prev != null) {
            prev.setNext(newLink);
        } else {
            head = newLink;
        }

        return ret;
    }

    public boolean add(E element) {
        ListElement<E> temp = new ListElement<>(element);
        
        // if the list is not empty, point the new link to head
        if (head != null) {
            temp.setNext(head);
        }
        // update the head
        head = temp;

        return true;
    }

    public void clear() {
        head = null;
    }

    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public boolean remove(E element) {
        ListElement<E> ptr = head;
        ListElement<E> prev = null;

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

    public int size() {
        if (isEmpty()) return 0;
        ListElement<E> ptr = head;
        int i=1;
        while (ptr.getNext() != null) {
            i++;
            ptr = ptr.getNext();
        }
        return i;
    }
    
    
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        String ret = "";
        ListElement<E> ptr = head;

        while (ptr.getNext() != null) {
            ret += ptr.getValue()+", ";
            ptr = ptr.getNext();
        }

        ret += ptr.getValue();
        ret = "[" + ret + "]";
        return ret;
    }
}