
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Execute_SingleThread_Callable{
    public static void main(String[] args) throws Exception {
        
        ExecutorService executor = null;
        
        try {
            executor = Executors.newSingleThreadExecutor();
            
            
            Future<?> future = executor.submit(new MeuCallable());
            System.out.println(future.isDone());
            System.out.println(future.get());
            // executor.shutdown();
            // executor.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println(future.isDone());
        } catch (InterruptedException e) {
            throw e;
        } finally {
            if (executor != null)
                executor.shutdownNow();
        }
    }

    public static class MeuRunnable implements Runnable{
        public void run(){
            String name = Thread.currentThread().getName();
            System.out.println(name + ": LP-III");
        }
    }

    public static class MeuCallable implements Callable<String>{
        public String call() throws Exception{
            String nome = Thread.currentThread().getName();
            int numero = new Random().nextInt();
            return nome + ": LP-III" + numero;
        }
    }
}