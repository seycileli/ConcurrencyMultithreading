import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Message message = new Message();
        (new Thread(new Writer(message))).start();
        (new Thread(new Reader(message))).start();

    }
}

class Message {
    private String messages;
    private boolean empty = true;

    public synchronized String read() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }

        }
        empty = true;
        return messages;
    }

    /* Void because we aren't returning anything
     * because we're writing */
    public synchronized void write(String messages) {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
        empty = false;
        this.messages = messages;
        notifyAll();
    }
}

class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }

    public void run() {
        String[] messages = {
                "Hello Java",
                "This is my message",
                "I am testing multithreading",
                "Let's see how this looks when we print"
        };

        Random random = new Random();
        for (String s : messages) {
            message.write(s);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
        message.write("Finished");
    }
}

class Reader implements Runnable {
    private Message message;

    public Reader(Message message) {
        this.message = message;
    }

    public void run() {
        Random random = new Random();
        for (String latestMessage = message.read(); !latestMessage.equals("Finished");
             latestMessage = message.read()) {
             System.out.println(latestMessage);

            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
    }
}
