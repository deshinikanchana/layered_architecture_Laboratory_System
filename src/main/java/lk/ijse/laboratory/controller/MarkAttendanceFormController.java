package lk.ijse.laboratory.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.AttendanceBO;
import lk.ijse.laboratory.Dto.Tm.attendanceTm;
import lk.ijse.laboratory.Dto.attendanceDto;
import lk.ijse.laboratory.Dto.employeeDto;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;


public class MarkAttendanceFormController {
    public TableView<attendanceTm> tblAttendance;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colEmployeeId;
    public TableColumn<?,?> colEmployeeName;
    public TableColumn<?,?> colInTime;
    public TableColumn<?,?> colOutTime;
    public TableColumn<?,?> colStatus;
    public TextField txtEmpName;
    public JFXComboBox cmbEmpId;
    public CheckBox checkBoxIn;
    public CheckBox checkBoxOut;
    public TextField txtStatus;
    public JFXButton updateBtn;
    public Label lbaDate;
    @FXML
    private AnchorPane root;

    AttendanceBO bo = (AttendanceBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ATTENDANCE);


        public void initialize() throws ClassNotFoundException {
            loadEmployeeIds();
            setCellValueFactory();
            loadAllAttendance();
            setCurrentDate();
            boxes();
        }

    private void setCurrentDate() {
            Date date = Date.valueOf(LocalDate.now());
        lbaDate.setText(String.valueOf(date));
    }

    private void loadAllAttendance() throws ClassNotFoundException {
        ObservableList<attendanceTm> obList = FXCollections.observableArrayList();

        try {
            List<attendanceDto> dtoList = bo.loadAllAttendance();
            for(attendanceDto dto : dtoList) {
                employeeDto EmpName = bo.SearchEmployee("empId",dto.getEmpId());

                obList.add(
                        new attendanceTm(
                                dto.getDate(),
                                dto.getEmpId(),
                                EmpName.getName(),
                                dto.getInTime(),
                                dto.getOutTime(),
                                dto.getStatus()

                        )
                );
            }

            tblAttendance.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInTime.setCellValueFactory(new PropertyValueFactory<>("inTime"));
        colOutTime.setCellValueFactory(new PropertyValueFactory<>("outTime"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

        private void clearFields() {
        txtStatus.setText(" ");
        checkBoxIn.setSelected(false);
        checkBoxOut.setSelected(false);
        txtEmpName.setText(" ");
        cmbEmpId.setValue(" ");
        }

    private void loadEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<employeeDto> empList = bo.loadAllEmployees();

            for (employeeDto dto : empList) {
                obList.add(dto.getEmpId());
            }
            cmbEmpId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public void onActionBackBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/userDashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionClearBtn(ActionEvent event){
            clearFields();

    }
    public void onActionSaveBtn(ActionEvent event) throws ClassNotFoundException {
            setStatus();
        Date date = Date.valueOf(lbaDate.getText());
        String empId = (String) cmbEmpId.getValue();
        Time inTime = onActionCheckboxIn();
        Time outTime = null;
        String status = txtStatus.getText();


        var dto = new attendanceDto(empId,date,inTime,outTime,status);


        try {
            boolean isSaved = bo.SaveAttendance(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Attendance Mark Successfully !!!").show();
                loadAllAttendance();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    private void fillFields(attendanceDto dto) {
                txtStatus.setText(dto.getStatus());
                checkBoxIn.setSelected(dto.getInTime() != null);
                checkBoxOut.setSelected(dto.getOutTime() != null);
                boxes();
    }

    private void boxes(){
        if(checkBoxOut.isSelected()){
            checkBoxOut.setDisable(true);
        }
        if(checkBoxIn.isSelected()) {
            checkBoxIn.setDisable(true);
        }
    }

    public void onActionSearchCmbEmpId(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = (String) cmbEmpId.getValue();
            try {
                txtEmpName.setText((bo.SearchEmployee("empId",id).getName()));
                attendanceDto dto = bo.searchAttendancesById(id);
                fillFields(dto);

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                clearFields();
            }
    }

    public Time onActionCheckboxIn() {
        if(checkBoxIn.isSelected()) {
            setStatus();
            checkBoxIn.setDisable(true);
            SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
           Time time = Time.valueOf(dateFormat.format(new java.util.Date()));
            return time;
        }
        return null;
    }

    public void setStatus(){
            if(checkBoxIn.isSelected() && checkBoxOut.isSelected()){
                txtStatus.setText("WORKED");
            }else if(checkBoxIn.isSelected()){
                txtStatus.setText("IN");
        } else {
                txtStatus.setText("ABSENT");
            }
    }

    public Time onActionCheckBoxOut() {
            if(checkBoxOut.isSelected()){
                setStatus();
                checkBoxOut.setDisable(true);
                SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
                Time time = Time.valueOf(dateFormat.format(new java.util.Date()));
               return time;
            }
      return null;
    }

    public void onActionUpdateBtn(ActionEvent event) throws ClassNotFoundException {
            setStatus();
            Date date = null;
        String empId = (String) cmbEmpId.getValue();
        Time inTime = null;
        Time outTime = onActionCheckBoxOut();
        String status = txtStatus.getText();


        var dto = new attendanceDto(empId,date,inTime,outTime,status);

        try {
            boolean isUpdated = bo.Update(dto);

            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Attendance Mark updated !!!").show();
                loadAllAttendance();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            System.out.println("wisaala karadarayakne meka");
            clearFields();
        }
    }
}
