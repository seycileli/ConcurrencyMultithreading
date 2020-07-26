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
		boolean status = false;

		try {
			if(lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
				
				try {
					balance += amount
					status = true; //if transaction successful
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

		System.out.println("Trancation status = " + status);
	}

	public void withdraw(double amount) {
		boolean status = false;
		try {

			if(lock.tryLock(1000, TimeUnit.MILLISECONDS)) {

				try {
					balance -= amount;
					status = true; //if successful
				} finally {
					lock.unlock();
				}
			} else {
				System.out.println("Could not get the lock");
			}

		} catch(InterruptedException e) {
			System.out.println("InterruptedException caught")
		}

		System.out.println("Trancation status = " + status);
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void printAccountNumber() {
		System.out.println("Account number = " + accountNumber);
	}

}
