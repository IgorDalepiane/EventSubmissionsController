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

public class autorUpdateController {
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldUniversidade;
    @FXML
    private Button closeButton;

    private Autor a;

    void initAutor(Autor a) {
        this.a = a;
        textFieldNome.setText(a.getNome());
        textFieldUniversidade.setText(a.getUniversidade());
    }

    public void update(ActionEvent actionEvent) {
        try {
            a.setNome(textFieldNome.getText());
            a.setUniversidade(textFieldUniversidade.getText().toUpperCase());

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            session.update(a);

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
