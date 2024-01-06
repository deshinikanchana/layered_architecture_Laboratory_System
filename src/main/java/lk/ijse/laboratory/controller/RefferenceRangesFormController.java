package lk.ijse.laboratory.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.RefferenceRangesBO;
import lk.ijse.laboratory.Dto.Tm.refferenceRangesTm;
import lk.ijse.laboratory.Dto.refferenceRangesDto;
import lk.ijse.laboratory.Dto.subTestDto;
import lk.ijse.laboratory.Dto.testDto;
import java.sql.SQLException;
import java.util.List;

public class RefferenceRangesFormController {
    public AnchorPane pane;
    public TextField txtRefRange;
    public TableView<refferenceRangesTm> tblRefRanges;
    public TableColumn<?,?> colCode;
    public TableColumn<?,?> colRefRange;
    public TextField txtTestCode;
    public ComboBox cmbSubTestId;
    public TextField txtSubTestName;
    public TableColumn<?,?> colSubTestCode;
    public TableColumn<?,?> colSubTestName;
    public TableColumn<?,?> colUnit;
    public TextField txtUnit;
    public TextField txtSubTestCode;
    RefferenceRangesBO bo = (RefferenceRangesBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.REFFERENCERANGES);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        loadAllRefferenceRanges();
        setTestCode();
        setSubTestCode();
        generateNextStId();
    }

    private void generateNextStId() {
        try {
            String testId = bo.generateNextSubTestId();
            txtSubTestCode.setText(testId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setSubTestCode() throws SQLException, ClassNotFoundException {
        List<subTestDto> testList = bo.loadAllSubTest();
        String id =testList.get(testList.size()-1).getSubTestId();
        if (id != null) {
            String[] split = id.split("T");
            int code = Integer.parseInt(split[1]);
            if (code < 10) {
                code++;
                txtSubTestCode.setText("ST00" + code);
            } else {
                code++;
                txtSubTestCode.setText("ST0" + code);
            }
        }
        txtSubTestCode.setText("ST001");
    }


    private void setTestCode() {

        try {
           List<testDto> testList = bo.loadAllTest();
            String id =testList.get(testList.size()-1).getTestId();
            txtTestCode.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllRefferenceRanges() {

        ObservableList<refferenceRangesTm> obList = FXCollections.observableArrayList();

        try {
            List<refferenceRangesDto> dtoList = bo.loadAllRefferenceRanges();

            for(refferenceRangesDto dto : dtoList) {
               subTestDto sdto = bo.SearchSubTest(null,dto.getSubTestId());
                obList.add(
                        new refferenceRangesTm(
                                 sdto.getTestId(),
                                sdto.getSubTestId(),
                                sdto.getName(),
                                dto.getRanges(),
                                dto.getUnit()
                        )
                );
            }

            tblRefRanges.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("testCode"));
        colSubTestCode.setCellValueFactory(new PropertyValueFactory<>("subTestCode"));
        colSubTestName.setCellValueFactory(new PropertyValueFactory<>("subTest"));
        colRefRange.setCellValueFactory(new PropertyValueFactory<>("refferenceRange"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
    }

    public void onActionSaveBtn(ActionEvent event) {
        String testId = String.valueOf(cmbSubTestId.getValue());
        String ranges = txtRefRange.getText();
        String unit = txtUnit.getText();


        var dto = new refferenceRangesDto(testId,ranges,unit);

        try {
            boolean isSaved = bo.SaveRefferenceRanges(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Refference Ranges saved Successfully !!!").show();
                loadAllRefferenceRanges();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void clearFields() {
        txtTestCode.setText("");
        txtUnit.setText("");
        txtRefRange.setText("");
        txtSubTestName.setText("");
        cmbSubTestId.getItems().clear();;
        txtSubTestCode.setText(" ");
    }

    public void onActionUpdateBtn(ActionEvent event) {
        String testId = (String) cmbSubTestId.getValue();
        String ranges = txtRefRange.getText();
        String unit = txtUnit.getText();


        var dto = new refferenceRangesDto(testId,ranges,unit);
        try {
            boolean isUpdated = bo.UpdateRefferenceRanges(dto);

            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Refference Range updated !!!").show();
                loadAllRefferenceRanges();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void onActionDeleteBtn(ActionEvent event) {
        String id = (String) cmbSubTestId.getValue();

        try {
            boolean isDeleted = bo.DeleteRefferenceRanges(id);

            if(isDeleted) {
                tblRefRanges.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Refference Range deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionReferenceRange(ActionEvent event) {
        txtUnit.requestFocus();
    }

    public void onActionSubTestName(ActionEvent event) {
        txtRefRange.requestFocus();
    }
}
