package utils;

import UI.indexController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class InterfaceUtil extends Application {
    public static Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        indexController.showView();
        stage.setResizable(false);
        stage.show();
        center();
    }

    public static void center() {
        Rectangle2D monitor = Screen.getPrimary().getVisualBounds();
        stage.setX((monitor.getWidth() - stage.getWidth()) / 2);
        stage.setY((monitor.getHeight() - stage.getHeight()) / 2);
    }

    /**
     * Mostra o diálogo de erro ao usuário
     *
     * @param texto o corpo da mensagem
     */
    public static void erro(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(texto);
        alert.show();
    }

    /**
     * Mostra o diálogo de sucesso ao usuário
     *
     * @param texto o corpo da mensagem
     */
    public static void sucesso(String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(texto);
        alert.show();
    }
}
