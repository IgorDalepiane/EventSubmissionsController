package UI.aut;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    //table lateral direita
    @FXML
    private TableView<Submissao> tableRight;
    @FXML
    private TableColumn<Submissao, String> tableColumnTipo;
    @FXML
    private TableColumn<Submissao, String> tableColumnTitulo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnUniversidade.setCellValueFactory(new PropertyValueFactory<>("universidade"));
        tableLeft.setItems(FXCollections.observableArrayList(listar()));
        //listener de clicks
        tableLeft.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> tableRight(newValue));
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
        AnchorPane anchor = FXMLLoader.load(autorController.class.getResource("/autorInsert.fxml"));

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Autor");

        Scene scene = new Scene(anchor);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

        tableLeft.setItems(FXCollections.observableArrayList(listar()));
    }

    public void alterarDialog(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/autorUpdate.fxml"));

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
