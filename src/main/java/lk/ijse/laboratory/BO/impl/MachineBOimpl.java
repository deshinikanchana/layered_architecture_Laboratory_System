package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.MachinesBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.machineDAO;
import lk.ijse.laboratory.DAO.custom.sectionDAO;
import lk.ijse.laboratory.Dto.machineDto;
import lk.ijse.laboratory.Dto.sectionDto;
import lk.ijse.laboratory.Entity.machine;
import lk.ijse.laboratory.Entity.section;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineBOimpl implements MachinesBO {
    machineDAO mDao = (machineDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MACHINE);
    sectionDAO sDao = (sectionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SECTION);
    @Override
    public String generateNextMachineId() throws SQLException, ClassNotFoundException {
        return mDao.generateNextId();
    }

    @Override
    public List<machineDto> loadAllMachines() throws SQLException, ClassNotFoundException {
        List<machine> mList = mDao.loadAll();
        List<machineDto> machineList = new ArrayList<>();
        for(machine mc:mList){
            machineList.add(new machineDto(mc.getMachineId(),mc.getSecId(),mc.getMachineName(),mc.getStatus()));
        }
        return machineList;
    }

    @Override
    public List<sectionDto> loadAllSections() throws SQLException, ClassNotFoundException {
        List<section> sList = sDao.loadAll();
        List<sectionDto> secList = new ArrayList<>();
        for(section sc:sList){
            secList.add(new sectionDto(sc.getSecId(),sc.getSecName(),sc.getConsultant()));
        }
        return secList;
    }

    @Override
    public boolean SaveMachine(machineDto mc) throws SQLException, ClassNotFoundException {
        return mDao.Save(new machine(mc.getMachineId(),mc.getSecId(),mc.getMachineName(),mc.getStatus()));
    }

    @Override
    public machineDto SearchMachine(String column, String value) throws SQLException, ClassNotFoundException {
        machine entity = mDao.Search(column,value);
        return new machineDto(entity.getMachineId(),entity.getSecId(),entity.getMachineName(),entity.getStatus());
    }

    @Override
    public sectionDto SearchSection(String column, String value) throws SQLException, ClassNotFoundException {
        section entity = sDao.Search(column,value);
        return new sectionDto(entity.getSecId(),entity.getSecName(),entity.getConsultant());
    }

    @Override
    public boolean UpdateMachine(machineDto dto) throws SQLException, ClassNotFoundException {
        return mDao.Update(new machine(dto.getMachineId(),dto.getSecId(),dto.getMachineName(),dto.getStatus()));
    }

    @Override
    public boolean DeleteMachine(String id) throws SQLException, ClassNotFoundException {
        return mDao.Delete(id);
    }
}
