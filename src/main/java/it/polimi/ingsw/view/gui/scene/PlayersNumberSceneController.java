package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GuiView;

public class PlayersNumberSceneController implements GenericSceneController{

    private GuiView gui;

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }
}