package deadlock;

class Job implements Runnable {

    Object mutex1;
    Object mutex2;

    public Job(Object mutex1, Object mutex2) {
        this.mutex1 = mutex1;
        this.mutex2 = mutex2;
    }

    @Override
    public void run() {
        System.out.println("Started: " + Thread.currentThread().getName());

        synchronized (mutex1){
            System.out.println("Th: " + Thread.currentThread().getName() + " in M1");
            synchronized (mutex2){
                System.out.println("Th: " + Thread.currentThread().getName() + " in M2");
            }
            System.out.println("Th: " + Thread.currentThread().getName() + " out M2");
        }
        System.out.println("Th: " + Thread.currentThread().getName() + " out M1");

    }
}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String mutex1 = "Mutex 1";
        String mutex2 = "Mutex 2";

        Thread t1 = new Thread(new Job(mutex1, mutex2));
        Thread t2 = new Thread(new Job(mutex2, mutex1));

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
