package lk.ijse.laboratory.DAO.custom;

import lk.ijse.laboratory.DAO.crudDAO;
import lk.ijse.laboratory.Entity.user;

import java.sql.*;

public interface userDAO extends crudDAO<user> {
   user getLastAdmin() throws SQLException, ClassNotFoundException;
   user CheckCredential(user entity) throws SQLException, ClassNotFoundException;

   }
