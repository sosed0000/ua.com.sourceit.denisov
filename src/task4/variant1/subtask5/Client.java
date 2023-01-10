package task4.variant1.subtask5;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Client implements Runnable {
    private final TransferQueue<Call> queue;
    private final String name;
    private final AtomicInteger callCount = new AtomicInteger(0);
    private final Random random = new Random();

    public Client(String name, TransferQueue<Call> transferQueue) {
        this.name = name;
        this.queue = transferQueue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Call call = null;
            try {
                call = new Call(name, callCount.incrementAndGet());
                call.setActive(true);
                System.out.printf("Client %s calling to call center %s\n", name, call);
                if (!queue.tryTransfer(call, 1, TimeUnit.SECONDS)) {
                    System.out.printf("Call rejected by call center %s\n", call);
                    call.setActive(false);
                }
                while (call.isActive()) {
                    Thread.sleep(random.nextInt(2000));
                    if (call.isActive()) {
                        call.setActive(false);
                        System.out.format("Caller %s ended                  %s\n", name, call);
                    }
                }
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                return;
            } finally {
                if (call != null)
                    call.setActive(false);
            }
        }
    }


}
