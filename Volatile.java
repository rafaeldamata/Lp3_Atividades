import java.lang.Thread.State;

public class Volatile{
    private static volatile int numero = 0;
    private static volatile boolean preparado = false;

    public static void main(String[] args) {
        MeuRunnable runnable = new MeuRunnable();
        Thread t0 = new Thread(runnable);
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t0.start();
        t1.start();
        t2.start();
        numero = 42;
        preparado = true;

        while (t0.getState() != State.TERMINATED
         || t1.getState() != State.TERMINATED 
         || t2.getState() != State.TERMINATED){

        }

        numero = 0;
        preparado = false;
        
    }

    public static class MeuRunnable implements Runnable{
        public void run(){
            while (!preparado){
                Thread.yield();
            }
            if (numero != 42){
                throw new IllegalStateException("LP-III");
            }
            System.out.println("Numero: " + numero);
        }
    }

}