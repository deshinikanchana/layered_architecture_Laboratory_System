package lk.ijse.laboratory.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.ViewAttendanceBO;
import lk.ijse.laboratory.Dto.Tm.attendanceTm;
import lk.ijse.laboratory.Dto.attendanceDto;
import lk.ijse.laboratory.Dto.designationDto;
import lk.ijse.laboratory.Dto.employeeDto;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ViewAttendanceFormController {

    public TextField txtDate;
    public JFXComboBox cmbEmployeeId;
    public TextField txtEmployeeName;
    public TextField txtOtHours;
    public TextField txtWorkedDaysCount;
    public TableView<attendanceTm> tblAttendance;
    public TableColumn<?,?> colEmployeeId;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colInTime;
    public TableColumn<?,?> colOutTime;
    public TableColumn<?,?> colStatus;

    public TableColumn<?,?> colName;

    public AnchorPane root;
    public Label lblEmployees;
    public Label lblDesignation;
    public Label lblViewAttendance;
    public Label lblSalary;
    public TextField txtNopayHrs;

    ViewAttendanceBO bo = (ViewAttendanceBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.VIEWATTENDANCE);
    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadAllAttendances();
        loadAllEmpIds();
        setDates();
    }

    private void setDates() {
        Date date = Date.valueOf(LocalDate.now());
        txtDate.setText(String.valueOf(date));
    }

    private void loadAllEmpIds(){
        ObservableList<String> obListId = FXCollections.observableArrayList();
        try {
            List<employeeDto> testList = bo.loadAllEmployees();

            for (employeeDto dto : testList) {
                obListId.add(dto.getEmpId());
            }
            cmbEmployeeId.setItems(obListId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllAttendances() throws ClassNotFoundException {

        ObservableList<attendanceTm> obList = FXCollections.observableArrayList();

        try {
            List<attendanceDto> dtoList = bo.loadAllAttendances();
            for(attendanceDto dto : dtoList) {

                employeeDto edto = bo.SearchEmployee("empId", dto.getEmpId());
                obList.add(
                        new attendanceTm(
                                dto.getDate(),
                                dto.getEmpId(),
                                edto.getName(),
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
        colInTime.setCellValueFactory(new PropertyValueFactory<>("inTime"));
        colOutTime.setCellValueFactory(new PropertyValueFactory<>("outTime"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void onActionBackBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/adminDashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionEmployeeManageBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/employee_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();

    }

    public void onActionManageSalariesBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/salaryManage_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionManageDesignationsBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/designationManage_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void cmbEmployeeIdSearchOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = String.valueOf(cmbEmployeeId.getValue());

        try {
            attendanceDto dto = bo.SearchAttendances(null,id);

            if(dto != null) {
                fieldDetails(dto);

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fieldDetails(attendanceDto dto) throws SQLException, ClassNotFoundException {
        Date date = Date.valueOf(txtDate.getText());
        int mon = (date.getMonth() + 1);


        employeeDto edto = bo.SearchEmployee("empId",dto.getEmpId());
        txtEmployeeName.setText(edto.getName());
        designationDto Ddto = bo.SearchDesignations(null,edto.getJobId());


        int days = bo.getCount(dto.getEmpId(),mon);
        float hours = bo.calculateTotalHours(dto.getEmpId(), mon);

            LocalDate day = LocalDate.now();
            float Ot = (hours - Ddto.getWorkingHoursPerMonth());
            if(day.getDayOfMonth() < 25 && Ot >0){
                txtOtHours.setText(String.valueOf(Ot));
                txtNopayHrs.setText("-");
            }else if(day.getDayOfMonth() > 26 && Ot <0){
                float nop = (Ddto.getWorkingHoursPerMonth() - hours);
                txtNopayHrs.setText(String.valueOf(nop));
                txtOtHours.setText("-");
            }else{
                txtOtHours.setText("-");
                txtNopayHrs.setText("-");
            }


        txtWorkedDaysCount.setText(String.valueOf(days));

    }

    public void onActionTxtDate(ActionEvent event) {
// show table
    }

    public void onActionSalaryMouseEntered(MouseEvent mouseEvent) {
    lblSalary.setVisible(true);
    }

    public void onActionSalaryMouseExited(MouseEvent mouseEvent) {
lblSalary.setVisible(false);
    }

    public void onActionEmployeesMouseExited(MouseEvent mouseEvent) {
lblEmployees.setVisible(false);
    }

    public void onActionEmployeesMouseEntered(MouseEvent mouseEvent) {
        lblEmployees.setVisible(true);
    }

    public void onActionDesignationsMouseEntered(MouseEvent mouseEvent) {
lblDesignation.setVisible(true);
    }

    public void onActionDesignationsMouseExited(MouseEvent mouseEvent) {
        lblDesignation.setVisible(false);
    }

    public void onActionViewAttendanceMouseExited(MouseEvent mouseEvent) {
lblViewAttendance.setVisible(false);
    }

    public void onActionViewAttendanceMouseEntered(MouseEvent mouseEvent) {
        lblViewAttendance.setVisible(true);
    }
}
