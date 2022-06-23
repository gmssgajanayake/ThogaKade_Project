package lk.ijse.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.bo.BoFactory;
import lk.ijse.pos.bo.custom.impl.SystemUserBoImpl;
import lk.ijse.pos.dto.SystemUserDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class PasswordResetForm {

    public PasswordField txtPassword;
    public PasswordField txtPassword1;
    public AnchorPane resetPasswordContainer;
    public TextField txtEmail;
    public final SystemUserBoImpl systemUserBo= BoFactory.getInstance().getBo(BoFactory.BoType.SYSTEM_USER);


    public void resetPasswordButtonOnAction(ActionEvent actionEvent) {
        if (
                txtPassword.getText().equalsIgnoreCase(txtPassword1.getText()) &&
                       ! (txtPassword.getText().equalsIgnoreCase("") ||
                                txtPassword.getText().equalsIgnoreCase(""))){
            try {
                SystemUserDto systemUser = systemUserBo.getSystemUser(txtEmail.getText());
                if(systemUser!=null && !txtEmail.getText().equalsIgnoreCase("") ){
                    systemUser.setPassword(txtPassword.getText());
                    systemUserBo.resetPassword(systemUser);
                    new Alert(Alert.AlertType.INFORMATION,"Successfully changed your password").show();
                }else{
                    new Alert(Alert.AlertType.WARNING,"This system user cannot be find").show();
                }

            } catch (SQLException | ClassNotFoundException e) {
               new Alert(Alert.AlertType.ERROR,e.getMessage(),ButtonType.CANCEL).show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Passwords are not matched", ButtonType.CANCEL).show();
        }
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        resetPasswordButtonOnAction(actionEvent);
    }

    public void logInUpOnAction(ActionEvent actionEvent)  {
        Stage stage= (Stage) resetPasswordContainer.getScene().getWindow();
        try {
            stage.setScene(
                    new Scene(
                            FXMLLoader.load(
                                    Objects.requireNonNull(getClass().getResource("../view/LoginForm.fxml"))
                            )
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }
}
