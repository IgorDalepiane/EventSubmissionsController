package UI.aut;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import submissao.embeddables.Autor;
import utils.HibernateUtil;
import utils.InterfaceUtil;

public class autorInsertController {
    @FXML private TextField textFieldNome;
    @FXML private TextField textFieldUniversidade;
    @FXML private Button closeButton;

    public void inserir(ActionEvent actionEvent) {
        Autor a = new Autor();
        try {
            a.setNome(textFieldNome.getText());
            a.setUniversidade(textFieldUniversidade.getText());

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            session.save(a);

            session.getTransaction().commit();
            session.close();

            closeDialog(null);
        } catch (Exception e) {
            InterfaceUtil.erro(e.getMessage());
        }
    }

    public void closeDialog(ActionEvent actionEvent) {
        Stage s = (Stage) closeButton.getScene().getWindow();
        s.close();
    }
}
