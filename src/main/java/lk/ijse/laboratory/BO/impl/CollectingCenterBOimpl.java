package lk.ijse.laboratory.BO.impl;

import lk.ijse.laboratory.BO.custom.CollectingCenterBO;
import lk.ijse.laboratory.DAO.DAOFactory;
import lk.ijse.laboratory.DAO.custom.collectingCenterDAO;
import lk.ijse.laboratory.Dto.collectingCenterDto;
import lk.ijse.laboratory.Entity.collectingCenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectingCenterBOimpl implements CollectingCenterBO {
    collectingCenterDAO dao = (collectingCenterDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COLLECTINGCENTER);
    @Override
    public String generateNextCenterId() throws SQLException, ClassNotFoundException {
        return dao.generateNextId();
    }

    @Override
    public List<collectingCenterDto> loadAllCenters() throws SQLException, ClassNotFoundException {
        List<collectingCenter> centerList = dao.loadAll();
        List<collectingCenterDto> CcList = new ArrayList<>();
        for(collectingCenter cc : centerList){
            CcList.add(new collectingCenterDto(cc.getCcId(),cc.getCenterName(),cc.getAddress(),cc.getTelNo(),cc.getEmail()));
        }
        return CcList;
    }

    @Override
    public int getSampleCount(String CcId) throws SQLException, ClassNotFoundException {
        return dao.getSampleCount(CcId);
    }

    @Override
    public boolean SaveCenter(collectingCenterDto dto) throws SQLException, ClassNotFoundException {
        return dao.Save(new collectingCenter(dto.getCcId(),dto.getCenterName(),dto.getAddress(),dto.getTelNo(),dto.getEmail()));
    }

    @Override
    public collectingCenterDto SearchCenter(String code, String nic) throws SQLException, ClassNotFoundException {
        collectingCenter cc = dao.Search(code,nic);
        return new collectingCenterDto(cc.getCcId(),cc.getCenterName(),cc.getAddress(),cc.getTelNo(),cc.getEmail());
    }

    @Override
    public boolean UpdateCenter(collectingCenterDto dto) throws SQLException, ClassNotFoundException {
        return dao.Update(new collectingCenter(dto.getCcId(),dto.getCenterName(),dto.getAddress(),dto.getTelNo(),dto.getEmail()));
    }

    @Override
    public boolean DeleteCenter(String id) throws SQLException, ClassNotFoundException {
        return dao.Delete(id);
    }
}
