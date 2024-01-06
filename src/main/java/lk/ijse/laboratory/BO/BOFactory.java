package lk.ijse.laboratory.BO;

import lk.ijse.laboratory.BO.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)?boFactory=
                new BOFactory():boFactory;

    }
    public enum BOTypes{
        ADMIN,ATTENDANCE,COLLECTINGCENTER,DESIGNATION,EMPLOYEE,FORGOTPW,INSTRUCTION,LOGIN,MACHINE,MYPROFILE,NEWACCOUNT,ORDERS,PATIENT,PRESCRIPTION,REFFERENCERANGES,REPORT,RESETPW,RESULT,SALARY,SECTION,STOCK,STOCKUSAGE,SUPPLIER,TESTDETAILS,TEST,USER,USERDB,VERIFICATION,VIEWATTENDANCE
    }
    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case ADMIN:
                return new AdminBOimpl();
            case ATTENDANCE:
                return new AttendanceBOimpl();
            case COLLECTINGCENTER:
                return new CollectingCenterBOimpl();
            case DESIGNATION:
                return new DesignationBOimpl();
            case EMPLOYEE:
                return new EmployeeBOimpl();
            case FORGOTPW:
                return new ForgotPasswordBOimpl();
            case INSTRUCTION:
                return new InstructionsBOimpl();
            case LOGIN:
                return new LoginBOimpl();
            case MACHINE:
                return new MachineBOimpl();
            case MYPROFILE:
                return new MyProfileBOimpl();
            case NEWACCOUNT:
                return new NewAccountBOimpl();
            case ORDERS:
                return new OrdersBOimpl();
            case PATIENT:
                return new PatientsBOimpl();
            case PRESCRIPTION:
                return new PrescriptionBOimpl();
            case REFFERENCERANGES:
                return new RefferenceRangesBOimpl();
            case REPORT:
                return new ReportBOimpl();
            case RESETPW:
                return new ResestPasswordBOimpl();
            case RESULT:
                return new ResultBOimpl();
            case SALARY:
                return new SalaryBOimpl();
            case SECTION:
                return new SectionBOimpl();
            case STOCK:
                return new StocksBOimpl();
            case STOCKUSAGE:
                return new StockUsageBOimpl();
            case SUPPLIER:
                return new SupplierBOimpl();
            case TESTDETAILS:
                return new TestDetailsBOimpl();
            case TEST:
                return new TestBOimpl();
            case USER:
                return new UserBOimpl();
            case USERDB:
                return new UserDashBoardBOimpl();
            case VERIFICATION:
                return new VerificationBOimpl();
            case VIEWATTENDANCE:
                return new ViewAttendanceBOimpl();
            default:
                return null;
        }
    }
}
