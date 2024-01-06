package lk.ijse.laboratory.controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.UserDashBoardBO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static lk.ijse.laboratory.controller.LoginFormController.newDto;

public class UserDashboardFormController {


    public AnchorPane root;
    public Label lblPendingPayments;
    public Label lblTime;
    public Label lblDate;
    public Label txtTodayempCount;
    public Label txtTodayTestCount;
    public Label txtPendingReports;
    @FXML
    private Label userTypeLabel;
    @FXML
    private Label userNameLabel;
    UserDashBoardBO bo = (UserDashBoardBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USERDB);

    public void initialize() throws SQLException, ClassNotFoundException {
       setUserDetails();
      setDashBordDetails();
    }

    private void setDashBordDetails() throws SQLException, ClassNotFoundException {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        };
        timer.start();
        lblDate.setText(String.valueOf(LocalDate.now()));
        int emp = bo.getDailyCount();
        txtTodayempCount.setText(String.valueOf(emp));
        int test = bo.getReportCount();
        txtTodayTestCount.setText(String.valueOf(test));
        int pay = bo.getPrescriptionCount();
        lblPendingPayments.setText(String.valueOf(pay));
        int rep = bo.getPendingReports();
        txtPendingReports.setText(String.valueOf(rep));
    }

    public void setUserDetails(){
        userTypeLabel.setText(newDto.getUserType());
        userNameLabel.setText(newDto.getUserName());
    }
    public void onActionPatientsBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/patient_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionReportsBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/reports_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionAttendanceBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/markAttendance_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionTestDetailsBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/testDetails_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionMyProfileBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/myProfile_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionLogOutButton(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/welcomeScreen_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }
}
