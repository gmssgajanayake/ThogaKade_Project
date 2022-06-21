package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.CustomerDao;
import lk.ijse.pos.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public ArrayList<Customer> searchCustomer(String txtSearch) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerDtoArrayList=new ArrayList<>();
        txtSearch="%"+txtSearch+"%";
        ResultSet resultSet =
                CrudUtil.execute("SELECT * FROM customer WHERE name LIKE? OR address LIKE?", txtSearch, txtSearch);
        while (resultSet.next()){
            customerDtoArrayList.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return customerDtoArrayList;
    }

    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO customer VALUES (?,?,?,?)",
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getSalary()
        );
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE customer SET name=?, address=?, salary=? WHERE id=?",
                customer.getName(),
                customer.getAddress(),
                customer.getSalary(),
                customer.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM customer WHERE id=?",id);
    }

    @Override
    public Customer get(String s) {
        return null;
    }

    @Override
    public ArrayList<Customer> getAll() {
        return null;
    }
}
