import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MainSynchronization {

    public static final String EOF = "EOF";
    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_PURPLE);
        MyConsumer myConsumer = new MyConsumer(buffer, ThreadColor.ANSI_YELLOW);
        MyConsumer myConsumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN);

        new Thread(producer).start();
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

    public MyProducer(List<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    public void run() {
        //creating Instance of Random class
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num : nums) {
            try {
                System.out.println(color + " adding... " + num);
                synchronized (buffer) {
                    buffer.add(num);
                }

                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }

        System.out.println(color + "adding EOF and exiting...");
        synchronized (buffer) {
            buffer.add("EOF");
        }
    }
}

class MyConsumer implements Runnable {
    private List<String> buffer;
    private String color;

    public MyConsumer(List<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    public void run() {
        while (true) {
            synchronized (buffer) {
                if (buffer.isEmpty()) {
                    continue;
                }
                if (buffer.get(0).equals(MainSynchronization.EOF)) {
                    System.out.println(color + "exiting");
                    break;
                } else {
                    System.out.println(color + "Removed" + buffer.remove(0));
                }
            }
        }
    }
}
