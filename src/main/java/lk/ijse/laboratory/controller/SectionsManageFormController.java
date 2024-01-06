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
import lk.ijse.laboratory.BO.custom.SectionBO;
import lk.ijse.laboratory.Dto.Tm.sectionTm;
import lk.ijse.laboratory.Dto.sectionDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SectionsManageFormController {

    public AnchorPane root;
    public TableView<sectionTm>tblSections;
    public TableColumn<?,?> colSectionId;
    public TableColumn<?,?> colSection;
    public TableColumn<?,?> colConsultantName;
    public TableColumn<?,?> colTestCountSection;
    public TextField txtSectionId;
    public TextField txtConsultantName;
    public TextField txtSection;
    public Label lblTetsts;
    public Label lblSections;
    public Label lblMachines;
    public Label lblCenters;
    SectionBO bo = (SectionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SECTION);

    public void initialize(){
        setCellValueFactory();
        loadAllSections();
        generateNextSectionId();
    }

    private void generateNextSectionId() {
        try {
            String secId = bo.generateNextSectionId();

            txtSectionId.setText(secId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllSections() {

        ObservableList<sectionTm> obList = FXCollections.observableArrayList();

        try {
            List<sectionDto> dtoList = bo.loadAllSections();

            for(sectionDto dto : dtoList) {
                int count = bo.getTestCount(dto.getSecId());
                obList.add(
                        new sectionTm(
                                dto.getSecId(),
                                dto.getSecName(),
                                dto.getConsultant(),
                               count
                        )
                );
            }

            tblSections.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colSectionId.setCellValueFactory(new PropertyValueFactory<>("secId"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("secName"));
        colConsultantName.setCellValueFactory(new PropertyValueFactory<>("consultant"));
        colTestCountSection.setCellValueFactory(new PropertyValueFactory<>("testCount"));
    }

    public void onActionBackBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/userDashboard_form.fxml"));
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

    public void onActionManageCollectingCentersBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/collectingCentersManage_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionSaveBtn(ActionEvent event) {
        String secId = txtSectionId.getText();
        String secName = txtSection.getText();
        String consultant = txtConsultantName.getText();


        var dto = new sectionDto(secId,secName,consultant);

        try {
            boolean isSaved = bo.SaveSection(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Section saved Successfully !!!").show();
                loadAllSections();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtSectionId.setText("");
        txtConsultantName.setText("");
        txtSection.setText("");
        generateNextSectionId();
    }

    public void onActionUpdateBtn(ActionEvent event) {
        String secId = txtSectionId.getText();
        String secName = txtSection.getText();
        String consultant = txtConsultantName.getText();
        var dto = new sectionDto(secId,secName,consultant);

        try {
            boolean isUpdated = bo.UpdateSection(dto);

            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Section updated Successfully!!!").show();
                loadAllSections();
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
        String secId = txtSectionId.getText();

        try {
            boolean isDeleted = bo.DeleteSection(secId);

            if(isDeleted) {
                tblSections.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Section deleted !!!").show();
                clearFields();
                loadAllSections();
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

    public void onActionSearchtxtSectionId(ActionEvent event) {
        String id= txtSectionId.getText();

        try {
            sectionDto dto = bo.SearchSection("secId",id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Section Id not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillFields(sectionDto dto) {
        txtSectionId.setText(dto.getSecId());
        txtSection.setText(dto.getSecName());
        txtConsultantName.setText(dto.getConsultant());
    }

    public void onActionSearchtxtSection(ActionEvent event) {
        String sec= txtSection.getText();

        try {
            sectionDto dto = bo.SearchSection("secName",sec);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Section not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void onActionManageTestsBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/test_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionMachinesMouseExited(MouseEvent mouseEvent) {
lblMachines.setVisible(false);
    }

    public void onActionMachinesMouseEntered(MouseEvent mouseEvent) {
lblMachines.setVisible(true);
    }

    public void onActionCentersMouseEntered(MouseEvent mouseEvent) {
lblCenters.setVisible(true);
    }

    public void onActionSectionsMouseExited(MouseEvent mouseEvent) {
lblSections.setVisible(false);
    }

    public void onActionSectionsMouseEntered(MouseEvent mouseEvent) {
lblSections.setVisible(true);
    }

    public void onActionTestsMouseEntered(MouseEvent mouseEvent) {
lblTetsts.setVisible(true);
    }

    public void onActionTestsMouseExited(MouseEvent mouseEvent) {
lblTetsts.setVisible(false);
    }

    public void onActionCentersMouseExited(MouseEvent mouseEvent) {
lblCenters.setVisible(false);
    }
}
