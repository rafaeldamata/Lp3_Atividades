
import java.util.ArrayList;

public class Bilheteria{
    
    static int ingressos = 100;
    public static void main(String[] args) {
        
        Runnable Vendedor = new Vendedor();
        ArrayList<Thread> Threads = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            Thread thread = new Thread(Vendedor);
            thread.setName("Vendedor" + i);
            Threads.add(thread);
        }
        
        for(Thread t : Threads){
            try{
                t.join();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            t.start();
            }
        
            
    }
        
    public static  class Vendedor implements Runnable{
        
        public static Object lock = new Object();
        static int quantidade;
        @Override
        public void run(){
                
                while (ingressos != 0){
                    
                if (ingressos == 0){
                    System.out.println("Ingressos esgotados");
                    System.out.println("Quantidade de ingressos: " + ingressos);
                    System.exit(0);
                }
            synchronized (this){
                ingressos--;
                quantidade = ingressos + 1;
            }
            String name = Thread.currentThread().getName();
            System.out.println(name + " / Numero do ingresso: " +  quantidade + "/ Quantidade de ingressos disponiveis: " + ingressos);
        }
        }
    }
}