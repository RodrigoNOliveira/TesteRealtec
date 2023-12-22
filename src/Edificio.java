
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.UnaryOperator;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class Edificio {

    @FXML
    private Label andarTxt;

    @FXML
    private Label andarTxtB;

    @FXML
    private Label direcaoA;

    @FXML
    private Label direcaoB;

    @FXML
    private Button chamarBotao;

    @FXML
    private TextField destinoEntrada;

    @FXML
    private TextField origemEntrada;

    @FXML
    private TextArea proxA;

    @FXML
    private TextArea proxB;

    @FXML
    void initialize() {
        configureIntegerField(origemEntrada);
        configureIntegerField(destinoEntrada);

        proxA.setEditable(false);
        proxB.setEditable(false);
    }

    private void configureIntegerField(TextField textField) {

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);

        textField.setTextFormatter(textFormatter);
    }

    @FXML
    void botaoChamar(ActionEvent event) {

        chamarBotao.setDisable(true);
        String origemStr = origemEntrada.getText();
        String destinoStr = destinoEntrada.getText();
        origemStr = origemStr.equalsIgnoreCase("t") ? "0" : origemStr;
        destinoStr = destinoStr.equalsIgnoreCase("t") ? "0" : destinoStr;

        try {
            int origem = Integer.parseInt(origemStr);
            int destino = Integer.parseInt(destinoStr);

            if (origem >= 0 && origem <= 10 && destino >= 0 && destino <= 10 && origem != destino) {

                chamarElevador(origem, destino);

            } else if (destino <= 10 && origem != destino) {
                exibirAlerta("Andares inválidos", "Certifique-se de inserir valores diferentes.");
            } else {

                exibirAlerta("Andares inválidos",
                        "Certifique-se de inserir valores correspondente aos andares 0 a 10.");
            }
            origemEntrada.clear();
            destinoEntrada.clear();
        } catch (NumberFormatException e) {
            exibirAlerta("Andares inválidos", "Certifique-se de inserir valores correspondente aos andares 0 a 10.");
        }
        Platform.runLater(() -> chamarBotao.setDisable(false));
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public Label getAndarTxt() {
        return andarTxt;
    }

    public void setAndarTxt(Label andarTxt) {
        this.andarTxt = andarTxt;
    }

    public void atualizarAndarTxt(int novoAndar) {
        String textoAndar;
        if (novoAndar == 0) {
            textoAndar = "Térreo";
        } else {
            textoAndar = "Andar Atual: " + novoAndar;
        }

        final String textoFinal = textoAndar;
        Platform.runLater(() -> andarTxt.setText(textoFinal));
    }

    public void atualizarAndarTxtB(int novoAndar) {
        String textoAndar;
        if (novoAndar == 0) {
            textoAndar = "Térreo";
        } else {
            textoAndar = "Andar Atual: " + novoAndar;
        }

        final String textoFinal = textoAndar;
        Platform.runLater(() -> andarTxtB.setText(textoFinal));
    }

    public void atualizarDirecaoA(String direcao) {
        Platform.runLater(() -> direcaoA.setText("Direção: " + direcao));
    }

    public void atualizarDirecaoB(String direcao) {
        Platform.runLater(() -> direcaoB.setText("Direção: " + direcao));
    }

    public void atualizarProxA(Elevador elevador) {
        if (!getListElevador().get(0).getListEmbarque().isEmpty()) {
            StringBuilder proxAInfo = new StringBuilder("Proximos embarques e desembarques: ");

            for (int i = 0; i < getListElevador().get(0).getListEmbarque().size(); i++) {
                int ori = getListElevador().get(0).getListEmbarque().get(i).getOrigem();
                int dest = getListElevador().get(0).getListEmbarque().get(i).getDestino();

                proxAInfo.append("\nAndar de embarque: ").append(ori).append(", andar de desembarque: ").append(dest);
            }

            Platform.runLater(() -> proxA.setText(proxAInfo.toString()));
        } else if (!getListElevador().get(0).getListChamadas().isEmpty()) {
            StringBuilder proxAInfo = new StringBuilder("Proximas chamadas pendentes:  ");

            for (int i = 0; i < getListElevador().get(0).getListChamadas().size(); i++) {
                int ori = getListElevador().get(0).getListChamadas().get(i).getOrigem();
                int dest = getListElevador().get(0).getListChamadas().get(i).getDestino();

                proxAInfo.append("\nAndar de embarque: ").append(ori).append(", andar de desembarque: ").append(dest);
            }

            Platform.runLater(() -> proxA.setText(proxAInfo.toString()));
        } else {
            Platform.runLater(() -> proxA.setText(""));
        }
    }

    public void atualizarProxB(Elevador elevador) {
        if (!getListElevador().get(1).getListEmbarque().isEmpty()) {
            StringBuilder proxBInfo = new StringBuilder("Proximos embarques e desembarques: ");

            for (int i = 0; i < getListElevador().get(1).getListEmbarque().size(); i++) {
                int ori = getListElevador().get(1).getListEmbarque().get(i).getOrigem();
                int dest = getListElevador().get(1).getListEmbarque().get(i).getDestino();

                proxBInfo.append("\nAndar de embarque: ").append(ori).append(", andar de desembarque: ").append(dest);
            }

            Platform.runLater(() -> proxB.setText(proxBInfo.toString()));
        } else if (!getListElevador().get(1).getListChamadas().isEmpty()) {
            StringBuilder proxBInfo = new StringBuilder("\nProximas chamadas pendentes: ");

            for (int i = 0; i < getListElevador().get(1).getListChamadas().size(); i++) {
                int ori = getListElevador().get(1).getListChamadas().get(i).getOrigem();
                int dest = getListElevador().get(1).getListChamadas().get(i).getDestino();

                proxBInfo.append("\nAndar de embarque: ").append(ori).append(", andar de desembarque: ").append(dest);
            }

            Platform.runLater(() -> proxB.setText(proxBInfo.toString()));
        } else {
            Platform.runLater(() -> proxB.setText(""));
        }
    }

    private String nome;
    private int andares;
    private Timer timer;
    private Elevador elevadorA;
    private Elevador elevadorB;
    private ArrayList<Elevador> listElevador;
    private Chamada chamada;
    public Scanner scanner = new Scanner(System.in);

    public Edificio() {
        elevadorA = new Elevador("A");
        elevadorB = new Elevador("B");
        this.listElevador = new ArrayList<Elevador>();
        getListElevador().add(elevadorA);
        getListElevador().add(elevadorB);

    }

    public void chamarElevador(int origem, int destino) {

        int direcao = 3;

        if (origem < destino) {
            direcao = 0;
        } else if (destino < origem) {
            direcao = 1;
        }
        if (direcao != 3) {
            chamada = new Chamada(origem, destino, direcao);

            for (int i = 0; i < getListElevador().size(); i++) {

                if (getListElevador().get(0).getAndarAtual() == 0) {
                    getListElevador().get(0).chamarElevador(chamada);
                    getListElevador().get(0).setTempo(getListElevador().get(0).getTempo() + chamada.getTempo());
                    System.out.println(getListElevador().get(0).getListChamadas());
                    System.out.println(getListElevador().get(0).toString());
                } else if (getListElevador().get(1).getAndarAtual() == 0) {
                    getListElevador().get(1).chamarElevador(chamada);
                    getListElevador().get(1).setTempo(getListElevador().get(1).getTempo() + chamada.getTempo());
                    System.out.println(getListElevador().get(1).getListChamadas());
                    System.out.println(getListElevador().get(1).toString());
                } else if (direcao == 0 && elevMenor() == 0) {
                    getListElevador().get(0).chamarElevador(chamada);
                    getListElevador().get(0).setTempo(getListElevador().get(0).getTempo() + chamada.getTempo());
                    System.out.println(getListElevador().get(0).getListChamadas());
                    System.out.println(getListElevador().get(0).toString());
                } else if (direcao == 0 && elevMenor() == 1) {
                    getListElevador().get(1).chamarElevador(chamada);
                    getListElevador().get(1).setTempo(getListElevador().get(1).getTempo() + chamada.getTempo());
                    System.out.println(getListElevador().get(1).getListChamadas());
                    System.out.println(getListElevador().get(1).toString());
                } else if (direcao == 1 && elevMenor() == 0) {
                    getListElevador().get(1).chamarElevador(chamada);
                    getListElevador().get(1).setTempo(getListElevador().get(1).getTempo() + chamada.getTempo());
                    System.out.println(getListElevador().get(1).getListChamadas());
                    System.out.println(getListElevador().get(1).toString());
                } else if (direcao == 1 && elevMenor() == 1) {
                    getListElevador().get(0).chamarElevador(chamada);
                    getListElevador().get(0).setTempo(getListElevador().get(0).getTempo() + chamada.getTempo());
                    System.out.println(getListElevador().get(0).getListChamadas());
                    System.out.println(getListElevador().get(0).toString());
                } else {
                    getListElevador().get(1).chamarElevador(chamada);
                    getListElevador().get(1).setTempo(getListElevador().get(1).getTempo() + chamada.getTempo());
                    System.out.println(getListElevador().get(1).getListChamadas());
                    System.out.println(getListElevador().get(1).toString());
                }

                destino(getListElevador().get(i));
                movimentaElevador();
            }

        }

    }

    public int elevMenor() {
        int x = 0;
        if (getListElevador().get(0).getAndarAtual() <= getListElevador().get(1).getAndarAtual()) {
            x = 0;
        } else {
            x = 1;
        }
        return x;
    }

    public void movimentaElevador() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                for (int i = 0; i < getListElevador().size(); i++) {

                    for (int j = 0; j < getListElevador().size(); j++) {

                        if (getListElevador().get(j).getListChamadas().size() >= 2) {
                            for (int x = 0; x < getListElevador().get(j).getListChamadas().size(); x++) {
                                for (int z = 1; z < getListElevador().get(j).getListChamadas().size(); z++) {
                                    if (getListElevador().get(j).getListChamadas().get(x)
                                            .getOrigem() == getListElevador().get(j)
                                                    .getListChamadas().get(z).getOrigem()
                                            && getListElevador().get(j).getListChamadas().get(x)
                                                    .getDestino() == getListElevador()
                                                            .get(j).getListChamadas().get(z).getDestino()) {
                                        getListElevador().get(j).getListChamadas()
                                                .remove(getListElevador().get(j).getListChamadas().get(z));
                                    }
                                }
                            }
                        }
                    }

                    if (getListElevador().get(i).getAndarAtual() != getListElevador().get(i).getDestinoComeco()) {
                        if (getListElevador().get(i).getDirecao() == 1) {
                            if (getListElevador().get(i).getAndarAtual() < getListElevador().get(i)
                                    .getDestinoComeco()) {
                                getListElevador().get(i).setAndarAtual(getListElevador().get(i).getAndarAtual() + 1);
                                getListElevador().get(i).setDirecao(0);
                            } else {
                                getListElevador().get(i).setAndarAtual(getListElevador().get(i).getAndarAtual() - 1);
                                getListElevador().get(i).setDirecao(1);
                            }
                        } else if (getListElevador().get(i).getDirecao() == 0) {
                            if (getListElevador().get(i).getAndarAtual() < getListElevador().get(i)
                                    .getDestinoComeco()) {
                                getListElevador().get(i).setAndarAtual(getListElevador().get(i).getAndarAtual() + 1);
                                getListElevador().get(i).setDirecao(0);
                            } else {
                                getListElevador().get(i).setAndarAtual(getListElevador().get(i).getAndarAtual() - 1);
                                getListElevador().get(i).setDirecao(1);
                            }
                        }
                        destino(getListElevador().get(i));
                    }
                    if (i == 0) {
                        atualizarAndarTxt(getListElevador().get(i).getAndarAtual());
                        atualizarProxA(getListElevador().get(i));
                        if (getListElevador().get(i).getDirecao() == 0) {
                            atualizarDirecaoA("Subindo");
                        } else if (getListElevador().get(i).getDirecao() == 1) {
                            atualizarDirecaoA("Descendo");
                        } else if (getListElevador().get(i).getListEmbarque().isEmpty()
                                || getListElevador().get(i).getDirecao() == 3) {
                            atualizarDirecaoA("Parado");
                        }
                    } else if (i == 1) {
                        atualizarAndarTxtB(getListElevador().get(i).getAndarAtual());
                        atualizarProxB(getListElevador().get(i));
                        if (getListElevador().get(i).getDirecao() == 0) {
                            atualizarDirecaoB("Subindo");
                        } else if (getListElevador().get(i).getDirecao() == 1) {
                            atualizarDirecaoB("Descendo");
                        } else {
                            atualizarDirecaoB("Parado");
                        }
                    }

                    if (!getListElevador().get(i).getListChamadas().isEmpty()) {
                        embarcar(getListElevador().get(i).getAndarAtual());

                    }
                    if (!getListElevador().get(i).getListEmbarque().isEmpty()) {
                        desembarcando(getListElevador().get(i).getAndarAtual());

                    }

                    if (getListElevador().get(i).getListChamadas().isEmpty()
                            && getListElevador().get(i).getListEmbarque().isEmpty()) {
                        timer.cancel();
                    }

                }

            }

        }, 0, 2000);
    }

    public void embarcar(int andar) {

        for (int j = 0; j < getListElevador().size(); j++) {

            Elevador elevador = getListElevador().get(j);

            for (int i = 0; i < elevador.getListChamadas().size(); i++) {

                if (elevador.getDirecao() == 0 && elevador.getListChamadas().get(i).getDirecao() == 1) {
                    elevador.setDirecao(0);
                }

                if (elevador.getListChamadas().get(i).getOrigem() == andar
                        && elevador.getListChamadas().get(i).getDirecao() == elevador.getDirecao()) {
                    if (!elevador.getListChamadas().isEmpty()) {
                        elevador.setDestinoComeco(elevador.getListChamadas().get(i).getDestino());
                        elevador.getListEmbarque().add(elevador.getListChamadas().get(i));
                    }

                    elevador.setEmbarcado(elevador.getEmbarcado() + 1);

                    elevador.setDirecao(elevador.getListEmbarque().get(i).getDirecao());

                    if (!elevador.getListChamadas().isEmpty()) {
                        elevador.getListChamadas().remove(elevador.getListChamadas().get(i));
                    }

                }
            }

            if (elevador.getEmbarcado() > 0) {
                if (!elevador.getListEmbarque().isEmpty()) {
                    elevador.setDestinoComeco(elevador.getListEmbarque().get(0).getDestino());
                }

            }
            if (elevador.getEmbarcado() == 0 && !elevador.getListChamadas().isEmpty()) {
                elevador.setDirecao(elevador.getListChamadas().get(0).getDirecao());
            }
        }
    }

    public void desembarcando(int andar) {

        for (int j = 0; j < getListElevador().size(); j++) {
            Elevador elevador = getListElevador().get(j);

            for (int i = 0; i < elevador.getListEmbarque().size(); i++) {
                if (!elevador.getListEmbarque().isEmpty()) {
                    if (elevador.getListEmbarque().get(i).getDestino() == elevador.getAndarAtual()) {
                        elevador.setEmbarcado(elevador.getEmbarcado() - 1);
                        if (!elevador.getListEmbarque().isEmpty()) {
                            elevador.setTempo(elevador.getTempo() - elevador.getListEmbarque().get(i).getTempo());
                            elevador.getListEmbarque().remove(elevador.getListEmbarque().get(i));
                        }
                        destino(elevador);
                    }
                }
            }
        }
    }

    public void destino(Elevador elevador) {

        if (elevador.getListChamadas().isEmpty() && elevador.getListEmbarque().isEmpty()) {
            elevador.setDirecao(3);
            return;
        }

        if (elevador.getEmbarcado() > 0) {
            for (int i = 0; i < elevador.getListChamadas().size(); i++) {
                Chamada chamada = elevador.getListChamadas().get(i);

                if (elevador.getDirecao() == chamada.getDirecao()) {
                    embarcar(elevador.getAndarAtual());
                }
            }

        }

        if (!elevador.getListChamadas().isEmpty()) {
            elevador.setDestinoComeco(elevador.getListChamadas().get(0).getOrigem());
            if (elevador.getEmbarcado() == 0) {
                elevador.setDirecao(elevador.getListChamadas().get(0).getDirecao());
            }
        }

        if (!elevador.getListEmbarque().isEmpty()) {
            elevador.setDestinoComeco(elevador.getListEmbarque().get(0).getDestino());
        }

        for (int i = 0; i < elevador.getListChamadas().size(); i++) {
            if (elevador.getAndarAtual() <= elevador.getListChamadas().get(i).getOrigem()) {
                if (elevador.getDestinoComeco() >= elevador.getListChamadas().get(i).getOrigem()) {
                    elevador.setDestinoComeco(elevador.getListChamadas().get(i).getOrigem());
                }
            }
        }

        for (int i = 0; i < elevador.getListEmbarque().size(); i++) {
            if (!elevador.getListEmbarque().isEmpty()) {
                if (elevador.getDirecao() == elevador.getListEmbarque().get(i).getDirecao()
                        && elevador.getDirecao() == 0) {
                    if (elevador.getDestinoComeco() <= elevador.getListEmbarque().get(i).getOrigem()) {
                        elevador.setDestinoComeco(elevador.getListEmbarque().get(i).getDestino());
                        elevador.setDirecao(elevador.getListEmbarque().get(i).getDirecao());
                    }
                } else if (elevador.getDirecao() == elevador.getListEmbarque().get(i).getDirecao()
                        && elevador.getDirecao() == 1) {
                    if (elevador.getDestinoComeco() <= elevador.getListEmbarque().get(i).getOrigem()) {
                        elevador.setDestinoComeco(elevador.getListEmbarque().get(i).getDestino());
                        elevador.setDirecao(elevador.getListEmbarque().get(i).getDirecao());
                    }
                }
            }
        }

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAndares() {
        return andares;
    }

    public void setAndares(int andares) {
        this.andares = andares;
    }

    public ArrayList<Elevador> getListElevador() {
        return listElevador;
    }

    public void setListElevador(ArrayList<Elevador> listElevador) {
        this.listElevador = listElevador;
    }

}
