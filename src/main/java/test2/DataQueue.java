package test2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

@Getter @Setter
public class DataQueue {
    private final Queue<Message> queue;
    private final int maxSize;
    private final Object consMonitor = new Object();
    private final Object prodMonitor = new Object();
    private volatile int idx;

    public DataQueue(Queue<Message> queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
        this.idx = 0;
    }

    public void add(Message message) {
        synchronized (queue) {
            queue.add(message);
        }
    }

    public void incIdx() {
        synchronized (queue) {
            idx++;
        }
    }

    public Message remove() {
        synchronized (queue) {
            return queue.poll();
        }
    }

    public boolean isEmpty() {
        synchronized (queue) {
            return queue.isEmpty();
        }
    }

    public boolean isFull() {
        synchronized (queue) {
            return queue.size() == maxSize;
        }
    }
}
