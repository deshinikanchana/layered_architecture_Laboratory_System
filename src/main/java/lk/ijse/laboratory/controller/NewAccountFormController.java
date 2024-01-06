package lk.ijse.laboratory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.NewAccountBO;
import lk.ijse.laboratory.Dto.userDto;

import javax.mail.MessagingException;

import static lk.ijse.laboratory.Util.EmailController.SendEmail;
import static lk.ijse.laboratory.controller.WelcomeScreenFormController.verify;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class NewAccountFormController implements Initializable {
    public AnchorPane pane;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtPw;
    public TextField txtConfirmPw;
    public ChoiceBox <String> choicebxUserType;
    public PasswordField pwfPw;
    public PasswordField pwfConfirmPw;
    public CheckBox checkBoxPw;
    public static int logOtp;

    public static userDto user;
    private String [] Types = {"Admin","User"};

    NewAccountBO bo = (NewAccountBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.NEWACCOUNT);
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choicebxUserType.getItems().addAll(Types);
    }
    public void onActionCheckBoxPw(ActionEvent event) {
        if(checkBoxPw.isSelected()){
            txtPw.setText(pwfPw.getText());
            pwfPw.setVisible(false);
            txtPw.setVisible(true);

            txtConfirmPw.setText(pwfConfirmPw.getText());
            pwfConfirmPw.setVisible(false);
            txtConfirmPw.setVisible(true);

            return;
        }
        pwfPw.setText(txtPw.getText());
        txtPw.setVisible(false);
        pwfPw.setVisible(true);
        pwfConfirmPw.setText(txtConfirmPw.getText());
        txtConfirmPw.setVisible(false);
        pwfConfirmPw.setVisible(true);
    }

    public int generateOTP() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int rand = random.nextInt(100000,999999);
        return rand;
    }

    public void onActionBtnRegister(ActionEvent event) throws IOException, SQLException, MessagingException, ClassNotFoundException {
        verify =1;
        txtPw.setText(pwfPw.getText());
        txtConfirmPw.setText(pwfConfirmPw.getText());

            String name = txtName.getText();
            String type = (String) choicebxUserType.getValue();
            String pw = txtPw.getText();
            String Conpw = txtConfirmPw.getText();
            String email = txtEmail.getText();
            String userId = bo.generateNextUserId();

            user = new userDto(userId,name,type,pw,email);
            if(name != null && type != null && Conpw != null && email != null && pw != null) {
                if(pw.equals(Conpw)) {

                    int body =generateOTP();
                    logOtp = body;
                    SendEmail(name,email,"Verification", String.valueOf(body),1);
                    userDto dto = bo.getLastAdmin();
                        if(dto != null) {
                            SendEmail(dto.getUserName(), dto.getEmail(), "Alert", " ", 2);
                        }
                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/verification_form.fxml"));
                    Stage stage = (Stage) pane.getScene().getWindow();

                    stage.setScene(new Scene(anchorPane));
                    stage.centerOnScreen();



                }else {
                    new Alert(Alert.AlertType.ERROR, "Passwords not matched !!!").show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "All Fields Are Required !!!").show();
            }
        }
    }
