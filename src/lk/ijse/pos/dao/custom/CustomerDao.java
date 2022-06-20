package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDao;
import lk.ijse.pos.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDao extends CrudDao<Customer,String> {
    public ArrayList<Customer> searchCustomer(String search) throws SQLException, ClassNotFoundException;
}
