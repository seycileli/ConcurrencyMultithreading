public class TestAnother {

    public static void main(String[] args) {
        BankAccount account1 = new BankAccount("12345-678", 500.00);
        BankAccount account2 = new BankAccount("98765-432", 1000.00);
 
        new Thread(new Transfer(account1, account2, 10.00), "Transfer1").start();
        new Thread(new Transfer(account2, account1, 55.88), "Transfer2").start();
    }

}
 
class BankAccount {
    private double balance;
    private String accountNumber;
    private Lock lock = new ReentrantLock();
 
    BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
 
    public boolean withdraw(double amount) {
        if (lock.tryLock()) {
            try {
                // Simulate database access
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
            }
            balance -= amount;
            System.out.printf("%s: Withdrew %f\n", Thread.currentThread().getName(), amount);
            return true;
        }
        return false;
    }
 
    public boolean deposit(double amount) {
        if (lock.tryLock()) {
            try {
                // Simulate database access
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
            }
            balance += amount;
            System.out.printf("%s: Deposited %f\n", Thread.currentThread().getName(), amount);
            return true;
        }
        return false;
    }
 
    public boolean transfer(BankAccount destinationAccount, double amount) {
        if (withdraw(amount)) {
            if (destinationAccount.deposit(amount)) {
                return true;
            }
            else {
                // The deposit failed. Refund the money back into the account.
                System.out.printf("%s: Destination account busy. Refunding money\n",
                        Thread.currentThread().getName());
                deposit(amount);
            }
        }
 
        return false;
    }
}
 
class Transfer implements Runnable {
    private BankAccount sourceAccount, destinationAccount;
    private double amount;
 
    Transfer(BankAccount sourceAccount, BankAccount destinationAccount, double amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }
 
    public void run() {
        while (!sourceAccount.transfer(destinationAccount, amount))
            continue;
        System.out.printf("%s completed\n", Thread.currentThread().getName());
    }


/*

    This code will gave rise to a LiveLock situation. Try and figure it out.

    edit;

    The two threads aren't blocked but they can not progress. 
    They keep withdrawing money, trying to do the transfer,
    failing, and refunding the money. Then they'll loop back and try to do the
    transer again. Application has to bemanually terminated.

    How do we fix this?
    Figure out whats wrong with this application, 

    Thread transfer1 calls the transfer() to transfer money from acount1 to account2

    transfer1 gets account1 lock (when the transfer() method calls the withdraw()
    method) --- and withdraws the money to be transferred from account1

    thread transfer2 calls transfer() to transfer money from account2 to account1

    transfer2 gets account2 lock and withdraws the money to be transferred from account2

    transfer1 calls account2s deposit() method, but transfer2 is holding the lock, so the
    transfer fails. It refunds the money back into account1 (it's stil holding the lock) and
    loops back.

    transfer2 calls accounts1 deposit() method, but transfer1 is holding the lock, so the
    transfer fails. It refunds the money back into account2 (its still holding the lock)
    and loops back

    since transfer1 and transfer2 never release the locks they're holding, they'll keep
    looping until we manually terminate the application

    the fix in this case is to have the thread release the lock by calling the unlock() method.
    When dealing with Lock objectts, we ALWAYS want to use try / finally block 

    now when we run the transfer, the transfers will eventually be successful and the application
    will terminate normally

*/
