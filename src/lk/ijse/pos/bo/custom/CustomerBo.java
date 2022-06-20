package lk.ijse.pos.bo.custom;

import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;

import java.util.ArrayList;

public interface CustomerBo {
    public boolean saveCustomer(CustomerDTO customerDTO);
    public boolean updateCustomer(CustomerDTO customerDTO);
    public boolean deleteCustomer(CustomerDTO customerDTO);
    public ArrayList<ItemDTO> searchCustomer(String search);
}
