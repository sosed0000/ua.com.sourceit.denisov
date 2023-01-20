package task5.subtask3;

import java.util.ArrayList;
import java.util.List;

public class PairOfCountersThread {
    public static int counter1 = 0, counter2 = 0;

    public static void main(String[] args) throws InterruptedException {

        int threadQuantity = 20;


        System.out.println("Not synchronised");
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadQuantity; i++) {
            threads.add(new Thread(() -> {
                System.out.println("counter1 == counter2: " + (counter1 == counter2));
                counter1++;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                counter2++;
            }));
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }


        System.out.println("Synchronised");
        counter1 = counter2 = 0;
        Object o = new Object();
        threads = new ArrayList<>();
        for (int i = 0; i < threadQuantity; i++) {
            threads.add(new Thread(() -> {
                synchronized (o) {
                    System.out.println("counter1 == counter2: " + (counter1 == counter2));
                    counter1++;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    counter2++;
                }
            }));
        }
        threads.forEach(Thread::start);

    }

}
