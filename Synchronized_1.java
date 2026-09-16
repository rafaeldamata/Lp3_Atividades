public class Synchronized_1{
    static int i = -1;
    public static void main(String[] args) {
        Runnable meuRunnable = new MeuRunnable();
        Thread t0 = new Thread(meuRunnable);
        Thread t1 = new Thread(meuRunnable);
        Thread t2 = new Thread(meuRunnable);
        Thread t3 = new Thread(meuRunnable);
        Thread t4 = new Thread(meuRunnable);
        
        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
    public static class MeuRunnable implements Runnable{
        static Object lock1 = new Object();
        static Object lock2 = new Object();
        @Override
        public void run(){
            synchronized (lock1) {
                i++;
                String name = Thread.currentThread().getName();
                System.out.println(name + ": " + i);
            
            }
            synchronized (lock2) {
                i++;
                String name = Thread.currentThread().getName();
                System.out.println(name + ": " + i);
            
            }
        }
    }
}