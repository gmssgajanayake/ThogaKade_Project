package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.ItemBo;
import lk.ijse.pos.dao.DaoFactory;
import lk.ijse.pos.dao.custom.impl.ItemDaoImpl;
import lk.ijse.pos.dto.ItemDto;
import lk.ijse.pos.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBoImpl implements ItemBo {

    private final ItemDaoImpl ItemDao= DaoFactory.getInstance().getDao(DaoFactory.DaoType.ITEM);
    @Override
    public boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return ItemDao.save(new Item(
                itemDto.getCode(),
                itemDto.getDescription(),
                itemDto.getUnitPrice(),
                itemDto.getQtyOnHand()
        ));
    }

    @Override
    public boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return ItemDao.update(new Item(
                itemDto.getCode(),
                itemDto.getDescription(),
                itemDto.getUnitPrice(),
                itemDto.getQtyOnHand()
        ));
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return ItemDao.delete(code);
    }

    @Override
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
}
