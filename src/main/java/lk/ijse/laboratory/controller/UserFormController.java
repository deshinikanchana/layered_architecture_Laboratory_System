package lk.ijse.laboratory.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.UserBO;
import lk.ijse.laboratory.Dto.Tm.userTm;
import lk.ijse.laboratory.Dto.userDto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserFormController  implements Initializable {


    public TableView <userTm> tblUsers;
    public TableColumn <?, ?> colUserId;
    public TableColumn <?, ?> colUserName;
    public TableColumn <?, ?>  colUserType;
    public TextField txtUserId;
    public TextField txtUserName;
    public TextField txtPassword;
    public TextField txtConfirmPw;

    public TextField txtEmail;
    public CheckBox cbShowPW;
    public PasswordField pwfPassword;
    public PasswordField pwfConfirmPw;
    public ChoiceBox <String> cbUserType;
    private String [] Types = {"Admin","User"};

    @FXML
    private AnchorPane root;
    UserBO bo = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbUserType.getItems().addAll(Types);
        loadAllUsers();
        setCellValueFactory();
        generateNextUserId();

    }
    private void loadAllUsers() {
        ObservableList<userTm> obList = FXCollections.observableArrayList();

        try {
            List<userDto> dtoList = bo.loadAllUsers();

            for(userDto dto : dtoList) {
                obList.add(
                        new userTm(
                              dto.getUserId(),
                                dto.getUserName(),
                                dto.getUserType()
                        )
                );
            }

            tblUsers.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextUserId() {
        try {
            String id = bo.generateNextUserId();
            txtUserId.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colUserType.setCellValueFactory(new PropertyValueFactory<>("userType"));
    }

    public void onActionBackBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/adminDashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void clearFields(){
        txtUserId.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        txtConfirmPw.setText("");
        pwfConfirmPw.setText("");
        pwfPassword.setText("");
        txtEmail.setText("");
        cbShowPW.setSelected(false);
        cbUserType.setValue("");
        generateNextUserId();

    }
    public void onActionSearchTxtUserId(ActionEvent event) {
        String id= txtUserId.getText();

        try {
            userDto dto = bo.SearchUser("userId",id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "User not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillFields(userDto dto) {
        txtUserId.setText(dto.getUserId());
        txtPassword.setText(" ");
        txtConfirmPw.setText(" ");
        txtUserName.setText(dto.getUserName());
        cbUserType.setValue(dto.getUserType());
        pwfPassword.setText(" ");
        pwfConfirmPw.setText(" ");
        txtEmail.setText(dto.getEmail());

    }


    public void onActionSaveBtn(ActionEvent event) {
        String pws = txtPassword.getText();
        String pwh = pwfPassword.getText();
        String conpws = txtConfirmPw.getText();
        String conpwh = pwfConfirmPw.getText();

        pws = pwh;
        conpws = conpwh;

            if(txtPassword.getText() != null || pwfPassword.getText()!= null) {
                if (conpws.equals(pws) || conpwh.equals(pwh)) {

                    String name = txtUserName.getText();
                    String userId = txtUserId.getText();
                    String type = cbUserType.getValue();
                    String pw = conpws;
                    String email = txtEmail.getText();

                    var dto = new userDto(userId, name, type, pw,email);

                    try {
                        boolean isSaved = bo.SaveUser(dto);
                        if (isSaved) {
                            new Alert(Alert.AlertType.CONFIRMATION, "New User saved Successfully !!!").show();
                            loadAllUsers();
                            clearFields();
                            return;
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                        return;
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                new Alert(Alert.AlertType.ERROR, "Passwords Are Not Matched !!!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Fill Fields First !").show();
            }
    }

    public void onActioncbShowPw(ActionEvent event) {
        if(cbShowPW.isSelected()){
            txtPassword.setText(pwfPassword.getText());
            pwfPassword.setVisible(false);
            txtPassword.setVisible(true);

            txtConfirmPw.setText(pwfConfirmPw.getText());
            pwfConfirmPw.setVisible(false);
            txtConfirmPw.setVisible(true);

            return;
        }
        pwfPassword.setText(txtPassword.getText());
        txtPassword.setVisible(false);
        pwfPassword.setVisible(true);

        pwfConfirmPw.setText(txtConfirmPw.getText());
        txtConfirmPw.setVisible(false);
        pwfConfirmPw.setVisible(true);
    }

    public void onActionTxtUserName(ActionEvent event) {
        txtEmail.requestFocus();
    }

    public void onActionPw(ActionEvent event) {
        txtConfirmPw.requestFocus();
    }

    public void onActionTxtEmail(ActionEvent event) {
        txtPassword.requestFocus();
    }
}