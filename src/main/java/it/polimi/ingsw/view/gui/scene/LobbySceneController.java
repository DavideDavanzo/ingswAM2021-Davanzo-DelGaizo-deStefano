package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GuiView;

public class LobbySceneController implements GenericSceneController {

    GuiView gui;

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }
}
