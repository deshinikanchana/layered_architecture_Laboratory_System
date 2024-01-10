package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.SectionBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.sectionDAO;
import lk.ijse.laboratory.Dto.sectionDto;
import lk.ijse.laboratory.Entity.section;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionBOimpl implements SectionBO {
    sectionDAO SecDao = (sectionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SECTION);
    @Override
    public String generateNextSectionId() throws SQLException, ClassNotFoundException {
        return SecDao.generateNextId();
    }

    @Override
    public List<sectionDto> loadAllSections() throws SQLException, ClassNotFoundException {
        List<section> sList =  SecDao.loadAll();
        List<sectionDto> sectionList = new ArrayList<>();
        for(section sec:sList){
            sectionList.add(new sectionDto(sec.getSecId(),sec.getSecName(),sec.getConsultant()));
        }
        return sectionList;
    }

    @Override
    public int getTestCount(String secId) throws SQLException, ClassNotFoundException {
        return SecDao.getTestCount(secId);
    }

    @Override
    public boolean SaveSection(sectionDto dto) throws SQLException, ClassNotFoundException {
        return SecDao.Save(new section(dto.getSecId(),dto.getSecName(),dto.getConsultant()));
    }

    @Override
    public sectionDto SearchSection(String column, String value) throws SQLException, ClassNotFoundException {
        section sec = SecDao.Search(column,value);
        return new sectionDto(sec.getSecId(),sec.getSecName(),sec.getConsultant());
    }

    @Override
    public boolean UpdateSection(sectionDto dto) throws SQLException, ClassNotFoundException {
        return SecDao.Update(new section(dto.getSecId(),dto.getSecName(),dto.getConsultant()));
    }

    @Override
    public boolean DeleteSection(String id) throws SQLException, ClassNotFoundException {
        return SecDao.Delete(id);
    }
}
