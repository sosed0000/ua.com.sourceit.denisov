package task4.variant1.subtask5;

import java.util.Random;
import java.util.concurrent.TransferQueue;

public class Operator implements Runnable {
    private final TransferQueue<Call> queue;
    private final String name;
    private final Random random = new Random();

    public Operator(String name, TransferQueue<Call> transferQueue) {
        this.name = name;
        this.queue = transferQueue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Call call = null;
            try {
                call = queue.take();
                System.out.format("Operator %s processing         %s\n", name, call);
                while (call.isActive()) {
                    Thread.sleep(random.nextInt(2000));
                    if (call.isActive()) {
                        call.setActive(false);
                        System.out.format("Operator %s ended              %s\n", name, call);
                    }
                }
            } catch (InterruptedException e) {
                break;
            } finally {
                if (call != null)
                    call.setActive(false);
            }
        }

    }
}
