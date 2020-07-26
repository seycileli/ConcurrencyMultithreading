There are two ways going about synchronizing code found in the BankAccount class, one way is 

adding the synchronized keyword within the method signature, i.e. below

1st solution

public synchronized void deposit(double account) {
	balance += account;
}

2nd solution
public void deposit(double account) {
	synchronized(this) {
		balance += account;
	}
}

IF deposit and withdraw methods contained more code and NOT ALL the
code was a critical section, the second solution is the better
option / better solution.

When and with synchronizing, we want to synchronize the smallest amount of 
code possible, so that way we can minimize the code performance impact on
the application.

So generally, we ONLY want to synchronize an entire method, when all the
code within the code block is a critical section.

threads that READ the account, does not need to be synchronized, 
for example, the getAccountNumber() and printAccountNumber() methods.
That would be over-synchronizing.

Applications with a large number of threads will lead to negative impact
and performance issues.

As mentioned earlier, we want to synchronize the smallest amount of code
when possible, so that way we can minimize the code performance impact
on the application. Nobody, me, you, wants to use a buggy, slow application.

===========================

another way of doing this;

create an instance variable, of Lock class
private Lock lock;

make sure to initialize inside of constructor such as;
this.lock = new ReentrantLock();

then you can proceed to lock and unlock the method.

by using the ReentrantLock, 

	public void deposit(double account) {
		lock.lock();
		try {
			balance += amount;
		} finally {
			//finally, call the unlock
			lock.unlock();
		}
	}

===========================

Just as above, but using tryLock(); method.

Which will look like the code below.

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

===========================

regarding variable -

boolean status = false;

Since status variable is a local variable, it's already Thread safe.
Local variables are stored in a Thread Stack. Therefore each Thread will
have it's own copy. Threads wont interfere with each other when it comes to
setting and getting the status value.
