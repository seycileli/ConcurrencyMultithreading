import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class MainReentrantLock {

    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        ReentrantLock bufferLock = new ReentrantLock();
        /* pass buffer ArrayList into all 3 instances */
        MyProducer myProducer = new MyProducer(buffer, ThreadColor.ANSI_YELLOW, bufferLock);
        MyConsumer myConsumer = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE, bufferLock);
        MyConsumer myConsumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN, bufferLock);

        /* start the 3 threads */
        new Thread(myProducer).start();
        new Thread(myConsumer).start();
        new Thread(myConsumer2).start();

    }

}

class MyProducer implements Runnable {
    /* Runnable is an interface for multithreading
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

    private List<String> buffer;
    private String color;
    private ReentrantLock bufferLock;

    public MyProducer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    public void run() {
        //creating Instance of Random class
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num : nums) {
            try {
                System.out.println(color + " adding... " + num);
                bufferLock.lock();
                buffer.add(num);
                bufferLock.unlock();

                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
        System.out.println(color + "adding EOF and exiting...");
        bufferLock.lock();
        buffer.add("EOF");
        bufferLock.unlock();
    }
}

class MyConsumer implements Runnable {
    private List<String> buffer;
    private String color;
    private ReentrantLock bufferLock;
    /*
    the code that we want thread-safety and to avoid thread interference
    we'll use the ReentrantLock class, then lcok(); and unlock(); method from ReentrantLock class
    we're using the lock method to lock, and the unlock method to release the lock
    when using lock objects, WE as the developers are responsible for releasing the lock
    it won't automatically as it will vs synchronization

    the intrinsic locks that we use with synchronized block are always released
    when the thread holding the lock exits the synchronized block */

    public MyConsumer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    public void run() {
        while (true) {
            bufferLock.lock();
            if (buffer.isEmpty()) {
                bufferLock.unlock();
                continue;
            }
            if (buffer.get(0).equals(MainReentrantLock.EOF)) {
                System.out.println(color + "exiting");
                bufferLock.unlock();
                break;
            } else {
                System.out.println(color + "Removed" + buffer.remove(0));
            }
            bufferLock.unlock();
        }
    }
}




