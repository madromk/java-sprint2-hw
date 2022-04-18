package manager;

import tasks.BaseTask;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private LinkedListHistory<BaseTask> linkedHistory = new LinkedListHistory<>();

    @Override
    public void addInHistory(BaseTask task) {
        if (linkedHistory.hashMapNode.containsKey(task.getId())) {
            linkedHistory.removeNode(linkedHistory.hashMapNode.get(task.getId()));
        }
        linkedHistory.linkLast(task);
    }

    @Override
    public void remove(int id) {
        if(!linkedHistory.hashMapNode.isEmpty()) {
            linkedHistory.removeNode(linkedHistory.hashMapNode.get(id));
        }
    }

    @Override
    public List<BaseTask> getHistory() {
        return linkedHistory.getTasks();
    }

    static class Node<E> {
        public InMemoryHistoryManager.Node<E> prev;
        public E data;
        public InMemoryHistoryManager.Node<E> next;


        public Node(InMemoryHistoryManager.Node<E> prev, E data, InMemoryHistoryManager.Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    static class LinkedListHistory<T extends BaseTask> {
        Map<Integer, Node<T>> hashMapNode = new HashMap<>(); // хештаблица для хранения узлов и id
        private Node<T> head;
        private Node<T> tail;
        private int size = 0;

        public void linkLast(T task) {
            final Node<T> oldTail = tail;
            final Node<T> newNode = new Node<T>(oldTail, task, null);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
            }
            hashMapNode.put(task.getId(), newNode); //  добавляем в хештаблицу задачу и узел
            size++;
        }

        public List<BaseTask> getTasks() {
            List<BaseTask> list = new ArrayList<>();
            Node<T> node = head;
            while (node != null) {
                list.add(node.data);
                node = node.next;
            }
            return list;
        }

        public T removeNode(Node<T> node) {

            final T element = node.data;
            final Node<T> next = node.next;
            final Node<T> prev = node.prev;

            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
                node.prev = null;
            }

            if (next == null) {
                tail = prev;
            } else {
                next.prev = prev;
                node.next = null;
            }

            node.data = null;
            size--;

            return element;
        }

        public int size() {
            return this.size;
        }
    }
}