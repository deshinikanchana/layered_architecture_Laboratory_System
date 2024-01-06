package lk.ijse.laboratory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.InstructionBO;
import lk.ijse.laboratory.Dto.Tm.instructionTM;
import lk.ijse.laboratory.Dto.instructionDto;
import lk.ijse.laboratory.Dto.testDto;

import java.sql.SQLException;
import java.util.List;

public class InstructionsFormController {

    public AnchorPane pane;
    public TextField txtIns;
    public TableView tblInstructions;
    public TableColumn colCode;
    public TableColumn colInstruction;

    public JFXButton saveBtn;

    public JFXButton updateBtn;

    public JFXButton deleteBtn;
    public TextField txtTestCode;
    public TextField txtTestName;
    public TableColumn colNo;
    public Label lblNo;
    InstructionBO bo = (InstructionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.INSTRUCTION);
    public void initialize() throws SQLException, ClassNotFoundException {
        generatenextInsNo();
        setCode();
        BtnActivation();
        setCellValueFactory();
        loadAllTbl();
        tblInstructions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            instructionTM ins= (instructionTM) tblInstructions.getSelectionModel().getSelectedItem();
            txtTestCode.setText(ins.getCode());
            try {
                onActionTestCode(new ActionEvent());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            txtIns.setText(ins.getInstruction());
            lblNo.setText(String.valueOf(ins.getNo()));
        });

    }

    private void generatenextInsNo() throws SQLException, ClassNotFoundException {
        String no = bo.generateNextInstructionId();
        lblNo.setText(String.valueOf(no));
    }

    private void setCellValueFactory() {
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colInstruction.setCellValueFactory(new PropertyValueFactory<>("instruction"));
    }

    private void loadAllTbl() {
        ObservableList<instructionTM> obList = FXCollections.observableArrayList();

        try {
            List<instructionDto> dtoList = bo.loadAllInstructions();
            for(instructionDto dto : dtoList) {
                    obList.add(
                            new instructionTM(
                                    dto.getNo(),
                                    dto.getTestId(),
                                   dto.getInstrucion()
                            )
                    );
                }
            tblInstructions.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void BtnActivation() {
    if(txtIns.getText()!= null){
        deleteBtn.setDisable(false);
        saveBtn.setDisable(false);
        updateBtn.setDisable(false);
    }else{
        deleteBtn.setDisable(true);
        saveBtn.setDisable(true);
        updateBtn.setDisable(true);
    }
    }

    private void setCode() throws SQLException, ClassNotFoundException {
        List<testDto> tests = bo.loadAllTests();
        txtTestCode.setText(tests.get(tests.size()-1).getTestId());
        onActionTestCode(new ActionEvent());
    }

    public void onActionSaveBtn(ActionEvent event) {
        int no = Integer.parseInt(lblNo.getText());
        String code = txtTestCode.getText();
        String ins = txtIns.getText();

        var dto = new instructionDto(no,code,ins);

        try {
            boolean isSaved = bo.SaveInstructions(dto);
            if (isSaved) {
                loadAllTbl();
                txtIns.setText(" ");
                BtnActivation();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionUpdateBtn(ActionEvent event) {
        int no = Integer.parseInt(lblNo.getText());
        String code = txtTestCode.getText();
        String ins = txtIns.getText();

        var dto = new instructionDto(no,code,ins);

        try {
            boolean isUpdated = bo.UpdateInstructions(dto);
            if (isUpdated) {
                loadAllTbl();
                txtIns.setText(" ");
                BtnActivation();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionDeleteBtn(ActionEvent event) {
        String no = lblNo.getText();

        try {
            boolean isDeleted = bo.DeleteInstructions(no);

            if(isDeleted) {
                loadAllTbl();
                txtIns.setText(" ");
                BtnActivation();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionTestCode(ActionEvent event) throws SQLException, ClassNotFoundException {

        String Code = txtTestCode.getText();
        testDto dto  = bo.SearchTest("testId",Code);
        txtTestName.setText(dto.getTest());
    }
}
