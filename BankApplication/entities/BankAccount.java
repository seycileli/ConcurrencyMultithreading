public class BankAccount {

	private String accountNumber;
	private double balance;
	private Lock lock;

	public BankAccount(String accountNumber, double intialBalance) {
		this.accountNumber = accountNumber;
		this.balance = intialBalance;
		this.lock = new ReentrantLock();
	}

	public void deposit(double account) {
		try {
			if(lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
				
				try {
					balance += amount
				} finally {
					lock.unlock();
				}
			} else {
				System.out.println("Could not get the lock");
				//access denied for example
			}


		} catch(InterruptedException e) {
			System.out.println("InterruptedException caught")
		}
	}

	public void withdraw(double amount) {
		lock.lock();
		try {
			balance -= amount;
		} finally {
			lock.unlock();
		}
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void printAccountNumber() {
		System.out.println("Account number = " + accountNumber);
	}
}
