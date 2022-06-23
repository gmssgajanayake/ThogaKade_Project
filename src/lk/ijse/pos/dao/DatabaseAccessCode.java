package lk.ijse.pos.dao;

import lk.ijse.pos.dao.custom.impl.CustomerDaoImpl;
import lk.ijse.pos.dao.custom.impl.ItemDaoImpl;
import lk.ijse.pos.dao.custom.impl.SystemUserDaoImpl;
import lk.ijse.pos.dto.CustomerDto;
import lk.ijse.pos.dto.ItemDto;
import lk.ijse.pos.dto.SystemUserDto;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.SystemUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DatabaseAccessCode {

    private final CustomerDaoImpl customerDao=DaoFactory.getInstance().getDao(DaoFactory.DaoType.CUSTOMER);
    private final ItemDaoImpl ItemDao=DaoFactory.getInstance().getDao(DaoFactory.DaoType.ITEM);
    private final SystemUserDaoImpl systemUserDao=DaoFactory.getInstance().getDao(DaoFactory.DaoType.SYSTEM_USER);

    //Register a new system user
    public boolean createSystemUser(SystemUserDto systemUserDto) throws SQLException, ClassNotFoundException {
        return systemUserDao.save(new SystemUser(
                systemUserDto.getName(),
                systemUserDto.getEmail(),
                systemUserDto.getPassword()
        ));
    }

    //Find a system user (Email=)
    public SystemUserDto getSystemUser(String email) throws SQLException, ClassNotFoundException {
        SystemUser systemUser= systemUserDao.get(email);
        return new SystemUserDto(
                systemUser.getName(),
                systemUser.getEmail(),
                systemUser.getPassword()
        );
    }

    //Register a new customer
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDao.save(new Customer(
                customerDto.getId(),
                customerDto.getName(),
                customerDto.getAddress(),
                customerDto.getSalary()
        ));
    }

    //System user's password reset
    public void resetPassword(SystemUserDto systemUserDto) throws SQLException, ClassNotFoundException {
       systemUserDao.resetPassword(new SystemUser(
               systemUserDto.getName(),
               systemUserDto.getEmail(),
               systemUserDto.getPassword()
       ));
    }

    //Customers find when searching
    public ArrayList<CustomerDto> searchCustomer(String txtSearch) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> customerDtoArrayList=new ArrayList<>();
        ArrayList<Customer> customerArrayList=customerDao.searchCustomer(txtSearch);
        for (Customer customer:customerArrayList) {
            customerDtoArrayList.add(new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getSalary()
            ));
        }
        return customerDtoArrayList;
    }

    //Update customer
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDao.update(
                new Customer(
                        customerDto.getId(),
                        customerDto.getName(),
                        customerDto.getAddress(),
                        customerDto.getSalary()
                )
        );
    }

    //Delete customer
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDao.delete(id);
    }

    //Save a new item
    public boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return ItemDao.save(new Item(
                itemDto.getCode(),
                itemDto.getDescription(),
                itemDto.getUnitPrice(),
                itemDto.getQtyOnHand()
        ));
    }

    //Update item
    public boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return ItemDao.update(new Item(
                itemDto.getCode(),
                itemDto.getDescription(),
                itemDto.getUnitPrice(),
                itemDto.getQtyOnHand()
        ));
    }

   // Items find when searching
    public ArrayList<ItemDto> searchItem(String txtSearch) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDto> itemDtoArrayList=new ArrayList<>();
        ArrayList<Item> itemArrayList=ItemDao.searchItem(txtSearch);
        for (Item item:itemArrayList) {
            itemDtoArrayList.add(new ItemDto(
               item.getCode(),
               item.getDescription(),
               item.getUnitPrice(),
               item.getQtyOnHand()
            ));
        }
        return itemDtoArrayList;
    }

    //Delete item
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return ItemDao.delete(code);
    }
}
