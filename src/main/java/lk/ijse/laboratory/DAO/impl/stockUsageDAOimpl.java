package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.stockUsageDAO;
import lk.ijse.laboratory.Entity.stockUsage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data

public class stockUsageDAOimpl implements stockUsageDAO {

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<stockUsage> loadAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean Save(stockUsage entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "INSERT INTO stock_usage VALUES(?, ?, ?)", entity.getTestId(), entity.getItemCode(), entity.getQtyPerTest());
    }

    @Override
    public stockUsage Search(String code, String nic) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<stockUsage> getUsages(String testId) throws SQLException, ClassNotFoundException {
        List<stockUsage> usageList = new ArrayList<>();
            ResultSet res = SQLUtil.execute("SELECT * FROM stock_usage WHERE testId = ?",testId);

            while (res.next()) {
                usageList.add(new stockUsage(res.getString(1),res.getString(2),res.getInt(3)));;
            }

        return usageList;
    }

    @Override
    public boolean Update(stockUsage entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "UPDATE stock_item SET qtyOnHand = qtyOnHand - ?  WHERE itemCode = ?", entity.getQtyPerTest(), entity.getItemCode());
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
