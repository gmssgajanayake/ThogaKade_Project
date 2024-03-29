package lk.ijse.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.bo.BoFactory;
import lk.ijse.pos.bo.custom.impl.SystemUserBoImpl;
import lk.ijse.pos.dto.SystemUserDto;
import lk.ijse.pos.util.SecurityConfig;

import java.io.IOException;
import java.util.Objects;

import static javafx.scene.control.ButtonType.*;

public class LoginForm {
    public AnchorPane loginFormContainer;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public static SystemUserDto lastSystemUser;
    public final SystemUserBoImpl systemUserBo= BoFactory.getInstance().getBo(BoFactory.BoType.SYSTEM_USER);

    public void signUpOnAction(ActionEvent actionEvent) {
        try {
            setUI("SignUpForm","Sign Up");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUI(String fileName,String title) throws IOException {
        Stage stage= (Stage) loginFormContainer.getScene().getWindow();
        stage.setScene(
                new Scene(
                        FXMLLoader.load(
                                Objects.requireNonNull(getClass().getResource("../view/" + fileName + ".fxml"))
                        )
                )
        );
        stage.setTitle(title);
        stage.centerOnScreen();
    }

    public void loginButtonOnAction(ActionEvent actionEvent) {
        try {
            SystemUserDto systemUserDto=Objects.requireNonNull(systemUserBo.getSystemUser(txtEmail.getText()));
            if ( /*Decrypts encrypted password*/SecurityConfig.decrypt(systemUserDto.getPassword(),SecurityConfig.holdingSecretKey)
                    .equalsIgnoreCase(txtPassword.getText())) {
                lastSystemUser=systemUserDto;
                setUI("Dashboard", "Dashboard");
            } else {
                new Alert(Alert.AlertType.ERROR, "Your email or password is wrong !!!", OK).show();
            }
        }catch (Exception ignored){
            new Alert(
                    Alert.AlertType.ERROR,(ignored.getMessage()==null ? "Your email or password is wrong !!!":""), OK).show();
        }
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        loginButtonOnAction(actionEvent);
    }

    public void forgotPasswordOnAction(ActionEvent actionEvent) {
        try {
            setUI("PasswordReset","Reset Password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

