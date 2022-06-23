package lk.ijse.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.bo.BoFactory;
import lk.ijse.pos.bo.custom.impl.SystemUserBoImpl;
import lk.ijse.pos.dto.SystemUserDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class SignUpForm {

    public AnchorPane signUpContainer;
    public TextField txtEmail;
    public TextField txtName;
    public PasswordField txtPassword;
    public Button singUpButton;
    public CheckBox agreementCheckBox;
    public final SystemUserBoImpl systemUserBo= BoFactory.getInstance().getBo(BoFactory.BoType.SYSTEM_USER);

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        setUI("LoginForm","Login");
    }

    public void setUI(String fileName,String title) throws IOException {
        Stage stage= (Stage) signUpContainer.getScene().getWindow();
        stage.setScene(
                new Scene(
                        FXMLLoader.load(
                                Objects.requireNonNull(getClass().getResource("../view/" + fileName + ".fxml"))
                        )
                )
        );
        stage.setTitle(title);
    }

    public void signUpButtonOnAction(ActionEvent actionEvent) {
        try {
            if (systemUserBo.createSystemUser(
                    new SystemUserDto(
                            txtName.getText(),
                            txtEmail.getText().trim(),
                            txtPassword.getText().trim()
                    )
            )) {
                new Alert(Alert.AlertType.INFORMATION, "Successfully saved", ButtonType.CLOSE).show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something went wrong !!!", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            if (e.getMessage().startsWith("Duplicate")) {
                new Alert(Alert.AlertType.INFORMATION, "Your already registered here", ButtonType.CLOSE).show();
            } else {
                new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.CANCEL).show();
            }
        }
        
    }

    public void agreementOnAction(ActionEvent actionEvent) {
        singUpButton.setDisable(!agreementCheckBox.isSelected() ||
                txtName.getText().equalsIgnoreCase("") ||
                txtEmail.getText().equalsIgnoreCase("") ||
                txtPassword.getText().equalsIgnoreCase(""));
    }

    public void txtNameOnInput(KeyEvent keyEvent) {
        singUpButton.setDisable(!agreementCheckBox.isSelected() ||
                txtName.getText().equalsIgnoreCase("") ||
                txtEmail.getText().equalsIgnoreCase("") ||
                txtPassword.getText().equalsIgnoreCase(""));
    }

    public void txtPasswordOnInput(KeyEvent keyEvent) {
        txtNameOnInput(keyEvent);
    }

    public void txtEmailOnInput(KeyEvent keyEvent) {
        txtNameOnInput(keyEvent);
    }
}
