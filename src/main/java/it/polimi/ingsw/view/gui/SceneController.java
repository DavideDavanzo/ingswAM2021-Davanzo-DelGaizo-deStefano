package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.scene.GenericSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private static GenericSceneController activeSceneController;
    private static Stage stage;
    private static Scene activeScene;

    public static void changeScene(GuiView gui, String fxml) {
        GenericSceneController controller;

        try {

            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController();


            activeSceneController = controller;
            activeSceneController.setGui(gui);

            activeScene = new Scene(root);

            stage.setScene(activeScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GenericSceneController getActiveSceneController() {
        return activeSceneController;
    }

    public static Stage getStage() {
        return stage;
    }

    public static Scene getActiveScene() {
        return activeScene;
    }

    public static void setActiveSceneController(GenericSceneController activeSceneController) {
        SceneController.activeSceneController = activeSceneController;
    }

    public static void setStage(Stage stage) {
        SceneController.stage = stage;
    }

    public static void setActiveScene(Scene activeScene) {
        SceneController.activeScene = activeScene;
    }
}