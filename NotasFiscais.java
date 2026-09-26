    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;
    import java.util.concurrent.Future;
    import java.util.concurrent.TimeUnit;

    public class NotasFiscais{
        
        private static volatile boolean expedienteEncerrado = false;
        private static List<Future<String>> listaFuture = new ArrayList<>();
        private static List<Nota> listaNotas = new ArrayList<>();
        
        public static void main(String[] args) {
            Nota nota1 = new Nota(1,"Ana","BA",120);
            Nota nota2 = new Nota (2,"Bruno","SP",350);
            Nota nota3 = new Nota(3,"Carlos","BA",80);
            Nota nota4 = new Nota(4,"Diana","RJ",450);
            Nota nota5 = new Nota(5,"Eduardo","SP",200);
            Nota nota6 = new Nota(6,"Fernanda","BA",300);
            Nota nota7 = new Nota(7,"Gabriel","RJ",150);
            Nota nota8 = new Nota(8,"Helena","SP",90);
            Nota nota9 = new Nota(9,"Igor","BA",500);
            Nota nota10 = new Nota(10,"Julia","RJ",60);

            listaNotas.add(nota1);
            listaNotas.add(nota2);
            listaNotas.add(nota3);
            listaNotas.add(nota4);
            listaNotas.add(nota5);
            listaNotas.add(nota6);
            listaNotas.add(nota7);
            listaNotas.add(nota8);
            listaNotas.add(nota9);
            listaNotas.add(nota10);

            List<EmitirNota> lista = new ArrayList<>();


            ExecutorService executor = Executors.newFixedThreadPool(10);

            
            Runnable r1 = () -> {
                    while(!expedienteEncerrado){
                        
                        for (Future<String> f : listaFuture){
                        if (f.isDone()){
                        try {
                            System.out.println(f.get());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                        
                    }
                    
                    sleep();
                    }
                };

            Thread painel = new Thread(r1);
            painel.setDaemon(true);
            painel.start();

            
            try{
            
            for (Nota n : listaNotas){
                Future<String> f = executor.submit(new EmitirNota(n));
                listaFuture.add(f);
            }
            
            
            
            executor.shutdown();
            executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
            expedienteEncerrado = true;
            
            
            for (Future<String> f : listaFuture){
                        
                try {
                    System.out.println(f.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            EmitirNota.imprimir_dados();


            } catch (Exception e) {
                e.printStackTrace();
            } 

            }

        public static void sleep(){
            try {
                Thread.sleep(150);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}