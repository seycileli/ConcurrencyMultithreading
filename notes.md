            /* 
               Notes for MyProducer class:
            
               Runnable is an interface for multithreading
             * implementing Runnable because we want it to run as a background thread
             *
             * synchronize the two buffer calls to add because ArrayList
             * isn't Thread safe.
             *
             * IF we don't synchronize, and the producer is suspended in the middle of running
             * the add method, and one of the consumers then calls get() or remove()
             * the internal integrity of the ArrayList might be compromised again
             * depending on timing.
             *
             * So we synchronized in those specific locations, adding the numbers to buffer
             * and EOF character, therefore adding a print statement won't be necessary to add
             * and doing this way, will be us adding the data.
             * */
        
        /*
        
        Notes for MyConsumer Class:
        
        the code that we want thread-safety and to avoid thread interference
        we'll use the ReentrantLock class, then lcok(); and unlock(); method from ReentrantLock class
        we're using the lock method to lock, and the unlock method to release the lock
        when using lock objects, WE as the developers are responsible for releasing the lock
        it won't automatically as it will vs synchronization
    
        the intrinsic locks that we use with synchronized block are always released
        when the thread holding the lock exits the synchronized block */
    
    /*
    * ReentrantLock
    * java.util.concurrent.locks.ReentrantLock implements java.util.concurrent.locks.Lock.
    * ReentrantLock behaves same as synchronized method but with more capability.
    * When a thread requests for lock for a shared resource, it is permitted only if resource is unlocked.
    * If thread does not acquire lock, it will be blocked until it acquires lock on the resource.
    *
    * unlock() is called always in FINALLY block to ensure that if there is exception in method body,
    * lock must be released.
    *
    * lock(): When a thread calls lock() method, thread will get lock only if no other thread is
    * already holding the lock on the resource.
    * If no other thread is already holding the lock, the current thread gets the lock and control returns
    * from lock() method immediately. After getting lock, hold count becomes one and if thread again requests
    * for lock, and gets success, hold count will be incremented by one. If the lock is held by another thread,
    * current thread will be blocked until it gets lock successfully.
    * If the hold count for current thread is greater than 0, it means it is holding the lock.
    *
    * unlock(): If current thread is holding the lock and unlock() is called then the hold count is decremented by 1.
    * When hold count reaches to 0, thread is released and resource is unlocked.
    *
    * tryLock(): When the thread calls tryLock() on the resource then if the resource is available, thread acquires
    * the lock and tryLock() returns true and hold count is incremented by 1,
    * no matter that other threads are waiting for lock. Even if fairness mode has been set,
    * tryLock() is served first if lock available and otherwise tryLock() returns false and thread does
    * not get blocked. */
