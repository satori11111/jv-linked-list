package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (size == 0) {
            head = node;
        } else {
            Node<T> currentNode = tail;
            currentNode.next = node;
            node.prev = currentNode;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            Node<T> node = new Node<>(value);
            Node<T> currentNode = getNodeByIndex(index);
            node.next = currentNode;
            node.prev = currentNode.prev;
            currentNode.prev.next = node;
            currentNode.prev = node;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        if (currentNode == null) {
            return null;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            Node<T> oldHead = head;
            head = head.next;
            size--;
            return oldHead.value;
        }
        Node<T> currentNode = getNodeByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if ((currentNode.value == null && object == null)
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                if (i == 0) {
                    head = head.next;
                    size--;
                    return true;
                }
                unlink(currentNode);
                return true;
            } else {
                currentNode = currentNode.next;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void addFirst(T value) {
        if (size == 0) {
            add(value);
        } else {
            Node<T> node = new Node<>(value);
            node.next = head;
            head.prev = node;
            head = node;
            size++;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index " + index
                    + " was out of bounds LinkedList size: " + size);
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        int sizeOfHalfList = size / 2;
        if (index <= sizeOfHalfList) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            int lastIndex = size - 1;
            for (int i = lastIndex; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
