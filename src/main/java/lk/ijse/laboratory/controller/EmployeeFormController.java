package lk.ijse.laboratory.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.EmployeeBO;
import lk.ijse.laboratory.Dto.Tm.EmployeeTm;
import lk.ijse.laboratory.Dto.designationDto;
import lk.ijse.laboratory.Dto.employeeDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import static lk.ijse.laboratory.controller.LoginFormController.newDto;

public class EmployeeFormController {
    @FXML
   public TableView<EmployeeTm> tblEmployees;
    @FXML
    public TableColumn<? ,?> colEmpId;
    @FXML
    public TableColumn<? ,?> colNic;
    @FXML
    public TableColumn<? ,?> colName;
    @FXML
    public TableColumn<?,?> colEmail;
    @FXML
    public TableColumn<? ,?> colTel;
    @FXML
    public TextField txtEmpId;
    @FXML
    public ComboBox cmbJobRole;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtNic;
    @FXML
    public TextField txtAddress;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtTelNo;
    public TextField txtJobRole;
    public TableColumn<? ,?> colDesignation;
    public TableColumn<? ,?> colAddress;
    public Label lblSalary;
    public Label lblViewAttendance;
    public Label lblDesignations;
    public Label lblEmployees;

    @FXML
    private AnchorPane root;
    EmployeeBO bo = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);


    public void initialize() {
        loadJobIds();
        setCellValueFactory();
        loadAllEmployees();
        generateNextEmployeeId();
    }

    private void generateNextEmployeeId(){
        try {
            String EmployeeId = bo.generateNextEmployeeId();
            txtEmpId.setText(EmployeeId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllEmployees() {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<employeeDto> dtoList = bo.loadAllEmployees();

            for(employeeDto dto : dtoList) {
                designationDto designation = bo.SearchDesignation(null,dto.getJobId());
                obList.add(
                        new EmployeeTm(
                                dto.getEmpId(),
                                dto.getNic(),
                                dto.getName(),
                                dto.getEmail(),
                                dto.getTelNo(),
                                designation.getJobTitle(),
                                dto.getAddress()

                        )
                );
            }

            tblEmployees.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("EmpId"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("Nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        colDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
    }


    private void loadJobIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<designationDto> cusList = bo.loadAllDesignations();

            for (designationDto dto : cusList) {
                obList.add(dto.getJboId());
            }
            cmbJobRole.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void onActionBackBtn(ActionEvent mouseEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/adminDashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();

    }

    public void onActionDesignationManageBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/designationManage_form.fxml"));
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

    @FXML
    public void onActionSaveBtn(ActionEvent event) {
        boolean IsValidate = validateEmployee();
        if(IsValidate) {
            String EmpId = txtEmpId.getText();
            String jobId = (String) cmbJobRole.getValue();
            String UserId = newDto.getUserId();
            String name = txtName.getText();
            String nic = txtNic.getText();
            String address = txtAddress.getText();
            String email = txtEmail.getText();
            String tel = txtTelNo.getText();

            var dto = new employeeDto(EmpId, jobId, UserId, name, nic, address, email, tel);

            try {
                boolean isSaved = bo.SaveEmployee(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee saved Successfully !!!").show();
                    loadAllEmployees();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean validateEmployee() {

        String id = txtEmpId.getText();
        boolean isCustomerValidate = Pattern.matches("[E][0-9]{3,}", id);
        if (!isCustomerValidate) {
            new Alert(Alert.AlertType.ERROR, "Inavlid Id !").show();
            return false;
        }
        String name = txtName.getText();

        boolean isCustomerNameValidate = Pattern.matches("[A-za-z]{3,}", name);
        if (!isCustomerNameValidate) {
            new Alert(Alert.AlertType.ERROR, "Inavlid Name !").show();
            return false;
        }
        String address = txtAddress.getText();

        boolean isCustomerAddressValidate = Pattern.matches("[A-za-z]{3,}", address);
        if (!isCustomerAddressValidate) {
            new Alert(Alert.AlertType.ERROR, "Inavlid Address !").show();
            return false;
        }
        String tel = txtTelNo.getText();

        boolean isCustomerTelValidate = Pattern.matches("\\d{10}", tel);
        if (!isCustomerTelValidate) {
            new Alert(Alert.AlertType.ERROR, "Inavlid tel. No !").show();
            return false;
        }

        return true;
    }



    public void onActionDeleteBtn(ActionEvent event) {
        String id = txtEmpId.getText();

        try {
            boolean isDeleted = bo.DeleteEmployee(id);

            if(isDeleted) {
                tblEmployees.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted !!!").show();
                clearFields();
                loadAllEmployees();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void txtNicSearchOnAction(ActionEvent event) {
        String nic= txtNic.getText();

        try {
            employeeDto dto = bo.SearchEmployee("nic",nic);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillFields(employeeDto dto) {
        txtEmpId.setText(dto.getEmpId());
        cmbJobRole.setValue(dto.getJobId());
        txtName.setText(dto.getName());
        txtNic.setText(dto.getNic());
        txtAddress.setText(dto.getAddress());
        txtEmail.setText(dto.getEmail());
        txtTelNo.setText(dto.getTelNo());
    }



    public void txtNameSearchOnAction(ActionEvent event) {
        String name= txtName.getText();

        try {
            employeeDto dto = bo.SearchEmployee("name",name);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtIdSearchOnAction(ActionEvent event) {
        String eId= txtEmpId.getText();

        try {
            employeeDto dto = bo.SearchEmployee("empId",eId);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void onActionNewbtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/designationManage_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionUpdateBtn(ActionEvent event) {
        String EmpId = txtEmpId.getText();
        String jobId = (String) cmbJobRole.getValue();
        String UserId = newDto.getUserId();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String tel = txtTelNo.getText();

        var dto = new employeeDto(EmpId,jobId,UserId,name,nic,address,email,tel);

        try {
            boolean isUpdated = bo.UpdateEmployee(dto);

            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated !!!").show();
                loadAllEmployees();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    void clearFields() {
        txtEmpId.setText("");
        txtJobRole.setText("");
        txtName.setText("");
        txtNic.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtTelNo.setText("");
        generateNextEmployeeId();
    }

    public void onActionClearBtn(ActionEvent event) {
        clearFields();
    }

    public void jobRoleSearchOnAction(ActionEvent event) {
       String id = String.valueOf(cmbJobRole.getValue());

        try {
            designationDto dto  = bo.SearchDesignation(null,id);
            if (dto.getJobTitle() != null) {
                txtJobRole.setText(dto.getJobTitle());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionEmployeesMouseEntered(MouseEvent mouseEvent) {
        lblEmployees.setVisible(true);
    }

    public void onActionEmployeesMouseExited(MouseEvent mouseEvent) {
        lblEmployees.setVisible(false);
    }

    public void onActionDesignationMouseEntered(MouseEvent mouseEvent) {
        lblDesignations.setVisible(true);
    }

    public void onActionDesignationMouseExited(MouseEvent mouseEvent) {
        lblDesignations.setVisible(false);
    }

    public void onActionViewAttendanceMouseExited(MouseEvent mouseEvent) {
        lblViewAttendance.setVisible(false);
    }

    public void onActionViewAttendanceMouseEntered(MouseEvent mouseEvent) {
        lblViewAttendance.setVisible(true);
    }

    public void onActionSalaryMouseExited(MouseEvent mouseEvent) {
        lblSalary.setVisible(false);
    }

    public void onActionSalaryMouseEntered(MouseEvent mouseEvent) {
        lblSalary.setVisible(true);
    }
}
