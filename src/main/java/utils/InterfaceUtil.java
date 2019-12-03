package utils;

import UI.submissaoController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Optional;

public class InterfaceUtil extends Application {
    public static final Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        submissaoController.showView();
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
        alert.showAndWait();
    }

    /**
     * Mostra o diálogo de confirmação ao usuário e retorna o botão clicado
     *
     * @param texto o corpo da mensagem
     */
    public static Optional<ButtonType> confirma(String texto) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(texto);
        return alert.showAndWait();

    }
}
