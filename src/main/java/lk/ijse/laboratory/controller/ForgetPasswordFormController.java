package lk.ijse.laboratory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.ForgotPasswordBO;
import lk.ijse.laboratory.Dto.userDto;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import static lk.ijse.laboratory.Util.EmailController.SendEmail;
import static lk.ijse.laboratory.controller.NewAccountFormController.logOtp;


public class ForgetPasswordFormController {

    public AnchorPane pane;
    public TextField txtEmail;
    public static userDto ud;
    ForgotPasswordBO bo = (ForgotPasswordBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.FORGOTPW);
    public void onActionSendBtn(ActionEvent event) throws IOException, SQLException, MessagingException, ClassNotFoundException {
        String email = txtEmail.getText();


        if(email.length() > 5) {
           ud = bo.SearchUser("email",email);
            int body =generateOTP();
            logOtp = body;
            String name = ud.getUserName();

            SendEmail(name,email,"Verification", String.valueOf(body),8);

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/verification_form.fxml"));
            Stage stage = (Stage) pane.getScene().getWindow();

            stage.setScene(new Scene(anchorPane));
            stage.centerOnScreen();
        }else{
            new Alert(Alert.AlertType.ERROR, "Enter Your Email Address First !!!").show();
        }
    }

    private int generateOTP() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int rand = random.nextInt(100000,999999);
        return rand;
    }
}
