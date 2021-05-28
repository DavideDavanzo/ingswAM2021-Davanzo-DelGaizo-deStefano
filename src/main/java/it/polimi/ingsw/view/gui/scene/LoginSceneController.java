package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.messages.LoginRequest;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginSceneController implements GenericSceneController {

    private GuiView gui;

    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextField;

    @FXML
    public void initialize() {
        loginButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::loginButtonClick);
    }

    public void loginButtonClick(Event event) {
        loginButton.setDisable(true);
        String nickname = usernameTextField.getText();
        gui.onLoginRequest(new LoginRequest(nickname));
    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }
}
