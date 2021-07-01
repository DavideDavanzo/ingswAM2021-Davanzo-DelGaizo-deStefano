package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.messages.LoginRequest;
import it.polimi.ingsw.observingPattern.Observable;
import it.polimi.ingsw.view.gui.GuiView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements the login scene
 */

public class LoginSceneController implements GenericSceneController {

    private GuiView gui;

    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Label invalidUserLabel;

    @FXML
    public void initialize() {
        loginButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::loginButtonClick);
    }

    /**
     * Button clicked after typing the nickname
     */
    private void loginButtonClick(Event event) {
        loginButton.setDisable(true);
        String nickname = usernameTextField.getText();
        if(isValid(nickname))
            gui.onLoginRequest(new LoginRequest(nickname));
        else
            reAskLogin();
    }

    /**
     * If its nickname is not valid, the player is asked to insert it again
     */
    public void reAskLogin() {
        usernameTextField.clear();
        loginButton.setDisable(false);
        invalidUserLabel.setVisible(true);
    }

    /**
     * Verifies that the chosen nickname is valid
     * @param nickname
     * @return
     */
    private boolean isValid(String nickname) {

        if(nickname == null || nickname.equals("") || nickname.contains(" ")) return false;

        String regex = "^[A-Za-z]\\w{2,19}$";
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(nickname);

        return m.matches();

    }

    @Override
    public void setGui(GuiView gui) {
        this.gui = gui;
    }

}
