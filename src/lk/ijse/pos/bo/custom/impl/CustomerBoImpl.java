package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.CustomerBo;
import lk.ijse.pos.dao.DaoFactory;
import lk.ijse.pos.dao.custom.impl.CustomerDaoImpl;
import lk.ijse.pos.dto.CustomerDto;
import lk.ijse.pos.dto.ItemDto;
import lk.ijse.pos.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBoImpl implements CustomerBo {

    private final CustomerDaoImpl customerDao= DaoFactory.getInstance().getDao(DaoFactory.DaoType.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDao.save(new Customer(
                customerDto.getId(),
                customerDto.getName(),
                customerDto.getAddress(),
                customerDto.getSalary()
        ));
    }

    @Override
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

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDao.delete(id);
    }

    @Override
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
}
