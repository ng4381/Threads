package test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

class R1 implements Runnable {

    Semaphore semaphore;
    boolean release;

    public R1(Semaphore semaphore, boolean release) {
        this.semaphore = semaphore;
        this.release = release;
    }

    @Override
    public void run() {
        try {
            //System.out.println("1: " + Thread.currentThread().getName());
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2: " + Thread.currentThread().getName());

        if (release) {
            semaphore.release();
        }
    }
}

class CDL implements Runnable {
    CountDownLatch countDownLatch;

    public CDL(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("CountDownLatch 1: " + Thread.currentThread().getName());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CountDownLatch 2: " + Thread.currentThread().getName());
    }
}


class CB implements Runnable {
    private final CyclicBarrier cyclicBarrier;
    private final String name;

    public CB(CyclicBarrier cyclicBarrier, String name) {
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("CB1: " + name);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("CB2: " + name);
    }
}




public class Main {
    public static void main(String[] args) throws InterruptedException {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        Runnable cb1 = new CB(cyclicBarrier, "th - 1");
        Runnable cb2 = new CB(cyclicBarrier, "th - 2");
        Runnable cb3 = new CB(cyclicBarrier, "th - 3");

        Thread t1 = new Thread(cb1);
        Thread t2 = new Thread(cb2);
        Thread t3 = new Thread(cb3);




        t1.start();
        t2.start();

        t1.join();
        t2.join();

        t3.start();


        /*
        CountDownLatch countDownLatch = new CountDownLatch(3);

        Runnable cdl = new CDL(countDownLatch);
        countDownLatch.countDown();
        countDownLatch.countDown();
        //countDownLatch.countDown();

        Thread th1 = new Thread(cdl);
        th1.start();

         */


        /*
        Runnable R1 = new R1(semaphore, false);
        Runnable R2 = new R1(semaphore, false);
        Runnable R3 = new R1(semaphore, false);
        Runnable R4 = new R1(semaphore, true);

        Thread th1 = new Thread(R1);
        Thread th2 = new Thread(R2);
        Thread th3 = new Thread(R3);
        Thread th4 = new Thread(R4);

        th1.start();
        th2.start();
        th3.start();
        th4.start();
         */
    }
}
