package lk.ijse.laboratory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.LoginBO;
import lk.ijse.laboratory.Dto.userDto;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class LoginFormController {
    public static userDto newDto;
   @FXML
   public CheckBox CheckBoxShowPw;
    public Hyperlink HyperlinkForgetPw;
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private PasswordField pwFieldPw;

    @FXML
    private AnchorPane root;
    LoginBO bo = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    public void initialize() throws SQLException, ClassNotFoundException {
        Date today = Date.valueOf(LocalDate.now());
        Time just = Time.valueOf(LocalTime.now());

    List<userDto> userList =bo.searchNewUsers(today,just);

    for (userDto dto : userList) {
        String id = dto.getUserId();
        dto.setUserId(bo.generateNextUserId());
       boolean saved = bo.SaveUser(dto);
       if(saved) {
           dto.setUserId(id);
           boolean isDeleted =bo.deleteTempUsers(dto);
       }
    }
}

    @FXML
    public void OnActionLoginBtn(ActionEvent e) throws SQLException, IOException {
    String userName = txtUsername.getText();
    String pw = txtPassword.getText();
    String hidePw = pwFieldPw.getText();
    Date date = Date.valueOf(LocalDate.now());
    Time time = Time.valueOf(LocalTime.now());


    pw = hidePw;
    var dto = new userDto();
    dto.setUserName(userName);
    dto.setPassword(pw);
    if (dto.getUserName().length() > 0 && dto.getPassword().length() > 0) {
        try {
            newDto = bo.CheckCredential(dto);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }else {
        new Alert(Alert.AlertType.ERROR, "Invalid Username Or Password !!!").show();
    }
    if (newDto.getUserType() != null) {
        if (newDto.getUserType().equals("Admin")) {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/adminDashboard_form.fxml"));
            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(new Scene(anchorPane));
            stage.centerOnScreen();
        } else {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/userDashboard_form.fxml"));
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(anchorPane));
            stage.centerOnScreen();
        }
    }else{
        new Alert(Alert.AlertType.ERROR, "Invalid Username Or Password !!!").show();
    }
}

    @FXML
    public void onActionCheckBoxShowPw(ActionEvent event) {

        if(CheckBoxShowPw.isSelected()){
            txtPassword.setText(pwFieldPw.getText());
            pwFieldPw.setVisible(false);
            txtPassword.setVisible(true);

            return;
        }
        pwFieldPw.setText(txtPassword.getText());
        txtPassword.setVisible(false);
        pwFieldPw.setVisible(true);

    }

    public void onActionForgetPw(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/forgetPassword_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Reset Password");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void onActionUserName(ActionEvent event) {
        if (CheckBoxShowPw.isSelected()) {
            txtPassword.requestFocus();
        } else {
            pwFieldPw.requestFocus();
        }
    }

    public void onActionBackBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/welcomeScreen_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }
}
