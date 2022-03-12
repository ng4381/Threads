package deadlock;

class DeadLockTutor {
    Thread t1, t2;
    Account account1 = new Account(100);
    Account account2 = new Account(50);



    private void transferMoney(Account from, Account to) {
        for (int i = 0; i < 100_000; i++) {
            from.transfer(to, 30);
            System.out.println(Thread.currentThread().getName() + i);
        }
    }

    public void testDeadLock(){
        t1 = new Thread( ()-> transferMoney(account1, account2));
        t1 = new Thread( ()-> transferMoney(account2, account1));

        startThreads();
        waitAllThreads();

        System.out.println("buf");
    }

    private void waitAllThreads() {
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startThreads() {
        t1.start();
        t2.start();
    }
}

class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    synchronized public void transfer(Account from, int amount) {
        deposit(amount);
        from.withdraw(amount);
    }

    synchronized public void deposit(int amount) {
        balance += amount;
    }

    synchronized public void withdraw(int amount) {
        balance -= amount;
    }
}

public class Main2 {
    public static void main(String[] args) {

        DeadLockTutor deadLock = new DeadLockTutor();
        deadLock.testDeadLock();


    }
}
