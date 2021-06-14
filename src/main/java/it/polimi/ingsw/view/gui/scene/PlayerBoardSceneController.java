package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.view.gui.GuiView;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Locale;
import java.util.Objects;

public class PlayerBoardSceneController implements GenericSceneController{

    private GuiView gui;

    @FXML
    private Button backButton;

    @FXML
    private ImageView pos0, pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, pos9, pos10, pos11, pos12, pos13, pos14, pos15, pos16, pos17, pos18, pos19, pos20, pos21, pos22, pos23, pos24;

    private ImageView[] positions = new ImageView[25];

    @FXML
    private ImageView shelf1, shelf2pos1, shelf2pos2, shelf3pos1, shelf3pos2, shelf3pos3;

    @FXML
    private ImageView slot1lvl1, slot1lvl2, slot1lvl3, slot2lvl1, slot2lvl2, slot2lvl3, slot3lvl1,slot3lvl2,slot3lvl3;

    @FXML
    private ImageView coinCoffer, shieldCoffer, servantCoffer, stoneCoffer;

    @FXML
    private ImageView popeToken1, popeToken2, popeToken3;

    public void initialize() {
        Platform.runLater(this::initAll);
    }

    private void initAll() {
        initTrack();
        backButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::backButtonClick);

        positions[gui.getClientModel().getFaithTrack().getCurrentPositionAsInt()].setImage(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/thumbnail_faith.png")))
        );
        popeToken1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/quadrato giallo.png"))));
        popeToken2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/quadrato arancione.png"))));
        popeToken3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/quadrato rosso.png"))));

        if(!gui.getClientModel().getWarehouse().getFirstShelf().isEmpty()) shelf1.setImage(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/resources/" + gui.getClientModel().getWarehouse().getFirstShelf().getShelfResource().getClass().getSimpleName().toLowerCase() + ".png")))
        );

    }

    public void backButtonClick(Event event){
        backButton.setDisable(true);
        Platform.runLater(() -> SceneController.changeScene(gui, "command_scene.fxml"));
    }

    private void initTrack() {
        positions[0] = pos0;
        positions[1] = pos1;
        positions[2] = pos2;
        positions[3] = pos3;
        positions[4] = pos4;
        positions[5] = pos5;
        positions[6] = pos6;
        positions[7] = pos7;
        positions[8] = pos8;
        positions[9] = pos9;
        positions[10] = pos10;
        positions[11] = pos11;
        positions[12] = pos12;
        positions[13] = pos13;
        positions[14] = pos14;
        positions[15] = pos15;
        positions[16] = pos16;
        positions[17] = pos17;
        positions[18] = pos18;
        positions[19] = pos19;
        positions[20] = pos20;
        positions[21] = pos21;
        positions[22] = pos22;
        positions[23] = pos23;
        positions[24] = pos24;
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }
}

