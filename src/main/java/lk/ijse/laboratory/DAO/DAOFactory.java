package lk.ijse.laboratory.DAO;
import lk.ijse.laboratory.DAO.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){
    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory
                =new DAOFactory():daoFactory;
    }

    public enum DAOTypes {
        ATTENDANCE,COLLECTINGCENTER,DESIGNATION,EMPLOYEE,INSTRUCTION,ITEMORDERDETAILS,MACHINE,ORDERSAVE,ORDERS,PATIENT,PRESCRIPTION,REFFERENCERANGES,REPORT,RESULT,SALARY,SECTION,STOCKITEM,STOCKUSAGE,SUBTEST,SUPPLIER,TEMPUSER,TEST,USER
    }
    public SuperDAO getDAO(DAOFactory.DAOTypes daoTypes){
        switch (daoTypes){

            case ATTENDANCE:
                return new attendanceDAOimpl();
            case COLLECTINGCENTER:
                return new collectingCenterDAOimpl();
            case DESIGNATION:
                return new designationDAOimpl();
            case EMPLOYEE:
                return new employeeDAOimpl();
            case INSTRUCTION:
                return new instructionDAOimpl();
            case ITEMORDERDETAILS:
                return new itemOrderDetailsDAOimpl();
            case MACHINE:
                return new machineDAOimpl();
            case ORDERSAVE:
                return new OrderSaveDAOimpl();
            case ORDERS:
                return new ordersDAOimpl();
            case PATIENT:
                return new patientDAOimpl();
            case PRESCRIPTION:
                return new prescriptionDAOimpl();
            case REFFERENCERANGES:
                return new refferenceRangeDAOimpl();
            case REPORT:
                return new reportDAOimpl();
            case RESULT:
                return new resultDAOimpl();
            case SALARY:
                return new salaryDAOimpl();
            case SECTION:
                return new sectionDAOimpl();
            case STOCKITEM:
                return new stockItemDAOimpl();
            case STOCKUSAGE:
                return new stockUsageDAOimpl();
            case SUBTEST:
                return new subTestDAOimpl();
            case SUPPLIER:
                return new supplierDAOimpl();
            case TEMPUSER:
                return new tempUserDAOimpl();
            case TEST:
                return new testDAOimpl();
            case USER:
                return new userDAOimpl();
            default:
                return null;
        }
    }

}
