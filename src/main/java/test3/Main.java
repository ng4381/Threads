package test3;



class DoSomething {

    synchronized public void nik1() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("+ nik-1");
        }
    }

    synchronized public void nik2() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("- nik-2");
        }
    }
}


public class Main {
    public static void main(String[] args) {
        DoSomething doSomething1 = new DoSomething();
        DoSomething doSomething2 = new DoSomething();

        Thread t1 = new Thread(()->doSomething1.nik1());
        Thread t2 = new Thread(()->doSomething2.nik2());

        t1.start();
        t2.start();
    }
}
