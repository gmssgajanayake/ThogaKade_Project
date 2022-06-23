package lk.ijse.pos.bo.custom;

import lk.ijse.pos.dto.CustomerDto;
import lk.ijse.pos.dto.ItemDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBo {

    public boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException;

    public boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException;

    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException;

    public ArrayList<ItemDto> searchItem(String search) throws SQLException, ClassNotFoundException;
}
