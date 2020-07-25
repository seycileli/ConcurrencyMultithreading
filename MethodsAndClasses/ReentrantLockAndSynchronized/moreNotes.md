Concurrency / Multithreading

ExecutiveService interface, found in java.util.Concurrent package

ThreadPool is a managed set of threads, which reduces the overhead of thread creation. Especially in Applications that use a large amount of threads. A ThreadPool may also limit the number of threads that
active running a blocked at any one particular time. 

When using a certain types of ThreadPools, an application can’t run wild and create an excessive number of threads. In Java, we use ThreadPools through the ExecutiveService implementations.

However you can implement your own ThreadPool by creating  a Class that implements one of the ThreadPool
interfaces and by doing so you can configure the underlying ThreadPool and how the ThreadPool is managed but it is recommended to use implementations provided by the JVM in most situations.

ExeuctiveService

src: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html

ExecutiveService method,

When using ExecutiveService, a thing to remember is that we have to terminate the application manually, otherwise it will continue to run.

If we don’t terminate the application it will remain live even after the main thread has terminated. So we need to actually call the shutdown method in order to shut down the service.

exeuctiveService.shutdown();

When the shutdown(); method is called, it’ll wait for any executing tasks to terminate.

For application that use a large number of thread, it is vital to use ExecutiveService because using them allows the JVM to optimize thread management.

What is a Deadlock?

A deadlock occurs when two or more thread are blocked on locks and every thread that’s blocked is holding a lock that another block thread wants.

For example;

Thread 1 is holding lock 1
=== waiting to acquire lock 2 ===
BUT -> thread 2 is holding lock 2
=== waiting to acquire lock 1 === BECAUSE??
all threads holding the locks are blocked, and they’ll never release the locks they’re holding, and so none of the waiting threads will actually ever run

It isn’t possible for one thread to hold one lock but not the other lock. This is the key to avoiding deadlocks, when two or more threads will be competing for two or more locks.

you want to make sure that all threads will try to obtain the locks in the same order. 

Deadlock in java is a part of multithreading. Deadlock can occur in a situation when a thread is waiting for an object lock, that is acquired by another thread and second thread is waiting for an object lock that is acquired by first thread. Since, both threads are waiting for each other to release the lock, the condition is called deadlock.
