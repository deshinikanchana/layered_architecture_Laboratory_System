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
import lk.ijse.laboratory.BO.custom.SuppliersBO;
import lk.ijse.laboratory.Dto.Tm.supplierTm;
import lk.ijse.laboratory.Dto.supplierDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SuppliersFormController {
    public TableView<supplierTm> tblSuppliers;
    public TableColumn<?,?> colSupId;
    public TableColumn<?,?> colSupName;
    public TableColumn<?,?> colCategory;
    public TableColumn<?,?> colSupEmail;
    public TableColumn<?,?> colTelNo;
    public TextField txtSupplierId;

    public TextField txtEmail;
    public TextField txtName;
    public TextField txtTelNo;
    public Label lblStock;
    public Label lblUsage;
    public Label lblSuppliers;
    public Label lblOrders;
    public JFXComboBox cmbCategory;
    @FXML
    private AnchorPane root;

    SuppliersBO bo = (SuppliersBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    public void initialize() {
        loadCategories();
        setCellValueFactory();
        loadAllSuppliers();
        generateNextSupplierId();
    }

    private void generateNextSupplierId() {
        try {
            String supId = bo.generateNextSupplierId();
            txtSupplierId.setText(supId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllSuppliers() {

        ObservableList<supplierTm> obList = FXCollections.observableArrayList();

        try {
            List<supplierDto> dtoList = bo.loadAllSuppliers();

            for(supplierDto dto : dtoList) {
                obList.add(
                        new supplierTm(
                               dto.getSupId(),
                                dto.getName(),
                                dto.getCategory(),
                                dto.getEmail(),
                                dto.getTelNo()

                        )
                );
            }

            tblSuppliers.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSupEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelNo.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void loadCategories() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> categoryList = bo.loadcategories();

            for (String cat : categoryList) {
                obList.add(cat);
            }
            cmbCategory.setItems(obList);

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

    public void onActionStockUsageBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/stockUsage_form.fxml"));
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

    public void onActionSaveBtn(ActionEvent event) {
        String supId = txtSupplierId.getText();
        String supName = txtName.getText();
        String tel = txtTelNo.getText();
        String email = txtEmail.getText();
        String category = (String) cmbCategory.getValue();

        var dto = new supplierDto(supId,supName,tel,email,category);

        try {
            boolean isSaved = bo.SaveSupplier(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier saved Successfully !!!").show();
               loadAllSuppliers();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearFields() {
        txtEmail.setText("");
        txtName.setText("");
        txtTelNo.setText("");
        cmbCategory.setValue("");
        generateNextSupplierId();
    }

    public void onActionUpdateBtn(ActionEvent event) {
        String supId = txtSupplierId.getText();
        String supName = txtName.getText();
        String tel = txtTelNo.getText();
        String email = txtEmail.getText();
        String category = (String) cmbCategory.getValue();

        var dto = new supplierDto(supId,supName,tel,email,category);

        try {
            boolean isUpdated = bo.UpdateSupplier(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier Updated Successfully !!!").show();
                loadAllSuppliers();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionDeleteBtn(ActionEvent event) {
        String id = txtSupplierId.getText();

        try {
            boolean isDeleted = bo.DeleteSupplier(id);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted !!!").show();
                clearFields();
                loadAllSuppliers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void onActionClearBtn(ActionEvent event) {clearFields();
    }

    public void onActionStockBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/stocks_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionSearchTxtName(ActionEvent event) {
        String name= txtName.getText();

        try {
            supplierDto dto = bo.SearchSupplier("name",name);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Supplier not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillFields(supplierDto dto) {
        txtName.setText(dto.getName());
        txtSupplierId.setText(dto.getSupId());
        txtTelNo.setText(dto.getTelNo());
        txtEmail.setText(dto.getEmail());
        cmbCategory.setValue(dto.getCategory());
    }

    public void OnActionSearchTxtSupplierId(ActionEvent event) {
        String id= txtSupplierId.getText();


        try {
            supplierDto dto = bo.SearchSupplier("supId",id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Supplier not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionUsageMouseExited(MouseEvent mouseEvent) {
lblUsage.setVisible(false);
    }

    public void onActionUsageMouseEntered(MouseEvent mouseEvent) {
lblUsage.setVisible(true);
    }

    public void onActionSuppliersMouseExited(MouseEvent mouseEvent) {
lblSuppliers.setVisible(false);
    }

    public void onActionSuppliersMouseEntered(MouseEvent mouseEvent) {
lblSuppliers.setVisible(true);
    }

    public void onActionOrdersMouseEntered(MouseEvent mouseEvent) {
lblOrders.setVisible(true);
    }

    public void onActionOrdersMouseExited(MouseEvent mouseEvent) {
lblOrders.setVisible(false);
    }

    public void onActionStocksMouseExited(MouseEvent mouseEvent) {
lblStock.setVisible(false);
    }

    public void onActionStocksMouseEntered(MouseEvent mouseEvent) {
lblStock.setVisible(true);
    }
}
