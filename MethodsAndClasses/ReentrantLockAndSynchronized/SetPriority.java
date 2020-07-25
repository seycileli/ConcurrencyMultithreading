public class Main {

    private static final Object lock = new Object();
    //will use this to synchronize our code

    public static void main(String[] args) {
        Thread t1 = new Thread(new Worker(ThreadColor.ANSI_PURPLE), "Priority 10");
        Thread t2 = new Thread(new Worker(ThreadColor.ANSI_BLUE), "Priority 8");
        Thread t3 = new Thread(new Worker(ThreadColor.ANSI_CYAN), "Priority 6");
        Thread t4 = new Thread(new Worker(ThreadColor.ANSI_GREEN), "Priority 4");
        Thread t5 = new Thread(new Worker(ThreadColor.ANSI_YELLOW), "Priority 2");

        t1.setPriority(10);
        t2.setPriority(8);
        t3.setPriority(6);
        t4.setPriority(4);
        t5.setPriority(2);

        /* using the setPriority() method, is your way of giving the JVM a suggestion
        * its not actually binding in any way we can never force the
        * OS to run threads in a specific order */

    }

    /* Let's create a worker class in our inner class
    this will contain the code that each thread will ultimately run */
    private static class Worker implements Runnable {
        private int runCount = 1;
        private final String threadColor;

        public Worker(String threadColor) {
            this.threadColor = threadColor;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    System.out.format(threadColor + "%s: runCount = %d\n",
                            Thread.currentThread().getName(), runCount++);
                    //execute critical section of code
                    /* print out the value of the loop counter when we call system.out.format
                    * current value is stored when the string is formatted and then
                    * the values increment */

                }
            }
        }
    }
}
