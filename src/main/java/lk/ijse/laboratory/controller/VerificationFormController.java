package lk.ijse.laboratory.controller;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.VerificationBO;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import static javafx.fxml.FXMLLoader.load;
import static lk.ijse.laboratory.controller.NewAccountFormController.logOtp;
import static lk.ijse.laboratory.controller.NewAccountFormController.user;
import static lk.ijse.laboratory.controller.WelcomeScreenFormController.verify;


public class VerificationFormController {

    public AnchorPane pane;
    public TextField txtOtp;
    VerificationBO bo = (VerificationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.VERIFICATION);

    public void onActionVerifyBtn(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        if (txtOtp.getText().length() != 0) {

            if(verify == 1) {

                int otp = Integer.parseInt(txtOtp.getText());
                Date dt = Date.valueOf(LocalDate.now());
                Time tm = Time.valueOf(LocalTime.now());
                if (otp == logOtp) {
                    boolean ok = bo.saveTempUser(user,dt,tm);
                    if(ok) {
                        AnchorPane anchorPane = load(getClass().getResource("/lk.ijse.laboratory/assests/registerSuccess_form.fxml"));
                        Stage stage = (Stage) pane.getScene().getWindow();

                        stage.setScene(new Scene(anchorPane));
                        stage.centerOnScreen();
                        return;
                    }
                    new Alert(Alert.AlertType.ERROR, "Something went Wrong !!!").show();
                    return;
                } else{
                new Alert(Alert.AlertType.ERROR, "OTP Code Doesn't Match !!!").show();
                AnchorPane anchorPane = load(getClass().getResource("/lk.ijse.laboratory/assests/newAccount_form.fxml"));
                Stage stage = (Stage) pane.getScene().getWindow();

                stage.setScene(new Scene(anchorPane));
                stage.centerOnScreen();
                return;
                }

            } else if (verify == 2) {
                int otp = Integer.parseInt(txtOtp.getText());
                if (otp == logOtp) {

                    AnchorPane anchorPane = load(getClass().getResource("/lk.ijse.laboratory/assests/resetPassword_form.fxml"));
                    Stage stage = (Stage) pane.getScene().getWindow();

                    stage.setScene(new Scene(anchorPane));
                    stage.centerOnScreen();

                    return;
                }
                new Alert(Alert.AlertType.ERROR, "OTP Code Doesn't Match !!!").show();
                onActionBackBtn(event);
                return;
            }
        }
        new Alert(Alert.AlertType.ERROR, "Enter The OTP Code First !!!").show();
    }
    public void onActionResesndCode(ActionEvent event) throws IOException {
        onActionBackBtn(event);
    }

    public void onActionBackBtn(ActionEvent event) throws IOException {
        if (verify == 1) {
            AnchorPane anchorPane = load(getClass().getResource("/lk.ijse.laboratory/assests/newAccount_form.fxml"));
            Stage stage = (Stage) pane.getScene().getWindow();

            stage.setScene(new Scene(anchorPane));
            stage.centerOnScreen();
        } else {
            AnchorPane anchorPane = load(getClass().getResource("/lk.ijse.laboratory/assests/forgetPassword_form.fxml"));
            Stage stage = (Stage) pane.getScene().getWindow();

            stage.setScene(new Scene(anchorPane));
            stage.centerOnScreen();
        }
    }
}
