
public class Thread_1{
    public static void main(String[] args) {
        Thread t = Thread.currentThread();
        System.out.println(t.getName());
        Runnable meuRunnable = new MeuRunnable();

        Thread t0 = new Thread(meuRunnable);
        // t0.run(); // Apenas exexucando na mesma Thread principal
        
        Thread t1 = new Thread(()->System.out.println("LP3-III")); // metodo Runnable sendo passado em uma funcao lambda
        // t0.run();
        
        Thread t2 = new Thread(meuRunnable);
        t0.start();
        t1.start();
        t2.start();
    }
}