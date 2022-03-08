package test1;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@RequiredArgsConstructor
class MyMessage {
    private final int id;
    private final String msg;

    @Override
    public String toString() {
        return id + ";     " + msg;
    }
}

@RequiredArgsConstructor
@Getter
class MyQueue {
    private final BlockingQueue<MyMessage> queue;


    public void add(MyMessage message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MyMessage remove() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

@RequiredArgsConstructor
class Producer implements Runnable {
    private final MyQueue queue;

    @Override
    public void run() {
        Stream.generate(() -> {
            int id = (int) (Math.random() * 1000);
            return new MyMessage(id, "msg-" + id);
        })
                .limit(1000)
                .forEach(message -> {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    queue.add(message);
                });
    }
}

@Getter
@Setter
class Consumer implements Runnable {
    private final MyQueue queue;
    private final MyJList myJList;
    private final List<MyMessage> consumerList = new ArrayList<>();
    private final int delay;

    public Consumer(MyQueue queue, MyJList myJList, int delay) {
        this.queue = queue;
        this.myJList = myJList;
        this.delay = delay;
    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            consumerList.add(queue.remove());
            myJList.getJList().setListData(consumerList.toArray());
        }
    }
}

@RequiredArgsConstructor
class Display implements Runnable {
    private final MyQueue queue;
    private final MyFrame myFrame;

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<String> myMessageList = queue.getQueue().stream().map(message -> message.toString()).collect(Collectors.toList());
            String[] myMessages = myMessageList.toArray(new String[0]);
            myFrame.setProducerList(myMessages);
        }
    }
}

@Getter
class MyJList {
    private final int idx;
    private final JList jList;

    public MyJList(int idx, JList jList) {
        this.jList = jList;
        this.idx = idx;
        init();
    }

    private void init() {
        jList.setSize(150, 700);
        jList.setLocation(300 + idx * 160, 100);
    }
}

public class Main {
    public static void main(String[] args) {

        MyFrame f = new MyFrame();
        MyQueue myQueue = new MyQueue(new LinkedBlockingQueue());

        Producer producer = new Producer(myQueue);
        Thread tProducer = new Thread(producer);
        tProducer.start();

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        /*
        for (int i = 0; i < 3; i++) {
            executorService.execute(new Consumer(myQueue, f.addConsumerJList(new MyJList(i, new JList())), 1000 * (i+1)));
        }
         */

        executorService.execute(new Consumer(myQueue, f.addConsumerJList(new MyJList(0, new JList())), 1700));
        executorService.execute(new Consumer(myQueue, f.addConsumerJList(new MyJList(1, new JList())), 2000));
        executorService.execute(new Consumer(myQueue, f.addConsumerJList(new MyJList(2, new JList())), 1400));

        executorService.shutdown();

        Display display = new Display(myQueue, f);

        Thread tDisplay = new Thread(display);
        tDisplay.start();
    }
}

class MyFrame extends JFrame implements ActionListener {
    private Container c;
    private MyJList[] listConsumer;
    private JList listProducer;

    private static final int CONSUMER_LIST_MAX = 3;

    public MyFrame() throws HeadlessException {

        listConsumer = new MyJList[CONSUMER_LIST_MAX];

        setTitle("Producer-Consumer");
        setBounds(300, 10, 900, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        JLabel title = new JLabel("Producer-Consumer");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        JLabel titleProducer = new JLabel("Producer");
        titleProducer.setFont(new Font("Arial", Font.BOLD, 15));
        titleProducer.setSize(300, 30);
        titleProducer.setLocation(50, 70);
        c.add(titleProducer);

        listProducer = new JList();
        listProducer.setSize(200, 700);
        listProducer.setLocation(50, 100);
        c.add(listProducer);

        setVisible(true);
    }

    public void setProducerList(String[] arr) {
        listProducer.setListData(arr);
    }

    public void setConsumerList(int idx, String[] arr) {
        listConsumer[idx].getJList().setListData(arr);
    }

    public MyJList addConsumerJList(MyJList myJList) {
        int idx = myJList.getIdx();
        listConsumer[idx] = myJList;

        JLabel titleConsumer = new JLabel("Consumer-" + idx);
        titleConsumer.setFont(new Font("Arial", Font.BOLD, 15));
        titleConsumer.setSize(300, 30);
        titleConsumer.setLocation(myJList.getJList().getX(), 70);
        c.add(titleConsumer);

        c.add(listConsumer[idx].getJList());

        return myJList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}