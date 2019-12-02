package UI;

import exceptions.FormularioException;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.textfield.CustomTextField;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import submissao.Situacao;
import submissao.Submissao;
import submissao.SubmissaoApresentacao;
import submissao.SubmissaoCientifica;
import submissao.categorias.*;
import utils.HibernateUtil;
import utils.InterfaceUtil;

import java.net.URL;
import java.util.*;

public class submissaoUpdateController implements Initializable {

    @FXML
    private VBox vboxTop;
    //Submissao.class
    @FXML
    private TextField textFieldTitulo;
    @FXML
    private ChoiceBox<Situacao> choiceBoxSituacao;

    @FXML
    private VBox vboxAutores;
    @FXML
    private CustomTextField textFieldAutor;
    @FXML
    private CheckListView<String> autores;


    //SubmissaoCientifica.class
    @FXML
    private GridPane gridCientifica;

    @FXML
    private VBox vboxInstituicoes;
    @FXML
    private CustomTextField textFieldInstituicao;
    @FXML
    private CheckListView<String> instituicoes = new CheckListView<>();

    @FXML
    private VBox vboxPalavraschave;
    @FXML
    private CustomTextField textFieldPalavraschave;
    @FXML
    private CheckListView<String> palavraschave = new CheckListView<>();

    //SubmissaoApresentacao.class
    @FXML
    private GridPane gridApresentacao;
    @FXML
    private TextArea textAreaResumo;
    @FXML
    private TextArea textAreaAbstract;
    @FXML
    private Slider sliderDuracao;

    //Classes específicas
    @FXML
    private GridPane gridEspecifica;
    @FXML
    private Label spResumoLabel;
    @FXML
    private Label spAbstractLabel;
    @FXML
    private Label spRecursosLabel;
    @FXML
    private Label spMetodologiaLabel;
    @FXML
    private Label spTipoLabel;
    @FXML
    private Label spOrientadorLabel;
    @FXML
    private Label spCursoLabel;
    @FXML
    private Label spAnoLabel;
    @FXML
    private Label spNumPagsLabel;
    @FXML
    private Label spCurriculoLabel;
    @FXML
    private TextArea spResumo;
    @FXML
    private TextArea spAbstract;
    @FXML
    private TextArea spRecursos;
    @FXML
    private TextArea spMetodologia;
    @FXML
    private ChoiceBox<Tipo> spTipo;
    @FXML
    private TextField spOrientador;
    @FXML
    private TextField spCurso;
    @FXML
    private TextField spAno;
    @FXML
    private TextField spNumPags;
    @FXML
    private TextField spCurriculo;

    @FXML
    private Button btnConfirmar;
    @FXML
    private Button closeButton;

    private Submissao sub;

    void init(Submissao sub) {
        this.sub = sub;
        removeSpecifics();
        //remove todos quando fecha a janela
        closeButton.getScene().getWindow().setOnHiding(e -> removeSpecifics());
        initSubPai();
    }

