package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Interface used to implements all scenes' controllers
 */

public interface GenericSceneController {
    void setGui(GuiView gui);
}
