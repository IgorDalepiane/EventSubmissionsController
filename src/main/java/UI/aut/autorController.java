package UI.aut;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import submissao.Submissao;
import submissao.embeddables.Autor;
import utils.HibernateUtil;
import utils.InterfaceUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class autorController implements Initializable {

    //tabela lateral esquerda
    @FXML
    private TableView<Autor> tableLeft;
    @FXML
    private TableColumn<Autor, String> tableColumnUniversidade;
    @FXML
    private TableColumn<Autor, String> tableColumnNome;

    //pesquisa
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField textField;

    private FilteredList<Autor> flAutor;

    //table lateral direita
    @FXML
    private TableView<Submissao> tableRight;
    @FXML
    private TableColumn<Submissao, String> tableColumnTipo;
    @FXML
    private TableColumn<Submissao, String> tableColumnTitulo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //carrega os valores na tabela à esquerda

        flAutor = new FilteredList<>(FXCollections.observableArrayList(listar()), p -> true);
        tableLeft.setItems(flAutor);

        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnUniversidade.setCellValueFactory(new PropertyValueFactory<>("universidade"));
        //listener de clicks na table esquerda
        tableLeft.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> tableRight(newValue));

        choiceBox.getItems().addAll("Nome", "Universidade");
        choiceBox.setValue("Nome");

        textField.setPromptText("Pesquise aqui!");
        textField.setOnKeyReleased(keyEvent -> {
            switch (choiceBox.getValue()) {
                case "Nome":
                    flAutor.setPredicate(aut -> aut.getNome()
                            .toLowerCase()
                            .contains(textField.getText().toLowerCase().trim()));
                    break;
                case "Universidade":
                    flAutor.setPredicate(aut -> aut.getUniversidade()
                            .toLowerCase()
                            .contains(textField.getText().toLowerCase().trim()));
                    break;
            }
        });

        //limpa o filtro quando troca o item da choicebox
        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                textField.setText("");
                flAutor.setPredicate(null);
            }
        });
    }

    /**
     * Mostra as informações detalhadas do registro selecionado na table à direita
     *
     * @param aut o registro selecionado
     */
    private void tableRight(Autor aut) {
        if (aut != null) {
            tableColumnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

            tableRight.setItems(FXCollections.observableArrayList(listarSubs(aut)));
        }
    }

    public void inserirDialog(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = FXMLLoader.load(autorController.class.getResource("/aut/autorInsert.fxml"));

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Autor");

        Scene scene = new Scene(anchor);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

        tableLeft.setItems(FXCollections.observableArrayList(listar()));
    }

    public void alterarDialog(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/aut/autorUpdate.fxml"));

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edição de Autor");

        Scene scene = new Scene(loader.load());
        dialogStage.setScene(scene);

        autorUpdateController upCtrl = loader.getController();
        Autor selecionado = tableLeft.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            upCtrl.initAutor(selecionado);
            dialogStage.showAndWait();
        } else {
            InterfaceUtil.erro("Selecione um autor");
        }

        tableLeft.setItems(FXCollections.observableArrayList(listar()));
    }

    public void removerDialog(ActionEvent actionEvent) {
        try {
            Autor a = tableLeft.getSelectionModel().getSelectedItem();
            if (a != null) {
                if (InterfaceUtil.confirma("Tem certeza que deseja apagar o autor?\n" + a.getNome() + " - " + a.getUniversidade())
                        .get() == ButtonType.OK) {
                    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                    session.beginTransaction();

                    session.remove(a);

                    session.getTransaction().commit();
                    session.close();

                    tableLeft.setItems(FXCollections.observableArrayList(listar()));
                    InterfaceUtil.sucesso("Autor apagado com sucesso.");
                }
            }
        } catch (HibernateException e) {
            InterfaceUtil.erro(e.getMessage());
        }
    }

    public List<Autor> listar() {
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            List result = session.createQuery("from Autor").list();

            session.getTransaction().commit();
            session.close();

            return result;
        } catch (HibernateException e) {
            InterfaceUtil.erro(e.getMessage());
            return null;
        }
    }

    public List<Submissao> listarSubs(Autor aut) {
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            List result = session.createQuery(
                    "SELECT sub FROM Submissao sub " +
                            "INNER JOIN sub.autores auts ON auts.id = " + aut.getId()).getResultList();

            session.getTransaction().commit();
            session.close();

            return result;
        } catch (HibernateException e) {
            InterfaceUtil.erro(e.getMessage());
            return null;
        }
    }
}
