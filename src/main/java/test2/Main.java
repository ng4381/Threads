package test2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Queue<Message> queue = new LinkedList<>();


        DataQueue dataQueue = new DataQueue(queue,5);
        Consumer consumer = new Consumer(dataQueue);
        Producer producer = new Producer(dataQueue);

        Thread thCons = new Thread(consumer);
        thCons.start();

        Thread thProd = new Thread(producer);
        thProd.start();




    }
}
