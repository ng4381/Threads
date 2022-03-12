package test2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Producer implements Runnable {
    private final DataQueue dataQueue;
    private final Object object = new Object();

    @Override
    public void run() {

        Object op = dataQueue.getProdMonitor();
        Object oc = dataQueue.getConsMonitor();

        while (true) {

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (op) {
                if (dataQueue.isFull()) {
                    try {
                        System.out.println("QUEUE IS FULL!!!");
                        op.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


            synchronized (oc) {

                dataQueue.incIdx();
                Message message = new Message(dataQueue.getIdx(), "msg");
                System.out.println("Add... " + message);
                dataQueue.add(message);

                oc.notifyAll();

            }
        }
    }
}