    //Submissao.class
    private void initSubPai() {
        textFieldTitulo.setText(sub.getTitulo());
        choiceBoxSituacao.setValue(sub.getSituacao());
        textFieldAutor.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                String input = textFieldAutor.getText();

                textFieldAutor.clear();
                textFieldAutor.setDisable(true);

                if (autores.getItems().size() < sub.getMAX_AUTORES())
                    if (!autores.getItems().contains(input))
                        autores.getItems().add(input);
                    else
                        InterfaceUtil.erro("O autor já faz parte da submissão.");
                else if (sub.getClass().getSimpleName().equals("Palestra") // caso especial pois a palestra e a
                        || sub.getClass().getSimpleName().equals("Monografia") // monografia só podem ter um autor
                        && autores.getItems().size() == sub.getMAX_AUTORES()) {
                    if (!autores.getItems().contains(input))
                        autores.getItems().set(0, input);
                    else
                        InterfaceUtil.erro("O autor já faz parte da submissão.");
                } else
                    InterfaceUtil.erro("Número máximo de autores excedido.");


                textFieldAutor.setDisable(false);
            }
        });

        //carrega a list dos autores
        autores.getItems().addAll(FXCollections.observableArrayList(sub.getAutores()));
        autores.getCheckModel().getCheckedIndices().addListener((ListChangeListener<Integer>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (int i : c.getAddedSubList()) {
                        if (autores.getItems().size() != 1) {
                            autores.getItems().remove(i);
                        } else {
                            InterfaceUtil.erro("Deve existir pelo menos um autor.");
                        }
                    }
                }
            }
        });

        initSubMed();
    }

    //SubmissaoApresentacao.class e SubmissaoCientifica.class
    private void initSubMed() {
        switch (sub.getClass().getSuperclass().getSimpleName()) {
            case "SubmissaoApresentacao":
                vboxTop.getChildren().add(1, gridApresentacao);

                textAreaResumo.setText(((SubmissaoApresentacao) sub).getResumo());
                textAreaAbstract.setText(((SubmissaoApresentacao) sub).get_abstract());
                sliderDuracao.setValue(((SubmissaoApresentacao) sub).getDuracao());

                initSubLow();
                break;
            case "SubmissaoCientifica":
                vboxTop.getChildren().add(1, gridCientifica);

                textFieldInstituicao.setOnKeyPressed(keyEvent -> {
                    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                        String input = textFieldInstituicao.getText();

                        textFieldInstituicao.clear();
                        textFieldInstituicao.setDisable(true);

                        if (instituicoes.getItems().size() < ((SubmissaoCientifica) sub).getMAX_INSTITUICOES())
                            if (!instituicoes.getItems().contains(input))
                                instituicoes.getItems().add(input.toUpperCase());
                            else
                                InterfaceUtil.erro("Essa instituição já faz parte da submissão.");
                        else InterfaceUtil.erro("Número máximo de instituições excedido.");

                        textFieldInstituicao.setDisable(false);
                    }
                });
                instituicoes.getItems().addAll(FXCollections.observableArrayList(((SubmissaoCientifica) sub).getInstituicao()));
                instituicoes.getCheckModel().getCheckedIndices().addListener((ListChangeListener<Integer>) c -> {
                    while (c.next()) {
                        if (c.wasAdded()) {
                            for (int i : c.getAddedSubList()) {
                                if (instituicoes.getItems().size() != 1) {
                                    instituicoes.getItems().remove(i);
                                } else {
                                    InterfaceUtil.erro("Deve existir pelo menos uma instituição.");
                                }
                            }
                        }
                    }
                });

                textFieldPalavraschave.setOnKeyPressed(keyEvent -> {
                    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                        String input = textFieldPalavraschave.getText();

                        textFieldPalavraschave.clear();
                        textFieldPalavraschave.setDisable(true);
                        if (palavraschave.getItems().size() < ((SubmissaoCientifica) sub).getMAX_PALAVRASCHAVE())
                            if (!palavraschave.getItems().contains(input))
                                palavraschave.getItems().add(input);
                            else
                                InterfaceUtil.erro("Essa palavra chave já existe na submissão.");
                        else InterfaceUtil.erro("Número máximo de palavras chave excedido");

                        textFieldPalavraschave.setDisable(false);
                    }
                });
                palavraschave.getItems().addAll(FXCollections.observableArrayList(((SubmissaoCientifica) sub).getPalavraChave()));
                palavraschave.getCheckModel().getCheckedIndices().addListener((ListChangeListener<Integer>) c -> {
                    while (c.next()) {
                        if (c.wasAdded()) {
                            for (int i : c.getAddedSubList()) {
                                if (palavraschave.getItems().size() != 1) {
                                    palavraschave.getItems().remove(i);
                                } else {
                                    InterfaceUtil.erro("Deve existir pelo menos uma palavra chave.");
                                }
                            }
                        }
                    }
                });

                initSubLow();
                break;
        }

    }

    //Classes específicas
    private void initSubLow() {
        vboxTop.getChildren().add(2, gridEspecifica);
        switch (sub.getClass().getSimpleName()) {
            case "Artigo":
                gridEspecifica.addRow(0, spResumoLabel, spResumo);
                gridEspecifica.addRow(1, spAbstractLabel, spAbstract);
                Artigo art = (Artigo) sub;
                spResumo.setText(art.getResumo());
                spAbstract.setText(art.get_abstract());
                break;
            case "Minicurso":
                gridEspecifica.addRow(0, spRecursosLabel, spRecursos);
                gridEspecifica.addRow(1, spMetodologiaLabel, spMetodologia);
                Minicurso mini = (Minicurso) sub;
                spRecursos.setText(mini.getRecursos());
                spMetodologia.setText(mini.getMetodologia());
                break;
            case "Monografia":
                gridEspecifica.addRow(0, spTipoLabel, spTipo);
                gridEspecifica.addRow(1, spOrientadorLabel, spOrientador);
                gridEspecifica.addRow(2, spCursoLabel, spCurso);
                gridEspecifica.addRow(3, spAnoLabel, spAno);
                gridEspecifica.addRow(4, spNumPagsLabel, spNumPags);
                gridEspecifica.addRow(5, spResumoLabel, spResumo);
                gridEspecifica.addRow(6, spAbstractLabel, spAbstract);
                Monografia mono = (Monografia) sub;
                spTipo.setItems(FXCollections.observableArrayList(Tipo.values()));
                spTipo.setValue(mono.getTipo());
                spOrientador.setText(mono.getOrientador());
                spCurso.setText(mono.getCurso());
                spAno.setText(String.valueOf(mono.getAno()));
                spNumPags.setText(String.valueOf(mono.getnPaginas()));
                spResumo.setText(mono.getResumo());
                spAbstract.setText(mono.get_abstract());
                break;
            case "Palestra":
                gridEspecifica.addRow(0, spCurriculoLabel, spCurriculo);
                Palestra pal = (Palestra) sub;
                spCurriculo.setText(pal.getCurriculo());
                break;
            case "RelatorioTecnico":
                gridEspecifica.addRow(0, spAnoLabel, spAno);
                gridEspecifica.addRow(1, spNumPagsLabel, spNumPags);
                gridEspecifica.addRow(2, spResumoLabel, spResumo);
                gridEspecifica.addRow(3, spAbstractLabel, spAbstract);
                RelatorioTecnico rel = (RelatorioTecnico) sub;
                spAno.setText(String.valueOf(rel.getAno()));
                spNumPags.setText(String.valueOf(rel.getnPaginas()));
                spResumo.setText(rel.getResumo());
                spAbstract.setText(rel.get_abstract());
                break;
            case "Resumo":
                //resumo nao tem campos específicos, mas pelo bem da
                //consistência, merece um lugarzinho no switch
                break;
        }
        btnConfirmar.setOnAction(e -> {
            try {
                update(sub);
            } catch (HibernateException | FormularioException ex) {
                InterfaceUtil.erro(ex.getMessage());
            }
        });
    }

    private void removeSpecifics() {
        gridEspecifica.getChildren().clear();
        //grids exceto a primeira
        vboxTop.getChildren().removeAll(
                gridCientifica,
                gridApresentacao,
                gridEspecifica);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        removeSpecifics();
        //popular a choicebox
        choiceBoxSituacao.setItems(FXCollections.observableArrayList(Situacao.values()));
    }

    private void update(Submissao subGenerica) throws HibernateException, FormularioException {
        String errorMsg = methods.form(
                subGenerica,
                textFieldTitulo,
                choiceBoxSituacao,
                autores,
                textAreaResumo,
                textAreaAbstract,
                sliderDuracao,
                spRecursos,
                spMetodologia,
                spCurriculo,
                instituicoes,
                palavraschave,
                spResumo,
                spAbstract,
                spTipo,
                spOrientador,
                spCurso,
                spAno,
                spNumPags);

        if (errorMsg.equals("")) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            session.update(subGenerica);

            session.getTransaction().commit();
            session.close();

            InterfaceUtil.sucesso("A submissão\n\n(" + subGenerica.getClass().getSimpleName() + ") "
                    + subGenerica.getTitulo() +
                    "\n\nfoi alterada com sucesso.");
        } else
            throw new FormularioException(errorMsg);
        closeDialog();
    }

    @FXML
    private void closeDialog() {
        Stage s = (Stage) closeButton.getScene().getWindow();
        s.close();
    }

}