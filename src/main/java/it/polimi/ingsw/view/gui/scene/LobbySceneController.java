package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GuiView;
import javafx.scene.control.Alert;

/**
 * Waiting room
 */
public class LobbySceneController implements GenericSceneController {

    GuiView gui;

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
