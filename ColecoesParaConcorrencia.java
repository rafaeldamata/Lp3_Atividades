import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ColecoesParaConcorrencia{
    //private static Map<Integer,String> mapa = new ConcurrentHashMap<>();
    private static BlockingQueue<String> fila = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        MeuRunnable runnable = new MeuRunnable();
        Thread t0 = new Thread(runnable);
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t0.start();
        t1.start();
        t2.start();

        // try {
        //     t0.join();
        //     t1.join();
        //     t2.join();

        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        System.out.println(fila);
    }

    public static class MeuRunnable implements Runnable{
        public void run(){
            // mapa.put(new Random().nextInt(),"LP-III");
            fila.add("LP-III");
            String name = Thread.currentThread().getName();
            System.out.println(name + " inseriu na lista.");
        }
    }
}