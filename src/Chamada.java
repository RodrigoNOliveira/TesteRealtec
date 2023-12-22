public class Chamada {
    private int origem;
    private int destino;
    private int direcao;
    private int tempo;

    
    public Chamada(int origem, int destino, int direcao) {
        this.origem = origem;
        this.destino = destino;
        this.direcao = direcao;


        if (direcao == 0) {
            setTempo((destino - origem) * 2);
        } else if(direcao == 1){
            setTempo((origem - destino) * 2);
        }
    }

    public int getOrigem() {
        return origem;
    }

    public void setOrigem(int origem) {
        this.origem = origem;
    }

    public int getDestino() {
        return destino;
    }


    public void setDestino(int destino) {
        this.destino = destino;
    }

 
    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    
    

    @Override
    public String toString() {
        return "Chamada [origem=" + origem + ", destino=" + destino + ", direcao=" + direcao + ", tempo=" + tempo + "]";
    }


    public int getDirecao() {
        return direcao;
    }

 
    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }

}
