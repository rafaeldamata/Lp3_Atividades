import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Catracas{
    
    private static AtomicInteger ingressos = new AtomicInteger(0);
    private static Map<Thread, Integer> mapa = new ConcurrentHashMap<>();
    private static List<String> lista = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        lista = Collections.synchronizedList(lista);
        MeuRunnable runnable = new MeuRunnable();
        
        try {
            for (int i = 0; i < 4; i++){
            Thread t = new Thread(runnable);
            t.setName("Catraca " + i);
            mapa.put(t, 0);
        }

        for (Thread t : mapa.keySet()){
            t.start();
        }

        } catch (Exception e) {
            throw e;
        }

        try {
            for (Thread t : mapa.keySet())
                t.join();
        } catch (Exception e) {
            throw e;
        }
        
        
        System.out.println(mapa);
        System.out.println(lista);

    }

    public static class MeuRunnable implements Runnable{
        
        public void run(){
            while (ingressos.get() != 200){
                String name = Thread.currentThread().getName();
                if (ingressos.get() != 200){
                    lista.add(name + " - ingressos: " + ingressos.incrementAndGet());
                
                    for (Thread t : mapa.keySet()){
                        if (t == Thread.currentThread())
                            mapa.put(t,mapa.get(t) + 1);
                    }
                }

            }
        }
    }
}