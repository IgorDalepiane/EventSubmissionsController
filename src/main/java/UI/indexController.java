package UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import utils.InterfaceUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class indexController implements Initializable {
    private static Scene scene;
    //anchor pane principal
    @FXML
    private AnchorPane anchor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Carrega o arquivo fxml e define a scene (View)
     *
     * @throws IOException
     */
    public static void showView() throws IOException {
        Parent root = FXMLLoader.load(indexController.class.getResource("/index.fxml"));
        if (scene == null)
            scene = new Scene(root, 600, 400);
        InterfaceUtil.stage.setTitle("Gerenciador de Submissões");
        InterfaceUtil.stage.setScene(scene);
        InterfaceUtil.center();
    }


    public void handleAutor(ActionEvent actionEvent) throws IOException {
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/autor.fxml"));
        anchor.getChildren().setAll(root);
    }
}
