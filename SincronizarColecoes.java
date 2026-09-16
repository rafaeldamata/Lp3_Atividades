import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SincronizarColecoes{
    private static List<String> lista = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        //lista = Collections.synchronizedList(lista);
        MeuRunnable runnable = new MeuRunnable();
        Thread t0 = new Thread(runnable);
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t0.start();
        t1.start();
        t2.start();
        System.out.println(lista);
    }

    public static class MeuRunnable implements Runnable{
        public void run(){
            lista.add("LP-III");
            String name = Thread.currentThread().getName();
            System.out.println(name + " adicionou a lista");
        }
    }
}