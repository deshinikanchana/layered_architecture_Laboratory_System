package lk.ijse.laboratory.controller;

import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.laboratory.BO.custom.StockUsageBO;
import lk.ijse.laboratory.Dto.*;
import lk.ijse.laboratory.Dto.Tm.stockUsageTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockUsageFormController {

    private final ObservableList<stockUsageTm> obList = FXCollections.observableArrayList();
    public TableView<stockUsageTm> tblStockUsage;
    public TableColumn<?,?> colTestCode;
    public TableColumn<?,?>  colTestName;
    public TableColumn<?,?>  colItemCode;
    public TableColumn<?,?>  colItemName;
    public TableColumn<?,?>  colUsagePerTest;
    public TextField txtUsagePerTest;
    public ComboBox cmbTestName;
    public ComboBox cmbItemName;
    public Label lblStock;
    public Label lblUsage;
    public Label lblSuppliers;
    public Label lblOrders;
    public JFXComboBox cmbtestId;
    public JFXComboBox cmbItemCode;
    @FXML
    private AnchorPane root;
    StockUsageBO bo = (StockUsageBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STOCKUSAGE);

    public void initialize(){
        loadTestIds();
        setCellValueFactory();
        loadItemIds();

    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colTestCode.setCellValueFactory(new PropertyValueFactory<>("testCode"));
        colTestName.setCellValueFactory(new PropertyValueFactory<>("testName"));
        colUsagePerTest.setCellValueFactory(new PropertyValueFactory<>("usagePerTest"));
    }

    private void loadTestIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        ObservableList<String> obList1 = FXCollections.observableArrayList();
        try {
            List<testDto> testList = bo.loadAllTests();

            for (testDto dto : testList) {
                obList.add(dto.getTestId());
                obList1.add(dto.getTest());
            }
            cmbtestId.setItems(obList);
            cmbTestName.setItems(obList1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        ObservableList<String> obList1 = FXCollections.observableArrayList();
        try {
            List<stockItemDto> itemList = bo.loadAllItems();

            for (stockItemDto dto : itemList) {
                obList.add(dto.getItemCode());
                obList1.add(dto.getDescription());
            }
            cmbItemCode.setItems(obList);
            cmbItemName.setItems(obList1);

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

    public void onActionSuppliersBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/suppliers_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionOrdersBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/orders_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }


    public void onActionSearchCmbTestName(ActionEvent event) {
        String Name = String.valueOf(cmbTestName.getValue());

        try {
            testDto dto = bo.SearchTest("test",Name);

            if(dto != null) {
                cmbtestId.setValue(dto.getTestId());
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

    private void clearFields() {
        cmbItemCode.setValue("");
        cmbItemName.setValue("");
        txtUsagePerTest.setText("");
    }

    public void onActionAddBtn(ActionEvent event) {
    String testCode = (String) cmbtestId.getValue();
    String testName = (String) cmbTestName.getValue();
    String itemCode = (String) cmbItemCode.getValue();
    String itemName = (String) cmbItemName.getValue();
    int qty = Integer.parseInt(txtUsagePerTest.getText());

        obList.add(new stockUsageTm(
                testCode,
                testName,
                itemCode,
                itemName,
                qty
        ));

        tblStockUsage.setItems(obList);
        tblStockUsage.refresh();
       clearFields();
    }

    public void onActionSaveBtn(ActionEvent event) throws SQLException, ClassNotFoundException {

        List<stockUsageTm> tmList = new ArrayList<>();
        boolean isSaved = false;
        for (stockUsageTm usageTm : obList) {
            tmList.add(usageTm);
            stockUsageDto dto = new stockUsageDto(usageTm.getTestCode(), usageTm.getItemCode(), usageTm.getUsagePerTest());
            isSaved = bo.SaveUsage(dto);
        }
        if(isSaved) {
            tblStockUsage.getItems().clear();
            new Alert(Alert.AlertType.CONFIRMATION, "details Saved !!!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Error !!!").show();
        }

    }
    public void onActionSearchCmbItemName(ActionEvent event) {
        String name = (String) cmbItemName.getValue();

        try {
            stockItemDto dto = bo.SearchItem("description",name);

            if(dto != null) {
                cmbItemCode.setValue(dto.getItemCode());

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Item not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionCmbTestIdSearch(ActionEvent event) {
        String id = String.valueOf(cmbtestId.getValue());

        try {
            testDto dto = bo.SearchTest("testId",id);

            if(dto != null) {
                cmbTestName.setValue(dto.getTest());
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

    public void onActiomCmbItemCodeSearch(ActionEvent event) {
        String code = (String) cmbItemCode.getValue();

        try {
            stockItemDto dto = bo.SearchItem("itemCode",code);

            if(dto != null) {
                cmbItemName.setValue(dto.getDescription());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Item not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void onActionStockItemBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/stocks_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionStocksMouseEntered(MouseEvent mouseEvent) {
lblStock.setVisible(true);
    }

    public void onActionStocksMouseExited(MouseEvent mouseEvent) {
lblStock.setVisible(false);
    }

    public void onActionOrdersMouseExited(MouseEvent mouseEvent) {
lblOrders.setVisible(false);
    }

    public void onActionUsageMouseEntered(MouseEvent mouseEvent) {
lblUsage.setVisible(true);
    }

    public void onActionUsageMouseExited(MouseEvent mouseEvent) {
lblUsage.setVisible(false);
    }

    public void onActionSuppliersMouseEntered(MouseEvent mouseEvent) {
lblSuppliers.setVisible(true);
    }

    public void onActionSuppliersMouseExited(MouseEvent mouseEvent) {
lblSuppliers.setVisible(false);
    }

    public void onActionOrdersMouseEntered(MouseEvent mouseEvent) {
lblOrders.setVisible(true);
    }

}
