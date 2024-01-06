package lk.ijse.laboratory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.laboratory.BO.BOFactory;
import lk.ijse.laboratory.BO.custom.ReportsBO;
import lk.ijse.laboratory.Dto.*;
import lk.ijse.laboratory.Dto.Tm.ptTestDetailsTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static lk.ijse.laboratory.Util.EmailController.SendEmail;

public class ReportsFormController {
    public TableView<ptTestDetailsTm> tblReports;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colPatientId;
    public TableColumn<?,?> colPatientName;
    public TableColumn<?,?> colTestName;
    public TableColumn<?,?> colComment1;
    public ComboBox cmbPresId;
    public TextField txtPtName;
    public TextField txtComment;
    public ComboBox cmbTestCode;
    public Label lblDate;
    public TextField txtTestName;
    public TextField txtPtId;
    public JFXButton BtnGetReport;
    @FXML
    private AnchorPane root;

    public static String TD;
    public static String  PD;
    public static String  Prescription;
    public static boolean Stadded;
    ReportsBO bo = (ReportsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.REPORT);

    public void initialize(){
        loadPrescriptionIds();
        setCellValueFactory();
        loadAllReports();
        setDate();
    }

    private void loadAllReports() {
        ObservableList<ptTestDetailsTm> obList = FXCollections.observableArrayList();

        try {
            List<ptTestDetailsDto> dtoList = bo.loadAllReports();

            for(ptTestDetailsDto dto : dtoList) {
                prescriptionDto Prdto = bo.SearchPrescription(null,dto.getPresId());

                if (Prdto != null) {
                    patientDto pDto = bo.SearchPatients(null,Prdto.getPtId());
                    testDto TDto = bo.SearchTest("testId", dto.getTestId());


                    obList.add(
                            new ptTestDetailsTm(
                                    (Date) dto.getDate(),
                                    pDto.getPtId(),
                                    pDto.getName(),
                                    TDto.getTest(),
                                    dto.getStatus()

                            )
                    );
                }
            }

            tblReports.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colTestName.setCellValueFactory(new PropertyValueFactory<>("testName"));
        colComment1.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    private void loadTestIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
            String presId = (String) cmbPresId.getValue();
        try {
            List<ptTestDetailsDto> testList = bo.loadTestIds(presId);

            for (ptTestDetailsDto dto : testList) {
                obList.add(dto.getTestId());
            }
            cmbTestCode.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadPrescriptionIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<prescriptionDto> presList = bo.loadAllPrescriptions();

            for (prescriptionDto dto : presList) {
                obList.add(dto.getPresId());
            }
            cmbPresId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblDate.setText(date);
    }

    public void onActionBackBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/userDashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    private void clearFields(){
        setDate();
        txtPtId.setText("");
        txtPtName.setText("");
        cmbTestCode.getSelectionModel().clearSelection();
        txtTestName.setText("");
        txtComment.setText("");
    }

    public void onActionCmbPresidSearch(ActionEvent event) throws SQLException, ClassNotFoundException {
        prescriptionDto Dto = bo.SearchPrescription(null,(String) cmbPresId.getValue());
        String ptId =Dto.getPtId();
        txtPtId.setText(ptId);
        if (ptId != null) {
            try {
                patientDto dto = bo.SearchPatients(null,ptId);

                if (dto != null) {
                    txtPtName.setText(dto.getName());
                }
                loadTestIds();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Prescription not found!").show();
        }
    }

    public void onActionTestCodeSearch(ActionEvent event) {
        String id= (String) cmbTestCode.getValue();

        try {
            testDto dto = bo.SearchTest("testId",id);

            if(dto != null) {
               txtTestName.setText(dto.getTest());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Test not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionBtnGetReport(ActionEvent event) throws JRException, SQLException, MessagingException, IOException, ClassNotFoundException {
        String pres = (String) cmbPresId.getValue();
        patientDto PDto = bo.SearchPatients(null,txtPtId.getText());
        prescriptionDto PRDto = bo.SearchPrescription(null,pres);
        testDto TDto = bo.SearchTest("testId",String.valueOf(cmbTestCode.getValue()));
      sectionDto SDto = bo.SearchSections("secId", TDto.getSecId());
      machineDto MDto = bo.SearchMachines("machineId",TDto.getMachineId());


      collectingCenterDto CDto = bo.SearchCenters(null,PDto.getCcId());
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<String> ranges = new ArrayList<String>();
        ArrayList<String> unit = new ArrayList<String>();

        List<ptTestDetailsDto> ptDto  = bo.loadTestIds(pres);
        String date = null;
        for(ptTestDetailsDto dt : ptDto){
             date = String.valueOf(dt.getDate());
        }

      List<subTestDto> SubList = bo.getAllsubTests(String.valueOf(cmbTestCode.getValue()));
        for (subTestDto Sdto : SubList) {
                name.add(Sdto.getName());
            String subId = String.valueOf(Sdto.getSubTestId());

            List<refferenceRangesDto> refList = bo.getRangeList(subId);
            for(refferenceRangesDto refDto : refList){
                ranges.add(refDto.getRanges());
                unit.add(refDto.getUnit());
            }

          resultDto re = bo.SearchResults(subId, pres);
            res.add(re.getResult());
        }

        String age = null;
        LocalDate today = LocalDate.now();
        LocalDate dob = LocalDate.parse(PDto.getDob());
        int ag = Period.between(dob, today).getYears();
        if (ag >= 1) {
            age = String.valueOf(ag);
        } else {
            age = String.valueOf(Period.between(dob, today).getMonths());
        }

        String reffBy = "Self Investigation";
        if(PRDto.getRefferedBy().length() > 3){
            reffBy =PRDto.getRefferedBy();
        }
        String[] topic = new String[68];
        String tId = (String) cmbTestCode.getValue();
        String Tname =txtTestName.getText();
        String Tcomment = txtComment.getText();
        HashMap hashMap = new HashMap();

        hashMap.put("ptName", PDto.getName());
        hashMap.put("age", age);
        hashMap.put("gender", PDto.getGender());
        hashMap.put("refferedBy", reffBy);
        hashMap.put("section", SDto.getSecName());
        hashMap.put("date", date);
        hashMap.put("specimenType", TDto.getSampleType());
        hashMap.put("specimenId", pres);
        hashMap.put("ptId", PDto.getPtId());
        hashMap.put("machineName", MDto.getMachineName());
        hashMap.put("collectingCenter", CDto.getCenterName());
        hashMap.put("testName",Tname);
        hashMap.put("comment", Tcomment);

        String val = "  ";


        for (int j = 0, k = 1; j < topic.length; ) {
            topic[j] = "subTestName" + k;
            j++;
            topic[j] = "result" + k;
            j++;
            topic[j] = "referenceRange" + k;
            j++;
            topic[j] = "unit" + k;
            j++;
            k++;
        }


        for(int i =0,l=0;i< name.size();i++) {
            hashMap.put(topic[l] , name.get(i));
            l++;
            hashMap.put(topic[l],res.get(i));
             l++;
             if(ranges.size() <= name.size()) {
                 hashMap.put(topic[l], ranges.get(i));
             }
             l++;
             if(unit.size()<= name.size()) {
                 hashMap.put(topic[l], unit.get(i));
             }
             l++;
        }

        int ind = name.size() * 4;
        for(int i =ind;i< topic.length;i++){
            hashMap.put(topic[i],val);
        }
        InputStream resourceAsStream = getClass().getResourceAsStream("/lk.ijse.laboratory/reports/reps.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                hashMap,
                new JREmptyDataSource()
        );
        JasperViewer.viewReport(jasperPrint, false);
        JasperExportManager.exportReportToPdfFile(jasperPrint,"/home/kmgdk/intelij project/Medical-Laboratoey-Management-System/src/main/resources/testReports/Report.pdf");
        Date Dt = Date.valueOf(date);
        ptTestDetailsDto dto = new ptTestDetailsDto(Dt, (String) cmbPresId.getValue(), (String) cmbTestCode.getValue(),"Report Ready",txtComment.getText());
        boolean isOk = bo.UpdateReports(dto);
       if(PDto.getEmail()!= null) {
           if (PRDto.getDuePayment() == 0) {
            SendEmail(PDto.getName(), PDto.getEmail(), "Test Report","/home/kmgdk/intelij project/Medical-Laboratoey-Management-System/src/main/resources/testReports/Report.pdf" , 3);
           } else {
               SendEmail(PDto.getName(), PDto.getEmail(), "Report Alert", "Your Test Report is Ready !!!", 4);
           }
       }
        clearFields();
    }

    public void onActionSubTestResults(ActionEvent event) throws IOException, SQLException {
        TD = (String) cmbTestCode.getValue();
        PD = (String) cmbPresId.getValue();
        Prescription = String.valueOf(cmbPresId.getValue());

        Parent anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/result_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Suwasahana Medical Laboratory");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }
}
