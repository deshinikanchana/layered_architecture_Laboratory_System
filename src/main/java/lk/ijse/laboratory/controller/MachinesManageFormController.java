package lk.ijse.laboratory.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.MachinesBO;
import lk.ijse.laboratory.Dto.Tm.machineTm;
import lk.ijse.laboratory.Dto.machineDto;
import lk.ijse.laboratory.Dto.sectionDto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MachinesManageFormController implements Initializable {
    public AnchorPane root;
    public TableView<machineTm> tblMachines;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?>  colMachineName;
    public TableColumn<?,?>  colSectionName;
    public TableColumn<?,?>  colCondition;
    public TextField txtMachineId;
    public TextField txtMachine;
    public JFXComboBox cmbSectionId;
    public TextField txtDate;
    public TableColumn<?,?>  colMachineId;
    public Label lblTests;
    public Label lblSections;
    public Label lblMachines;
    public Label lblCenters;
    @FXML
    private ChoiceBox<String> conditionChoiceBox;

    MachinesBO bo = (MachinesBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MACHINE);
    private String [] Conditions = {"Execellent","Good","Normal","Bad","Dangeours"};

    public void onActionBackBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/adminDashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionManageCollectingCentersBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/collectingCentersManage_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionUpdateBtn(ActionEvent event) {

        var dto = new machineDto();
        dto.setMachineId(txtMachineId.getText());
        dto.setStatus(conditionChoiceBox.getValue());

        try {
            boolean isUpdated =bo.UpdateMachine(dto);

            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Machine Details updated !!!").show();
                loadAllMachines();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionDeleteBtn(ActionEvent event) {
        String id = txtMachineId.getText();

        try {
            boolean isDeleted = bo.DeleteMachine(id);

            if(isDeleted) {
                tblMachines.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Machine deleted !!!").show();
                clearFields();
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

    public void onActionSearchtxtMachine(ActionEvent event) {
        String machineName = txtMachine.getText();
        try {
            machineDto dto = bo.SearchMachine("machineName",machineName);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Machine not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillFields(machineDto dto) {
        txtMachineId.setText(dto.getMachineId());
        txtMachine.setText(dto.getMachineName());
        cmbSectionId.setValue(dto.getSecId());
        conditionChoiceBox.setValue(dto.getStatus());
    }


    public void onActionSaveBtn(ActionEvent event) {
        boolean isValidate = validateMachine();
        if(isValidate) {
            String machineId = txtMachineId.getText();
            String machineName = txtMachine.getText();
            String sectionId = (String) cmbSectionId.getValue();
            String status = conditionChoiceBox.getValue();


            var dto = new machineDto(machineId, machineName, sectionId, status);


            try {
                boolean isSaved = bo.SaveMachine(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "New Machine saved Successfully !!!").show();
                    loadAllMachines();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean validateMachine() {

        String id = txtMachineId.getText();
        boolean isCustomerValidate = Pattern.matches("[M][0-9]{3,}", id);
        if (!isCustomerValidate) {
            new Alert(Alert.AlertType.ERROR, "Inavlid Machine Id !").show();
            return false;
        }
        String name = txtMachine.getText();

        boolean isCustomerNameValidate = Pattern.matches("[A-za-z]{3,}", name);
        if (!isCustomerNameValidate) {
            new Alert(Alert.AlertType.ERROR, "Inavlid Machine Name !").show();
            return false;
        }

        return true;
    }
    private void clearFields() {
        txtMachineId.setText("");
        txtMachine.setText(" ");
        cmbSectionId.setValue(" ");
        conditionChoiceBox.setValue(" ");
    }

    public void onActionSearchtxtMachineId(ActionEvent event) {
        String id = txtMachineId.getText();
        try {
            machineDto dto = bo.SearchMachine("machineId",id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Machine not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionManageSectionsBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/sectionsManage_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionManageTestsBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/test_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conditionChoiceBox.getItems().addAll(Conditions);
        loadSectionIds();
        setCellValueFactory();
        loadAllMachines();
        setdate();

    }

    private void setdate() {
txtDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadAllMachines() {

        ObservableList<machineTm> obList = FXCollections.observableArrayList();

        try {
            List<machineDto> dtoList =bo.loadAllMachines();
            for(machineDto dto : dtoList) {
                LocalDate date = LocalDate.now();

               sectionDto secDto = bo.SearchSection("secId",dto.getSecId());

                obList.add(
                        new machineTm(
                                date,
                                dto.getMachineId(),
                                dto.getMachineName(),
                                secDto.getSecName(),
                                dto.getStatus()

                        )
                );
            }

            tblMachines.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colMachineId.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        colMachineName.setCellValueFactory(new PropertyValueFactory<>("machineName"));
        colSectionName.setCellValueFactory(new PropertyValueFactory<>("sectionName"));
        colCondition.setCellValueFactory(new PropertyValueFactory<>("condition"));
    }

    private void loadSectionIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<sectionDto> SecList = bo.loadAllSections();

            for (sectionDto dto: SecList) {
                obList.add(dto.getSecId());
            }
            cmbSectionId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionTestMouseEntered(MouseEvent mouseEvent) {
        lblTests.setVisible(true);
    }

    public void onActionTestMouseExited(MouseEvent mouseEvent) {
        lblTests.setVisible(false);
    }

    public void onActionSectionMouseExited(MouseEvent mouseEvent) {
        lblSections.setVisible(false);
    }

    public void onActionSectionMouseEntered(MouseEvent mouseEvent) {
        lblSections.setVisible(true);
    }

    public void onActionCentersMouseEntered(MouseEvent mouseEvent) {
        lblCenters.setVisible(true);
    }

    public void onActionCentersMouseExited(MouseEvent mouseEvent) {
        lblCenters.setVisible(false);
    }

    public void onActionMachinesMouseExited(MouseEvent mouseEvent) {
        lblMachines.setVisible(false);
    }

    public void onActionMachinesMouseEntered(MouseEvent mouseEvent) {
        lblMachines.setVisible(true);
    }
}
