package main.data.impl.list;

import main.data.adt.ListADT;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<E> implements ListADT<E> {

    protected DoubleNode<E> head;
    protected DoubleNode<E> tail;
    protected int count;
    protected int modCount;

    public DoubleLinkedList() {
        head = tail = null;
        count = 0;
        modCount = 0;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }
        E result = head.getElement();
        head = head.getNext();
        if (head != null) {
            head.setPrevious(null);
        } else {
            tail = null;
        }
        count--;
        modCount++;
        return result;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }
        E result = tail.getElement();
        tail = tail.getPrevious();
        if (tail != null) {
            tail.setNext(null);
        } else {
            head = null;
        }
        count--;
        modCount++;
        return result;
    }

    @Override
    public E remove(E element) {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }
        DoubleNode<E> current = head;
        while (current != null) {
            E el = current.getElement();
            if ((element == null && el == null) || (element != null && element.equals(el))) {
                if (current == head) {
                    return removeFirst();
                } else if (current == tail) {
                    return removeLast();
                } else {
                    DoubleNode<E> previous = current.getPrevious();
                    DoubleNode<E> next = current.getNext();
                    previous.setNext(next);
                    next.setPrevious(previous);
                    count--;
                    modCount++;
                    return el;
                }
            } else {
                current = current.getNext();
            }
        }
        return null;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }
        return head.getElement();
    }

    @Override
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }
        return tail.getElement();
    }

    @Override
    public boolean contains(E target) {
        DoubleNode<E> current = head;
        while (current != null) {
            E el = current.getElement();
            if ((target == null && el == null) || (target != null && target.equals(el))) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        DoubleNode<E> current = head;
        while (current != null) {
            sb.append(current.getElement());
            current = current.getNext();
            if (current != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new DoubleLinkedListIterator<>(modCount, () -> modCount, head);
    }


}
