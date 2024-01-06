package lk.ijse.laboratory.controller;

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
import lk.ijse.laboratory.BO.custom.DesignationBO;
import lk.ijse.laboratory.Dto.Tm.designationTm;
import lk.ijse.laboratory.Dto.designationDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DesignationManageFormController {
    public AnchorPane root;
    public TableView<designationTm> tblDesignations;
    public TableColumn<?,?> colJobId;
    public TableColumn<?,?> colJobRole;
    public TableColumn<?,?> colBasicSalary;
    public TableColumn<?,?> colOtPayments;
    public TextField txtJobId;
    public TextField txtBasicSalary;
    public TextField txtOt;
    public TextField txtJobRole;
    public TextField txtWorkingHrs;
    public TableColumn<?,?> colwrkinghrs;
    public TableColumn<?,?> colEmpCount;
    public Label lblSalary;
    public Label lblviewAttendance;
    public Label lblDesignation;
    public Label lblEmployees;

    DesignationBO bo = (DesignationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DESIGNATION);
    public void initialize() {
        setCellValueFactory();
        loadAllDesignations();
        generateNextDesignationId();
    }

    private void generateNextDesignationId() {
        try {
            String desId = bo.generateNextDesignationId();
            txtJobId.setText(desId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colJobId.setCellValueFactory(new PropertyValueFactory<>("jboId"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        colBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        colOtPayments.setCellValueFactory(new PropertyValueFactory<>("otPaymentsPerHour"));
        colwrkinghrs.setCellValueFactory(new PropertyValueFactory<>("workingHoursPerMonth"));
        colEmpCount.setCellValueFactory(new PropertyValueFactory<>("empCount"));
    }

    private void loadAllDesignations() {
        ObservableList<designationTm> obList = FXCollections.observableArrayList();

        try {
            List<designationDto> DtoList = bo.loadAllDesignations();

            for(designationDto dto : DtoList) {
                int count = bo.getEmpCount(dto.getJboId());
                obList.add(
                        new designationTm(
                                dto.getJboId(),
                                dto.getJobTitle(),
                                dto.getWorkingHoursPerMonth(),
                                dto.getBasicSalary(),
                                dto.getOtPaymentsPerHour(),
                                count
                        )
                );
            }

            tblDesignations.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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


    public void onActionViewAttendanceBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/viewAttendance_form.fxml"));
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

    public void onActionSaveBtn(ActionEvent event) {
        String jobId = txtJobId.getText();
        String jobTitle = txtJobRole.getText();
        int workingHrs = Integer.parseInt(txtWorkingHrs.getText());
        float basicSalary = Float.parseFloat(txtBasicSalary.getText());
        float otPayments = Float.parseFloat(txtOt.getText());

        var dto = new designationDto(jobId,jobTitle,workingHrs,basicSalary,otPayments);

        try {
            boolean isSaved =bo.SaveDesignation(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Designation saved !!!").show();
                clearFields();
                loadAllDesignations();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtJobId.setText("");
        txtJobRole.setText("");
        txtWorkingHrs.setText("");
        txtBasicSalary.setText("");
        txtOt.setText("");
        generateNextDesignationId();
    }

    public void onActionUpdateBtn(ActionEvent event) {
        String jobId = txtJobId.getText();
        String jobTitle = txtJobRole.getText();
        int workingHrs = Integer.parseInt(txtWorkingHrs.getText());
        float basicSalary = Float.parseFloat(txtBasicSalary.getText());
        float otPayments = Float.parseFloat(txtOt.getText());

        if(jobId != null) {
            var dto = new designationDto(jobId, jobTitle, workingHrs, basicSalary, otPayments);

            try {
                boolean isUpdated = bo.UpdateDesignation(dto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Designation updated!!!").show();
                    clearFields();
                    loadAllDesignations();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.CONFIRMATION, "All Fields Are Required!!!").show();
        }
    }

    public void onActionDeleteBtn(ActionEvent event) {
        String id = txtJobId.getText();

        try {
            boolean isDeleted = bo.DeleteDesignation(id);

            if(isDeleted) {
                tblDesignations.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Designation deleted!!!").show();
                clearFields();
                loadAllDesignations();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void onActionClearBtn(ActionEvent event) {
        clearFields();
    }

    public void txtJobIdSearchOnAction(ActionEvent event) {
        String id = txtJobId.getText();

        try {
            designationDto dto = bo.SearchDesignation(null,id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillFields(designationDto dto) {
        txtJobId.setText(dto.getJboId());
        txtJobRole.setText(dto.getJobTitle());
        txtWorkingHrs.setText(String.valueOf(dto.getWorkingHoursPerMonth()));
        txtBasicSalary.setText(String.valueOf(dto.getBasicSalary()));
        txtOt.setText(String.valueOf(dto.getOtPaymentsPerHour()));
    }

    public void onActionDesignationMouseEntered(MouseEvent mouseEvent) {
        lblDesignation.setVisible(true);
    }

    public void onActionDesignationMouseExited(MouseEvent mouseEvent) {
        lblDesignation.setVisible(false);
    }

    public void onActionSalaryMouseEntered(MouseEvent mouseEvent) {
        lblSalary.setVisible(true);
    }

    public void onActionSalaryMouseExited(MouseEvent mouseEvent) {
        lblSalary.setVisible(false);
    }

    public void onActionViewAttendanceMouseEntered(MouseEvent mouseEvent) {
        lblviewAttendance.setVisible(true);
    }

    public void onActionViewAttendanceMouseExited(MouseEvent mouseEvent) {
        lblviewAttendance.setVisible(false);
    }
    public void onActionEmployeesMouseExited(MouseEvent mouseEvent) {
        lblEmployees.setVisible(false);
    }

    public void onActionEmployeesMouseEntered(MouseEvent mouseEvent) {
        lblEmployees.setVisible(true);
    }

    public void onActionBasicSalaryTxt(ActionEvent event) {
        txtWorkingHrs.requestFocus();
    }

    public void onActionJobRoleTxt(ActionEvent event) {
        txtBasicSalary.requestFocus();
    }

    public void onActionWrkHrsTxt(ActionEvent event) {
        double sal = Double.parseDouble(txtBasicSalary.getText());
        double hr = Double.parseDouble(txtWorkingHrs.getText());

        double ot = ((sal / hr)* 1.5);

        txtOt.setText(String.valueOf(ot));
    }
}
