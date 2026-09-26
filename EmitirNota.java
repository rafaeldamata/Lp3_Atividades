import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EmitirNota implements Callable<String>{
    private Nota nota;
    private static volatile boolean paradaSolicitada = false;
    private static AtomicInteger emitidas = new AtomicInteger(0);
    private static AtomicInteger canceladas = new AtomicInteger(0);
    private static AtomicLong valorEmitidoCentavos = new AtomicLong(0);
    private static ConcurrentHashMap<String, Integer> notasporUf = new ConcurrentHashMap<>();
    private static List<String> logSincronizada = Collections.synchronizedList(new ArrayList<>());

    

    public EmitirNota(Nota nota){
        this.nota = nota;
    }

    
    public String call(){
        
        sleep();
        
        if (!paradaSolicitada){
            if (valorEmitidoCentavos.addAndGet(nota.getValor()) >= 1000){
                paradaSolicitada = true;
                logSincronizada.add("Nota " + nota.getId() + " CANCELADA antes de iniciar");
                return "PARADA adicionada na nota " + nota.getId() + ": limite diário atingido";    
            } 
            emitidas.incrementAndGet();
            notasporUf.computeIfAbsent(nota.getUf(), k-> new AtomicInteger(0).incrementAndGet());
            logSincronizada.add("Nota " + nota.getId() + " Emitida (" + nota.getUf() + ")");
            return "Resultado: Nota " + nota.getId() + " emitida com sucesso.";
        }
        else{
        canceladas.incrementAndGet();
        logSincronizada.add("Nota " + nota.getId() + " CANCELADA antes de iniciar");
        return "Resultado: Nota " + nota.getId() + " cancelada (limite diario atingido)";
        }

    }

    public void sleep(){
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void imprimir_dados(){
        System.out.println("Notas emitidas: " + emitidas.get());
        System.out.println("Notas canceladas: " + canceladas.get());
        System.out.println("Valor emitido: R$ " + valorEmitidoCentavos.get());
        System.out.println("Notas por UF: " + notasporUf);
        System.out.println("Parada acionada: " + paradaSolicitada);
        System.out.println("");
        System.out.println("Log de auditoria (" + logSincronizada.size() + " eventos):");
        for (String log : logSincronizada){
            System.out.println(log);
        }
    }
}