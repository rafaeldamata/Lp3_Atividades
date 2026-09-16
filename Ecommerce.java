
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.*;

public class Ecommerce{
    private static BlockingQueue<Pedido> fila = new LinkedBlockingQueue<>();
    private static AtomicInteger processados = new AtomicInteger(0);
    private static AtomicLong valorTotal = new AtomicLong(0);
    private static List<Integer> pedidosProcessados = new ArrayList<>();
    public static void main(String[] args) throws Exception{
        Pedido pedido1 = new Pedido(0,"Ana",120);
        Pedido pedido2 = new Pedido(1,"Bruno",350);
        Pedido pedido3 = new Pedido(2,"Carlos",80);
        Pedido pedido4 = new Pedido(3,"Diana",450);
        Pedido pedido5 = new Pedido(4,"Eduardo",200);
        pedidosProcessados = Collections.synchronizedList(pedidosProcessados);
        ExecutorService executor = null;
         

        try {
            executor = Executors.newSingleThreadExecutor();
            Future<String> f1 = executor.submit(new ProcessarPedidos(pedido1));
            Future <String> f2 = executor.submit(new ProcessarPedidos(pedido2));
            Future <String> f3 = executor.submit(new ProcessarPedidos(pedido3));
            Future <String> f4 = executor.submit(new ProcessarPedidos(pedido4));
            Future <String> f5 = executor.submit(new ProcessarPedidos(pedido5));
            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println(f3.get());
            System.out.println(f4.get());
            System.out.println(f5.get());

            System.out.println("Total de pedidos processados: " + processados);
            System.out.println("R$ " + valorTotal.floatValue());
            System.out.println("Pedidos processados: " + pedidosProcessados);
        } catch (Exception e) {
            throw e;
        } finally {
            if (executor != null)
                executor.shutdownNow();
        }
    }

    public static class ProcessarPedidos implements Callable<String>{
        

        public ProcessarPedidos(Pedido pedido){
            
            fila.add(pedido);
        }
        public String call(){
            
            String name = Thread.currentThread().getName();
            System.out.println("Iniciando pedido " + processados.incrementAndGet() +" " + fila.peek().nome + " na Thread " + name + " ...");
            try {
                Thread.sleep(1000);
                
                pedidosProcessados.add(fila.peek().id);
                valorTotal.accumulateAndGet(fila.peek().valor, (atual,x)->atual+x);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            
            
            return "Finalizando pedido " + fila.poll().id + "\n";
        }
    }

    public static  class Pedido{
        private int id;
        private String nome;
        private long valor;

        public Pedido(int id, String nome, long valor){
            this.id = id;
            this.nome = nome;
            this.valor = valor;
        }
        public String getName(){
            return this.nome;
        }
    }
    

    

}