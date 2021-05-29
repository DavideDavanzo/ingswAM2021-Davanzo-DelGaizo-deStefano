package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.SocketHandler;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.scene.MenuSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class AppFX extends Application {

    @Override
    public void start(Stage primaryStage) {

        List<String> parameters = getParameters().getRaw();
        GuiView guiView = null;

        try {

            Socket socket = new Socket(parameters.get(0), Integer.parseInt(parameters.get(1)));
            SocketHandler handler = new SocketHandler(socket);
            guiView = new GuiView(handler);
            handler.addObserver(guiView);
            new Thread(handler).start();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        SceneController.setStage(primaryStage);
        SceneController.changeScene(guiView, "menu_scene.fxml");

        primaryStage.setResizable(false);
        primaryStage.setTitle("Maestri Del Rinascimento");
        primaryStage.show();
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}

