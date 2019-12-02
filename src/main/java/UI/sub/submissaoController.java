package UI.sub;


import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import submissao.Submissao;
import utils.HibernateUtil;
import utils.InterfaceUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class submissaoController implements Initializable {
    private static Scene scene;

    //tabela lateral esquerda
    @FXML
    private TableView<Submissao> tableLeft;
    @FXML
    private TableColumn<Submissao, String> tableColumnTipo;
    @FXML
    private TableColumn<Submissao, String> tableColumnTitulo;

    //pesquisa
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField textField;

    private FilteredList<Submissao> flSub;

    //info
    @FXML
    private TextFlow flowInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //carrega os valores na tabela à esquerda
        refresh();

        tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tableColumnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        //listener de clicks na table esquerda
        tableLeft.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> textflow(newValue));

        initPesquisa();
    }

    private void refresh() {
        flSub = new FilteredList<>(FXCollections.observableArrayList(listar()), p -> true);
        tableLeft.setItems(flSub);
    }

    private void initPesquisa() {
        choiceBox.getItems().setAll("Tipo", "Título", "Autor");
        choiceBox.setValue("Título");

        textField.setPromptText("Pesquise aqui!");
        textField.setOnKeyReleased(keyEvent -> {
            switch (choiceBox.getValue()) {
                case "Tipo":
                    flSub.setPredicate(sub -> sub.getClass().getSimpleName()
                            .toLowerCase()
                            .contains(textField.getText().toLowerCase().trim()));
                    break;
                case "Título":
                    System.out.println(keyEvent.getCode());
                    flSub.setPredicate(sub -> sub.getTitulo()
                            .toLowerCase()
                            .contains(textField.getText().toLowerCase().trim()));
                    break;
                case "Autor":
                    flSub.setPredicate(sub -> sub.getAutoresInline()
                            .toLowerCase()
                            .contains(textField.getText().toLowerCase().trim()));
                    break;
            }
        });

        //limpa o filtro quando troca o item da choicebox
        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                textField.setText("");
                flSub.setPredicate(null);
            }
        });
    }

    private void textflow(Submissao sub) {
        if (sub != null) {
            flowInfo.getChildren().clear();
            Text text = new Text(sub.toString());
            text.setStyle("-fx-font: 14 arial;");

            flowInfo.getChildren().add(text);
        }
    }

    public void inserirDialog(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = FXMLLoader.load(submissaoController.class.getResource("/sub/submissaoTipo.fxml"));

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Escolha o tipo de submissão");

        Scene scene = new Scene(anchor);
        dialogStage.setScene(scene);
        dialogStage.show();

        refresh();
    }

    public void alterarDialog(ActionEvent actionEvent) throws IOException {
        Submissao selecionado = tableLeft.getSelectionModel().getSelectedItem();

        if (selecionado != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/submissaoUpdate.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edição de " + selecionado.getClass().getSimpleName());

            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            submissaoUpdateController upCtrl = loader.getController();
            upCtrl.init(selecionado);
            dialogStage.showAndWait();
            refresh();
        } else
            InterfaceUtil.erro("Selecione uma submissão.");

        refresh();
    }

    public void removerDialog(ActionEvent actionEvent) {
        try {
            Submissao sub = tableLeft.getSelectionModel().getSelectedItem();
            if (sub != null) {
                if (InterfaceUtil.confirma("Tem certeza que deseja apagar a submissão?\n" + sub.getTitulo())
                        .get() == ButtonType.OK) {
                    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                    session.beginTransaction();

                    session.remove(sub);

                    session.getTransaction().commit();
                    session.close();

                    refresh();
                    InterfaceUtil.sucesso("Submissão apagada com sucesso.");
                }
            } else
                InterfaceUtil.erro("Selecione uma submissão.");

        } catch (HibernateException e) {
            InterfaceUtil.erro(e.getMessage());
        }
    }

    public List<Submissao> listar() {
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            List result = session.createQuery("from Submissao").list();

            session.getTransaction().commit();
            session.close();

            return result;
        } catch (HibernateException e) {
            InterfaceUtil.erro(e.getMessage());
            return null;
        }
    }

    /**
     * Carrega o arquivo fxml e define a scene (View)
     *
     * @throws IOException
     */
    public static void showView() throws IOException {
        Parent root = FXMLLoader.load(submissaoController.class.getResource("/submissao.fxml"));
        if (scene == null)
            scene = new Scene(root, 600, 370);
        InterfaceUtil.stage.setTitle("Gerenciador de Submissões");
        InterfaceUtil.stage.setScene(scene);
        InterfaceUtil.center();
    }
}