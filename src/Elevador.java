import java.util.ArrayList;


public class Elevador {

    private String nome;
    private int direcao;
    private int andarAtual;
    private int destinoComeco;
    private ArrayList<Chamada> listChamadas;
    private ArrayList<Chamada> listEmbarque;
    private int embarcado;
    private int tempo;


    public Elevador() {
    }

    public Elevador(String nome) {
        this.nome = nome;
        this.listChamadas = new ArrayList<Chamada>();
        this.listEmbarque = new ArrayList<Chamada>();
        this.direcao = 3;
        this.andarAtual = 0;
        this.destinoComeco = 0;
        this.embarcado = 0;
        this.tempo = 0;
    }



    public void chamarElevador(Chamada chamada) {
        this.listChamadas.add(chamada);
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }


    public void setDestinoComeco(Integer destinoComeco) {
        this.destinoComeco = destinoComeco;
    }

    public ArrayList<Chamada> getListEmbarque() {
        return listEmbarque;
    }

    public void setListEmbarque(ArrayList<Chamada> listEmbarque) {
        this.listEmbarque = listEmbarque;
    }

   

    @Override
    public String toString() {
        return "Elevador [nome=" + nome + ", direcao=" + direcao + ", andarAtual=" + andarAtual + ", destinoComeco="
                + destinoComeco + ", listChamadas=" + listChamadas + ", listEmbarque=" + listEmbarque + ", embarcado="
                + embarcado + ", tempo=" + tempo + "]";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDirecao() {
        return direcao;
    }

    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }

    public int getAndarAtual() {
        return andarAtual;
    }

    public void setAndarAtual(int andarAtual) {
        this.andarAtual = andarAtual;
    }

    public ArrayList<Chamada> getListChamadas() {
        return listChamadas;
    }

    public void setListChamadas(ArrayList<Chamada> listChamadas) {
        this.listChamadas = listChamadas;
    }

    public int getDestinoComeco() {
        return destinoComeco;
    }

    public void setDestinoComeco(int destinoComeco) {
        this.destinoComeco = destinoComeco;
    }

    public int getEmbarcado() {
        return embarcado;
    }

    public void setEmbarcado(int embarcado) {
        this.embarcado = embarcado;
    }

}
