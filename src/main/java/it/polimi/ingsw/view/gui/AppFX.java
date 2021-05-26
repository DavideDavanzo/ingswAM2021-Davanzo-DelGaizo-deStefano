package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.view.gui.scene.MenuSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCombination;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class AppFX extends Application {

    @Override
    public void start(Stage primaryStage) {

        List<String> parameters = getParameters().getRaw();
        GuiView guiView;

        try {
            Socket socket = new Socket(parameters.get(0), Integer.parseInt(parameters.get(1)));
            guiView = new GuiView(new SocketHandler(socket));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/menu_scene.fxml"));

        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        MenuSceneController controller = loader.getController();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1280d);
        primaryStage.setHeight(720d);
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Maestri Del Rinascimento");
        primaryStage.show();

    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}

