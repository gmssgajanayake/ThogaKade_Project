package lk.ijse.pos.dao;

import lk.ijse.pos.dao.custom.impl.CustomerDaoImpl;
import lk.ijse.pos.dao.custom.impl.ItemImpl;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.SystemUserDTO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.util.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


public class DatabaseAccessCode {

    private final CustomerDaoImpl customerDao=DaoFactory.getInstance().getDao(DaoFactory.DaoType.CUSTOMER);
    private final ItemImpl ItemDao=DaoFactory.getInstance().getDao(DaoFactory.DaoType.ITEM);

    //Register a new system user
    public boolean createSystemUser(SystemUserDTO systemUserDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO system_user VALUES (?,?,?)",
                systemUserDTO.getName(),
                systemUserDTO.getEmail(),
                systemUserDTO.getPassword()
        );
    }

    //Find a system user (Email=)
    public SystemUserDTO getSystemUser(String email) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM system_user WHERE email=?", email);
        return resultSet.next() ? new SystemUserDTO(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3)
        ): null;
    }

    //Register a new customer
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDao.save(new Customer(
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getSalary()
        ));
    }

    //System user's password reset
    public void resetPassword(SystemUserDTO systemUserDTO) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("UPDATE system_user SET password=? WHERE email=?"
                ,systemUserDTO.getPassword(),systemUserDTO.getEmail());
    }

    //Customers find when searching
    public ArrayList<CustomerDTO> searchCustomer(String txtSearch) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customerDTOArrayList=new ArrayList<>();
        ArrayList<Customer> customerArrayList=customerDao.searchCustomer(txtSearch);
        for (Customer customer:customerArrayList) {
            customerDTOArrayList.add(new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getSalary()
            ));
        }
        return customerDTOArrayList;
    }

    //Update customer
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDao.update(
                new Customer(
                        customerDTO.getId(),
                        customerDTO.getName(),
                        customerDTO.getAddress(),
                        customerDTO.getSalary()
                )
        );
    }

    //Delete customer
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDao.delete(id);
    }

    //Save a new item
    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return ItemDao.save(new Item(
                itemDTO.getCode(),
                itemDTO.getDescription(),
                itemDTO.getUnitPrice(),
                itemDTO.getQtyOnHand()
        ));
    }

    //Update item
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return ItemDao.update(new Item(
                itemDTO.getCode(),
                itemDTO.getDescription(),
                itemDTO.getUnitPrice(),
                itemDTO.getQtyOnHand()
        ));
    }

   // Items find when searching
    public ArrayList<ItemDTO> searchItem(String txtSearch) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemDTOArrayList=new ArrayList<>();
        ArrayList<Item> itemArrayList=ItemDao.searchItem(txtSearch);
        for (Item item:itemArrayList) {
            itemDTOArrayList.add(new ItemDTO(
               item.getCode(),
               item.getDescription(),
               item.getUnitPrice(),
               item.getQtyOnHand()
            ));
        }
        return itemDTOArrayList;
    }

    //Delete item
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return ItemDao.delete(code);
    }
}
