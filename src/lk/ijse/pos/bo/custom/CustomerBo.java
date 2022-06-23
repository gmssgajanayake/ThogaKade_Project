package lk.ijse.pos.bo.custom;

import lk.ijse.pos.dto.CustomerDto;
import lk.ijse.pos.dto.ItemDto;

import java.util.ArrayList;

public interface CustomerBo {
    public boolean saveCustomer(CustomerDto customerDTO);
    public boolean updateCustomer(CustomerDto customerDTO);
    public boolean deleteCustomer(CustomerDto customerDTO);
    public ArrayList<ItemDto> searchCustomer(String search);
}
