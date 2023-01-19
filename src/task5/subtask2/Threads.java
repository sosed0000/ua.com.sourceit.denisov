package task5.subtask2;

public class Threads {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new MyThreadExt();
        Thread t2 = new Thread(new MyThreadImpl());

        t1.start();
        t1.join();
        t2.start();
    }

    static class MyThreadExt extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                System.out.println(this.getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class MyThreadImpl implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
