package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.observingPattern.Observer;
import it.polimi.ingsw.view.gui.scene.GenericSceneController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

public class SceneController extends Observable {

    private static Scene activeScene;
    private static GenericSceneController activeSceneController;

    public static <T> T changeRootPane(Event event, String fxml) {
        Scene currentScene = ((Node) event.getSource()).getScene();
        return changeRootPane(currentScene, fxml);
    }

    public static <T> T changeRootPane(Scene scene, String fxml) {
        T controller = null;

        try {

            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController();

            activeSceneController = (GenericSceneController) controller;
            activeScene = scene;
            activeScene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return controller;
    }



}
