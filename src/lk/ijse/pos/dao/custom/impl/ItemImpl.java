package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.ItemDao;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemImpl implements ItemDao {

    @Override
    public ArrayList<Item> searchItem(String txtSearch) throws SQLException, ClassNotFoundException {
        ArrayList<Item> itemDTOArrayList=new ArrayList<>();
        txtSearch="%"+txtSearch+"%";
        ResultSet resultSet =
                CrudUtil.execute("SELECT * FROM item WHERE description LIKE? OR unitPrice LIKE?", txtSearch, txtSearch);
        while (resultSet.next()){
            itemDTOArrayList.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4)
            ));
        }
        return itemDTOArrayList;
    }

    @Override
    public boolean save(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO item VALUES (?,?,?,?)",
                item.getCode(),
                item.getDescription(),
                item.getUnitPrice(),
                item.getQtyOnHand()
        );
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",
                item.getDescription(),
                item.getUnitPrice(),
                item.getQtyOnHand(),
                item.getCode()
        );
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM item WHERE code=?",code);
    }

    @Override
    public Item get(String s) {
        return null;
    }

    @Override
    public ArrayList<Item> getAll() {
        return null;
    }
}
