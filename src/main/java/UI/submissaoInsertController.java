package UI;

import exceptions.FormularioException;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.type.TextType;
import submissao.Situacao;
import submissao.Submissao;
import submissao.SubmissaoCientifica;
import submissao.categorias.*;
import utils.HibernateUtil;
import utils.InterfaceUtil;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class submissaoInsertController implements Initializable {

    @FXML
    private VBox vboxTop;
    //radio buttons
    @FXML
    private RadioButton radioArtigo;
    @FXML
    private RadioButton radioMonografia;
    @FXML
    private RadioButton radioRelatorioTecnico;
    @FXML
    private RadioButton radioResumo;
    @FXML
    private RadioButton radioMinicurso;
    @FXML
    private RadioButton radioPalestra;
    private RadioButton radioSelecionado;
    private List<RadioButton> radioCientificas;
    private List<RadioButton> radioApresentacoes;

    //Submissao.class
    @FXML
    private GridPane gridSubmissao;
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

    private void init() {
        removeSpecifics();
        radios();
    }

    private void radios() {
        radioCientificas = Arrays.asList(
                radioArtigo,
                radioMonografia,
                radioRelatorioTecnico,
                radioResumo
        );
        radioApresentacoes = Arrays.asList(
                radioMinicurso,
                radioPalestra
        );
        ToggleGroup toggle = new ToggleGroup();
        toggle.getToggles().setAll(
                radioArtigo,
                radioMonografia,
                radioRelatorioTecnico,
                radioResumo,
                radioMinicurso,
                radioPalestra);
        toggle.selectedToggleProperty().addListener((observable, old_toggle, new_toggle) -> {
            if (new_toggle != null) {
                textFieldTitulo.clear();
                autores.getItems().clear();
                choiceBoxSituacao.setValue(null);
                removeSpecifics();
                radioSelecionado = (RadioButton) new_toggle;
                switch (radioSelecionado.getText()) {
                    case "Artigo":
                        sub = new Artigo();
                        break;
                    case "Minicurso":
                        sub = new Minicurso();
                        break;
                    case "Monografia":
                        sub = new Monografia();
                        break;
                    case "Palestra":
                        sub = new Palestra();
                        break;
                    case "Relatório Técnico":
                        sub = new RelatorioTecnico();
                        break;
                    case "Resumo":
                        sub = new Resumo();
                        break;
                }
                initSubPai();
            }
        });
    }

    //Submissao.class
    private void initSubPai() {
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

        autores.getCheckModel().getCheckedIndices().addListener((ListChangeListener<Integer>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (int i : c.getAddedSubList()) {
                        if (autores.getItems().size() > 1)
                            autores.getItems().remove(i);
                    }
                }
            }
        });

        if (radioCientificas.contains(radioSelecionado))
            initSubMed("SubmissaoCientifica");
        else if (radioApresentacoes.contains(radioSelecionado))
            initSubMed("SubmissaoApresentacao");
    }

    //SubmissaoApresentacao.class e SubmissaoCientifica.class
    private void initSubMed(String tipoMed) {
        switch (tipoMed) {
            case "SubmissaoApresentacao":
                vboxTop.getChildren().add(2, gridApresentacao);

                initSubLow(radioSelecionado.getText());
                break;
            case "SubmissaoCientifica":
                vboxTop.getChildren().add(2, gridCientifica);

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
                instituicoes.getItems().addAll(FXCollections.observableArrayList());
                instituicoes.getCheckModel().getCheckedIndices().addListener((ListChangeListener<Integer>) c -> {
                    while (c.next()) {
                        if (c.wasAdded()) {
                            for (int i : c.getAddedSubList()) {
                                if (instituicoes.getItems().size() > 1)
                                    instituicoes.getItems().remove(i);
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
                palavraschave.getItems().addAll(FXCollections.observableArrayList());
                palavraschave.getCheckModel().getCheckedIndices().addListener((ListChangeListener<Integer>) c -> {
                    while (c.next()) {
                        if (c.wasAdded()) {
                            for (int i : c.getAddedSubList()) {
                                if (palavraschave.getItems().size() > 1)
                                    palavraschave.getItems().remove(i);
                            }
                        }
                    }
                });

                initSubLow(radioSelecionado.getText());
                break;
        }

    }

    //Classes específicas
    private void initSubLow(String especifica) {
        vboxTop.getChildren().add(3, gridEspecifica);
        switch (especifica) {
            case "Artigo":
                gridEspecifica.addRow(0, spResumoLabel, spResumo);
                gridEspecifica.addRow(1, spAbstractLabel, spAbstract);
                break;
            case "Minicurso":
                gridEspecifica.addRow(0, spRecursosLabel, spRecursos);
                gridEspecifica.addRow(1, spMetodologiaLabel, spMetodologia);
                break;
            case "Monografia":
                gridEspecifica.addRow(0, spTipoLabel, spTipo);
                gridEspecifica.addRow(1, spOrientadorLabel, spOrientador);
                gridEspecifica.addRow(2, spCursoLabel, spCurso);
                gridEspecifica.addRow(3, spAnoLabel, spAno);
                gridEspecifica.addRow(4, spNumPagsLabel, spNumPags);
                gridEspecifica.addRow(5, spResumoLabel, spResumo);
                gridEspecifica.addRow(6, spAbstractLabel, spAbstract);
                break;
            case "Palestra":
                gridEspecifica.addRow(0, spCurriculoLabel, spCurriculo);
                break;
            case "Relatório Técnico":
                gridEspecifica.addRow(0, spAnoLabel, spAno);
                gridEspecifica.addRow(1, spNumPagsLabel, spNumPags);
                gridEspecifica.addRow(2, spResumoLabel, spResumo);
                gridEspecifica.addRow(3, spAbstractLabel, spAbstract);
                break;
            case "Resumo":
                //resumo nao tem campos específicos, mas pelo bem da
                //consistência, merece um lugarzinho no switch
                break;
        }
        btnConfirmar.setOnAction(e -> {
            try {
                insert();
            } catch (HibernateException | FormularioException ex) {
                InterfaceUtil.erro(ex.getMessage());
            }
        });
    }

    private void removeSpecifics() {
        gridEspecifica.getChildren().clear();
        vboxTop.getChildren().removeAll(
                gridCientifica,
                gridApresentacao,
                gridEspecifica);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //popular as choiceboxex
        choiceBoxSituacao.setItems(FXCollections.observableArrayList(Situacao.values()));
        spTipo.setItems(FXCollections.observableArrayList(Tipo.values()));
        //remove todos quando fecha a janela
//        vboxTop.getScene().getWindow().setOnHiding(e -> removeSpecifics());
        init();
    }

    private void insert() throws HibernateException, FormularioException {
        String errorMsg = methods.form(sub, textFieldTitulo, choiceBoxSituacao, autores, textAreaResumo, textAreaAbstract, sliderDuracao, spRecursos, spMetodologia, spCurriculo, instituicoes, palavraschave, spResumo, spAbstract, spTipo, spOrientador, spCurso, spAno, spNumPags);

        if (errorMsg.equals("")) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            session.save(sub);

            session.getTransaction().commit();
            session.close();

            InterfaceUtil.sucesso("A submissão\n\n(" + sub.getClass().getSimpleName() + ") "
                    + sub.getTitulo() +
                    "\n\nfoi inserida com sucesso.");
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