package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.scene.GenericSceneController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Implements the controller of a generic scene
 */
public class SceneController {

    private static GenericSceneController activeSceneController;
    private static Stage stage;
    private static Scene activeScene;

    public static void changeScene(GuiView gui, String fxml) {

        if(gui == null)
            System.out.println("GUI NULL in changeScene");

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

    public static void changeScene(GuiView gui, String fxml, Runnable r) {
        changeScene(gui, fxml);
        Platform.runLater(r);
    }

    public static void changeScene(GuiView gui, GenericSceneController controller, String fxml) {
        try {

            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));

            loader.setController(controller);
            activeSceneController = controller;
            activeSceneController.setGui(gui);
            Parent root = loader.load();

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