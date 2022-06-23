package lk.ijse.pos.bo.custom;

import lk.ijse.pos.dto.CustomerDto;
import lk.ijse.pos.dto.ItemDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBo {

    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    public ArrayList<CustomerDto> searchCustomer(String search) throws SQLException, ClassNotFoundException;
}
