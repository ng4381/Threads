package pc;

import java.util.LinkedList;

class PC {
    LinkedList<Integer> list = new LinkedList<>();
    final int capacity = 5;

    public void produce() throws InterruptedException {
        int value = 0;
        synchronized (this) {
            while(true) {
                while (list.size() == capacity)
                        wait();

                Thread.sleep(300);

                value++;
                System.out.println("+ " + value);
                list.add(value);
                notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (this) {
            while (true) {
                synchronized (this) {
                    while(list.size() == 0)
                            wait();

                    Thread.sleep(300);
                    System.out.println("- " + list.remove());
                    notify();
                }
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        PC pc = new PC();
        Thread t1 = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }
}
