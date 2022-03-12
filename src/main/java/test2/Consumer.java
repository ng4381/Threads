package test2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Consumer implements Runnable {
    private final DataQueue dataQueue;
    private final Object object = new Object();

    @Override
    public void run() {
        Object op = dataQueue.getProdMonitor();
        Object oc = dataQueue.getConsMonitor();

        while (true) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (oc) {

                if (dataQueue.isEmpty()) {
                    try {
                        System.out.println("QUEUE IS EMPTY!!!");
                        oc.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            synchronized (op) {

                System.out.println("Remove... " + dataQueue.remove());
                op.notifyAll();
            }

        }
    }
}