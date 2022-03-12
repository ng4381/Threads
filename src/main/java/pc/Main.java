package pc;

import java.util.LinkedList;

class PC {
    LinkedList<Integer> list = new LinkedList<>();
    final int capacity = 5;

    public void produce() {
        int value = 0;
        synchronized (this) {
            while(true) {
                if(list.size() == capacity) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                value++;
                System.out.println("+ " + value);
                list.add(value);
                notifyAll();
            }
        }
    }

    public void consume() {
        synchronized (this) {
            while (true) {
                synchronized (this) {
                    if(list.size() == 0) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("- " + list.remove());
                    notifyAll();
                }
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        PC pc = new PC();
        Thread t1 = new Thread(() -> pc.produce());
        Thread t2 = new Thread(() -> pc.consume());

        t1.start();
        t2.start();
    }
}
