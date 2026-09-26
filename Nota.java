public class Nota{
    private int id;
    private String cliente;
    private String uf;
    private long valor;

    public Nota(int id, String cliente, String uf, long valor){
        this.id = id;
        this.cliente = cliente;
        this.uf = uf;
        this.valor = valor;
    }
    public long getValor(){
        return this.valor;
    }

    public String getUf(){
        return this.uf;
    }

    public int getId(){
        return this.id;
    }
}