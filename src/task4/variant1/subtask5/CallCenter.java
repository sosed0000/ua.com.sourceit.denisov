package task4.variant1.subtask5;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class CallCenter {
    public static void main(String[] args) throws InterruptedException {
        TransferQueue<Call> queue = new LinkedTransferQueue<>();

        Thread client1 = new Thread(new Client("Client1", queue));
        Thread client2 = new Thread(new Client("Client2", queue));
        Thread operator1 = new Thread(new Operator("Operator1", queue));
        Thread operator2 = new Thread(new Operator("Operator2", queue));

        client1.start();
        client2.start();
        operator1.start();
        operator2.start();

        Thread.sleep(10 * 1000);

        client1.interrupt();
        client2.interrupt();
        operator1.interrupt();
        operator2.interrupt();


    }


}
