package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.scene.GenericSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    public static GenericSceneController activeSceneController;
    public static Stage stage;

    public static void changeScene(GuiView gui, String fxml) {
        GenericSceneController controller;

        try {

            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController();


            activeSceneController = controller;
            activeSceneController.setGui(gui);

            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}