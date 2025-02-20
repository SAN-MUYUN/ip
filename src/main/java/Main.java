import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import muyunbot.MuyunBot;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private MuyunBot bot = new MuyunBot();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDuke(bot);
            stage.setTitle("MuyunBot");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
