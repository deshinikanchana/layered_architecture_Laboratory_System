package lk.ijse.laboratory.controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.AdminBO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static lk.ijse.laboratory.controller.LoginFormController.newDto;

public class AdminDashboardFormController {
    public Label txtTodayempCount;
    public Label txtTodayTestCount;
    public Label lblTime;
    public Label lblDate;
    public Label txtPendingOrders;
    public Label txtStockWarnings;
    public Label txtPendingReports;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtUserType;
    @FXML
    private AnchorPane root;
   AdminBO bo = (AdminBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ADMIN);

  public void initialize() throws SQLException, ClassNotFoundException {
      addUserDetails();
      setDashBoardDetails();
  }

    private void setDashBoardDetails() throws SQLException, ClassNotFoundException {

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        };
        timer.start();
        lblDate.setText(String.valueOf(LocalDate.now()));
        int emp = bo.getDailyEmployeeCount();
        txtTodayempCount.setText(String.valueOf(emp));
        int test = bo.getCount();
        txtTodayTestCount.setText(String.valueOf(test));
        int od = bo.getOrderCount();
        txtPendingOrders.setText(String.valueOf(od));
        int stock = bo.warningStocks();
        txtStockWarnings.setText(String.valueOf(stock));
        int rep = bo.getPendingReports();
        txtPendingReports.setText(String.valueOf(rep));
    }

    @FXML
    public void onActionLogOutButton(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/welcomeScreen_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionEmployeesBtn(ActionEvent event) throws IOException {

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/employee_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionTestsBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/test_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionStockBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/stocks_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionUsersBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/user_form.fxml"));
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


    public void addUserDetails() {
        txtUserType.setText(newDto.getUserType());
        txtUserName.setText(newDto.getUserName());
    }
}
