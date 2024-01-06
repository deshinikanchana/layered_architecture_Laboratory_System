package lk.ijse.laboratory.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.laboratory.BO.custom.SalaryBO;
import lk.ijse.laboratory.Dto.*;
import lk.ijse.laboratory.Dto.Tm.SalaryTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;


public class SalaryManageFormController {

    public TableView<SalaryTm> tblSalary;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colEmpId;
    public TableColumn<?,?> colWrkingHurs;
    public TableColumn<?,?> colOt;
    public TableColumn<?,?>  colSalary;
    public TextField txtWorkedHoursCount;
    public TextField txtOtHours;
    public TextField txtEmployeeName;
    public JFXButton viewAttendanceBtn;
    public JFXComboBox cmbEmployeeId;
    public TextField txtSalaryAmount;
    public TextField txtSalaryCode;

    public AnchorPane root;
    public Label lblSalary;
    public Label lblViewAttendance;
    public Label lblDesignation;
    public Label lblEmployees;
    public Label lblDate;
    SalaryBO bo = (SalaryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SALARY);

    public void initialize() throws ClassNotFoundException {
        loadEmployeeIds();
        setCellValueFactory();
        loadAllSalaries();
        generateNextSalaryId();
        setDate();
    }

    private void generateNextSalaryId() {
        try {
            String salId = bo.generateNextSalaryId();
            txtSalaryCode.setText(salId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllSalaries() throws ClassNotFoundException {
        ObservableList<SalaryTm> obList = FXCollections.observableArrayList();

        try {
            List<salaryDto> dtoList = bo.loadAllSalaries();

            Date date = Date.valueOf(LocalDate.now());
            int mon = date.getMonth() + 1;

            for(salaryDto dto : dtoList) {
                float TotalHours = bo.calculateTotalHours(dto.getEmpId(),mon);
                 String job  = (bo.SearchEmployees("empId",dto.getEmpId())).getJobId();
                designationDto des = bo.SearchDesignation("jobId",job);
                Float otHours= getOtHours(des.getWorkingHoursPerMonth(),TotalHours);
                obList.add(
                        new SalaryTm(
                                dto.getPaidDate(),
                                dto.getEmpId(),
                              TotalHours,
                                otHours,
                               dto.getAmount()
                        )
                );
            }

            tblSalary.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOt.setCellValueFactory(new PropertyValueFactory<>("OTHours"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("EmpId"));
        colWrkingHurs.setCellValueFactory(new PropertyValueFactory<>("TotalWorkedHours"));
    }

    private void loadEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<employeeDto> empList = bo.loadAllEmployees();

            for (employeeDto dto : empList) {
                obList.add(dto.getEmpId());
            }
            cmbEmployeeId.setItems(obList);

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
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/adminDashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void cmbEmployeeIdSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = (String) cmbEmployeeId.getValue();
        try {

            employeeDto dto = bo.SearchEmployees("empId",id);
            if(dto != null) {
                txtEmployeeName.setText(dto.getName());

                Date date = Date.valueOf(lblDate.getText());
                int mon = (date.getMonth() + 1);

                float TotalHours = bo.calculateTotalHours(dto.getEmpId(),mon);
                String job  = dto.getJobId();
                designationDto des = bo.SearchDesignation("jobId",job);
                Float otHours=getOtHours(des.getWorkingHoursPerMonth(),TotalHours);
               Double salary = calculateSalary(TotalHours,otHours,dto.getJobId());
                fillFields(date,dto.getName(),TotalHours,otHours,salary);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        }

    }

    private Float getOtHours(int must, float worked) {
        float ot = 0;

        if(LocalDate.now().getDayOfMonth() > 25 && worked > must ) {

            ot = worked - must;
        }

        return ot;
    }

    private void fillFields(Date date, String name, Float totalHours, Float otHours, Double salary) {
        lblDate.setText(String.valueOf(date));
        txtEmployeeName.setText(name);
        txtWorkedHoursCount.setText(String.valueOf(totalHours));
        txtOtHours.setText(String.valueOf(otHours));
        txtSalaryAmount.setText(String.valueOf(salary));
    }

    private Double calculateSalary(Float totalHours, Float otHours, String jobId) {
        Double salary = 0.0;
        Double otSal =0.0;
        try {
            designationDto dto = bo.SearchDesignation(null,jobId);
            if (dto != null) {
                int wrkingHrs = dto.getWorkingHoursPerMonth();

                    Double nop = 0.0;
                    if (LocalDate.now().getDayOfMonth() > 25 && totalHours < wrkingHrs) {
                        nop = Double.valueOf((wrkingHrs) - (totalHours) * dto.getOtPaymentsPerHour());
                        Double sal = (dto.getBasicSalary() - nop) + otSal;
                        salary = sal - (sal * (8/100));
                        return salary;
                    }else if (totalHours > wrkingHrs) {
                     otSal = Double.valueOf(otHours * (dto.getOtPaymentsPerHour()));
                        Double sal = (dto.getBasicSalary() - nop) + otSal;
                        salary = sal - (sal * (8/100));
                        return salary;
                  }
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Salary not calculated!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return salary;
    }

    private void clearFields() {
        txtEmployeeName.setText("");
        txtSalaryAmount.setText("");
        txtWorkedHoursCount.setText("");
        txtOtHours.setText("");
        cmbEmployeeId.setValue("");
        setDate();
        generateNextSalaryId();
    }

    public void onActionEmployeeManageBtn(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/lk.ijse.laboratory/assests/employee_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.centerOnScreen();
    }

    public void onActionManageDesigntionsBtn(ActionEvent event) throws IOException {
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

    public void onActionBtnPaid(ActionEvent event) throws ClassNotFoundException {
        String SalId = txtSalaryCode.getText();
        String empId = (String) cmbEmployeeId.getValue();
        Double amount = Double.valueOf(txtSalaryAmount.getText());
        Date paidDate = Date.valueOf(lblDate.getText());


        var dto = new salaryDto(SalId,empId,amount,paidDate);

        try {
            boolean isSaved = bo.SaveSalary(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Saved Successfully !!!").show();
                loadAllSalaries();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void onActionSalaryMouseEntered(MouseEvent mouseEvent) {
     lblSalary.setVisible(true);
    }

    public void onActionSalaryMouseExited(MouseEvent mouseEvent) {
lblSalary.setVisible(false);
    }

    public void onActionEmployeeMouseEntered(MouseEvent mouseEvent) {
lblEmployees.setVisible(true);
    }

    public void onActionEmployeeMouseExited(MouseEvent mouseEvent) {
lblEmployees.setVisible(false);
    }

    public void onActionDesignationMouseEntered(MouseEvent mouseEvent) {
lblDesignation.setVisible(true);
    }

    public void onActionDesignationMouseExited(MouseEvent mouseEvent) {lblDesignation.setVisible(false);}

    public void onActionViewAttendanceMouseExited(MouseEvent mouseEvent) {
lblViewAttendance.setVisible(false);
    }

    public void onActionViewAttendanceMouseEntered(MouseEvent mouseEvent) {
lblViewAttendance.setVisible(true);
    }

    public void onActionPaySlipBtn(ActionEvent event) throws JRException, SQLException, ClassNotFoundException {

        String empId = (String) cmbEmployeeId.getValue();
        employeeDto Emdto = bo.SearchEmployees("empId", empId);
        designationDto DeDto = bo.SearchDesignation(null,Emdto.getJobId());
        Date date = Date.valueOf(LocalDate.now());
        int mon = date.getMonth() + 1;
        String Today = String.valueOf(date);

      double epSal = (DeDto.getBasicSalary());
        double gr = epSal;
        double epf = epSal * 12/100;
        double etf = epSal * 3/100;
        double epfCut = DeDto.getBasicSalary()*8/100;
    double ans = epSal - epfCut;
        String basic = String.valueOf(DeDto.getBasicSalary());
        String noH = "0.0";
        String nop = "0.0";
        String totEpf = String.valueOf(epSal);
        String oth = "0.0";
        String otpay = "0.0";
        String gross = String.valueOf(gr);
        String ep = String.valueOf(epfCut);
        String ded = "0.0";
        String net = String.valueOf(ans);
        String epfS = String.valueOf(epf);
        String etfS = String.valueOf(etf);

        int count = bo.getCount(empId,mon);
        String days = String.valueOf(count);
        HashMap hashMap = new HashMap();

        hashMap.put("date", Today);
        hashMap.put("name", Emdto.getName());
        hashMap.put("designation", DeDto.getJobTitle());
        hashMap.put("empId",Emdto.getEmpId());
        hashMap.put("workedDays", days);
        hashMap.put("basicSalary", basic);
        hashMap.put("noPayHrs", noH);
        hashMap.put("nopayDeduction", nop);
        hashMap.put("totalEPF", totEpf);
        hashMap.put("otHrs", oth);
        hashMap.put("otAmount", otpay);
        hashMap.put("grossSal", gross);
        hashMap.put("epf8%", ep);
        hashMap.put("TotalDeduction", ded);
        hashMap.put("netSal", net);
        hashMap.put("EPF", epfS);
        hashMap.put("ETF", etfS);

        InputStream resourceAsStream = getClass().getResourceAsStream("/lk.ijse.laboratory/reports/Pay_Slip.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                hashMap,
                new JREmptyDataSource()
        );

        JasperViewer.viewReport(jasperPrint, false);
        clearFields();
    }
}