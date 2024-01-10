package lk.ijse.laboratory.DAO.impl;

import lk.ijse.laboratory.DAO.SQLUtil;
import lk.ijse.laboratory.DAO.custom.stockItemDAO;
import lk.ijse.laboratory.Entity.stockItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data

public class stockItemDAOimpl implements stockItemDAO {

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT itemCode FROM stock_item ORDER BY itemCode DESC LIMIT 1");

        if (resultSet.next()) {
            return splitItemId(resultSet.getString(1));
        }
        return splitItemId(null);
    }

   private String splitItemId(String currentItemId) {
        if (currentItemId != null) {
            String[] split = currentItemId.split("I");
            int id = Integer.parseInt(split[1]);
            if (id < 10) {
                id++;
                return "I00" + id;
            } else {
                id++;
                return "I0" + id;
            }
        }
        return "I001";
    }

    @Override
    public int warningStocks() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(itemCode) FROM stock_item WHERE qtyOnHand <= warningLevel");

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public List<String> loadcategories() throws SQLException, ClassNotFoundException {
        List<String> list = new ArrayList<>();
        ResultSet res = SQLUtil.execute("SELECT distinct category from stock_item");
        while (res.next()) {
            list.add(res.getString(1));
        }
        return list;
    }

    @Override
    public boolean Save(stockItem entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO stock_item VALUES(?, ?, ?, ?, ?, ?)", entity.getItemCode(), entity.getUserId(), entity.getDescription(), entity.getQtyOnHand(), entity.getCategory(), entity.getWarningLevel());
    }

    @Override
    public List<stockItem> loadAll() throws SQLException, ClassNotFoundException {
        List<stockItem> dtoList = new ArrayList<>();
        ResultSet res = SQLUtil.execute("SELECT * FROM stock_item");

        while (res.next()) {
            dtoList.add(new stockItem(res.getString(1),res.getString(2),res.getString(3),res.getInt(4),res.getString(5),res.getInt(6)));
        }
        return dtoList;
    }

    @Override
    public boolean Update(stockItem entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE stock_item SET description = ?, qtyOnHand = ?, category = ? , warningLevel = ? WHERE itemCode = ?", entity.getDescription(), entity.getQtyOnHand(), entity.getCategory(), entity.getWarningLevel(), entity.getItemCode());
    }

    @Override
    public boolean Delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM stock_item WHERE itemCode = ?",id);
    }

    @Override
    public stockItem Search(String col, String value) throws SQLException, ClassNotFoundException {
        ResultSet res = SQLUtil.execute( "SELECT * FROM stock_item WHERE " + col + " = ?", value);
        res.next();
        return new stockItem(res.getString(1),res.getString(2),res.getString(3),res.getInt(4),res.getString(5),res.getInt(6));
    }
}
