package lk.ijse.pos.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;


public class Dashboard {

    public AnchorPane dashboardContainer;
    public Label timeLabel;
    public Label dateLabel;
    public Label systemUserNameTxt;
    public Button customerManagerButton;
    public Button itemManagerButton;
    public Button orderDetailButton;
    public Button placeOrderButton;

    public void initialize(){
        timeSet();
        dateSet();
        setSystemUserDetails();
    }

    private void dateSet() {
        dateLabel.setText(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
    }

    private void timeSet() {
        final DateFormat dateFormat = DateFormat.getDateInstance();
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        final Calendar calendar = Calendar.getInstance();
                        timeLabel.setText(
                                calendar.getTime().getHours() +
                                        " : "
                                        + calendar.getTime().getMinutes() +
                                        " : "
                                        + calendar.getTime().getSeconds());
                    }
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void logOutOnAction(ActionEvent actionEvent) {
        try {
            setUI("LoginForm","Login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setUI(String fileName,String title) throws IOException {
        Stage stage= (Stage) dashboardContainer.getScene().getWindow();
        stage.setScene(
                new Scene(
                        FXMLLoader.load(
                                Objects.requireNonNull(getClass().getResource("../view/"+fileName+".fxml"))
                        )
                )
        );
        stage.setTitle(title);
        stage.centerOnScreen();
    }

    public void setSystemUserDetails(){
        systemUserNameTxt.setText(LoginForm.lastSystemUser.getName());
    }

    public void customerManagerButtonOnAction(ActionEvent actionEvent) {
        try {
            setUI("CustomerManagerForm","Customer Manager");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void itemManagerButtonOnAction(ActionEvent actionEvent) {
        try {
            setUI("ItemManagerForm","Item Manager");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void orderDetailButtonOnAction(ActionEvent actionEvent) {
    }

    public void placeOrderButtonOnAction(ActionEvent actionEvent) {
    }
}
