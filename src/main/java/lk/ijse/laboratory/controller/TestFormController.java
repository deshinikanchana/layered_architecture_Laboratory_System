package lk.ijse.laboratory.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.TestsBO;
import lk.ijse.laboratory.Dto.*;
import lk.ijse.laboratory.Dto.Tm.testTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;



public class TestFormController  implements Initializable {


    public TableView<testTm> tblTests;
    public TableColumn<?,?> colTestId;
    public TableColumn<?,?> colTestSectionName;
    public TableColumn<?,?> colTestName;
    public TableColumn<?,?> colPrice;
    public TextField txtTestId;
    public ComboBox cmbSections;
    public TextField txtTestName;
    public TextField TxtEstTime;
    public TextField txtPrice;
    public TableColumn<?,?> colMachine;
    public TableColumn<?,?> colEstimatedTime;
    public JFXComboBox cmbMachineId;
    public Label lblTests;
    public Label lblSections;
    public Label lblMachines;
    public Label lblCenters;
    public Label lblSectionName;
    public JFXButton usageBtn;
    public JFXButton insBtn;
    public JFXButton subTestBtn;
    @FXML
    private AnchorPane root;
    @FXML
    private ChoiceBox<String> sampleTypeChoiceBox;

    TestsBO bo = (TestsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TEST);
    private String [] Samples = {"Blood","Urine","Stool","Seminal Fluid"};
    private ActionEvent Event;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sampleTypeChoiceBox.getItems().addAll(Samples);
        loadSections();
        loadMachineIds();
        generateNextTestCode();
        loadAllTestsToTbl();
        setCellValueFactory();
        btnDisable();
    }

    private void btnDisable(){
       if(txtPrice.getText().equals(null)) {
           subTestBtn.setDisable(true);
           insBtn.setDisable(true);
           usageBtn.setDisable(true);
       }else{
           subTestBtn.setDisable(false);
           insBtn.setDisable(false);
           usageBtn.setDisable(false);
       }
    }
    private void setCellValueFactory() {
        colTestId.setCellValueFactory(new PropertyValueFactory<>("testId"));
        colTestSectionName.setCellValueFactory(new PropertyValueFactory<>("section"));
        colMachine.setCellValueFactory(new PropertyValueFactory<>("machine"));
        colTestName.setCellValueFactory(new PropertyValueFactory<>("test"));
        colEstimatedTime.setCellValueFactory(new PropertyValueFactory<>("estimatedTime"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    private void loadAllTestsToTbl() {
        ObservableList<testTm> obList = FXCollections.observableArrayList();

        try {
            List<testDto> dtoList = bo.loadAllTests();
            for(testDto dto : dtoList) {
                machineDto mcDto = bo.SearchMachine("machineId",dto.getMachineId());

                sectionDto secDto = bo.SearchSection("secId", dto.getSecId());
                if (mcDto != null & secDto != null) {
                    obList.add(
                            new testTm(
                                    dto.getTestId(),
                                    secDto.getSecName(),
                                    mcDto.getMachineName(),
                                    dto.getTest(),
                                    dto.getEstimatedTime(),
                                    dto.getPrice()
                            )
                    );
                }
            }

            tblTests.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextTestCode() {
        try {
            String testId = bo.generateNextTestId();
            txtTestId.setText(testId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMachineIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<machineDto> machineList = bo.loadAllMachines();

            for (machineDto dto : machineList) {
                obList.add(dto.getMachineId());
            }
            cmbMachineId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSections() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<sectionDto> secList = bo.loadAllSections();

            for (sectionDto dto : secList) {
                obList.add(dto.getSecId());
            }
            cmbSections.setItems(obList);

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

    public void onActionSaveBtn(ActionEvent event) throws IOException {
        String testId = txtTestId.getText();
        String secId = (String) cmbSections.getValue();
        String testName = txtTestName.getText();
        String estTime = TxtEstTime.getText();
        Float price = Float.valueOf(txtPrice.getText());
        String sampleType = sampleTypeChoiceBox.getValue();
        String machineId = (String) cmbMachineId.getValue();


        var dto = new testDto(testId,secId,testName,estTime,price,sampleType,machineId);

        try {
            boolean isSaved = bo.SaveTest(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Test saved Successfully !!!").show();
               loadAllTestsToTbl();
                clearFields();
                btnDisable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Parent anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/refferenceRanges_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Suwasahana Medical Laboratory");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    private void clearFields() {
        txtTestId.setText("");
        txtPrice.setText("");
        txtTestName.setText("");
        cmbSections.setValue("");
        lblSectionName.setText("");
        sampleTypeChoiceBox.setValue("");
        TxtEstTime.setText("");
        cmbMachineId.setValue(" ");
        generateNextTestCode();
    }

    public void onActionUpdateBtn(ActionEvent event) {
        String testId = txtTestId.getText();
        String secId = (String) cmbSections.getValue();
        String testName = txtTestName.getText();
        String estTime = TxtEstTime.getText();
        Float price = Float.valueOf(txtPrice.getText());
        String sampleType = sampleTypeChoiceBox.getValue();
        String machineId = (String) cmbMachineId.getValue();


        var dto = new testDto(testId,secId,testName,estTime,price,sampleType,machineId);

        try {
            boolean isUpdated = bo.UpdateTest(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Test Updated Successfully !!!").show();
                loadAllTestsToTbl();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionDeleteBtn(ActionEvent event) {
        String id = txtTestId.getText();

        try {
            boolean isDeleted = bo.DeleteTest(id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Test deleted !!!").show();
                loadAllTestsToTbl();
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

    public void onActionSearchTxtTestId(ActionEvent event) {
        String id= txtTestId.getText();


        try {
            testDto dto = bo.SearchTest("testId", id);

            if(dto != null) {
                setFields(dto);
                btnDisable();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Test not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void setFields(testDto dto) {
        cmbMachineId.setValue(dto.getMachineId());
        txtTestId.setText(dto.getTestId());
        cmbSections.setValue(dto.getSecId());
        onActioncmbSecId(Event);
        txtTestName.setText(dto.getTest());
        TxtEstTime.setText(dto.getEstimatedTime());
        txtPrice.setText(String.valueOf(dto.getPrice()));
        sampleTypeChoiceBox.setValue(dto.getSampleType());
    }


    public void onActionManageCollectingCentersBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/collectingCentersManage_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionManageMachinesBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/machinesManage_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();

    }

    public void onActionManageSectionsBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/sectionsManage_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionTestsMouseEntered(MouseEvent mouseEvent) {
lblTests.setVisible(true);
    }

    public void onActionTestsMouseExited(MouseEvent mouseEvent) {
lblTests.setVisible(false);
    }

    public void onActionSectionsMouseEntered(MouseEvent mouseEvent) {
lblSections.setVisible(true);
    }

    public void onActionSectionsMouseExited(MouseEvent mouseEvent) {
lblSections.setVisible(false);
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

    public void onActioncmbSecId(ActionEvent event) {
        String id = (String) cmbSections.getValue();

        try {
            sectionDto dto = bo.SearchSection("secId",id);

            if(dto != null) {
              lblSectionName.setText(dto.getSecName());

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionSubTestBtn(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/refferenceRanges_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Suwasahana Medical Laboratory");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void onActionUsageBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/stockUsage_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionInsBtn(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/instructions_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Suwasahana Medical Laboratory");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
