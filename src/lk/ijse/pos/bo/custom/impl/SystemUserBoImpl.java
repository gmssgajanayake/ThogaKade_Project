package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.SystemUserBo;
import lk.ijse.pos.dao.DaoFactory;
import lk.ijse.pos.dao.custom.impl.SystemUserDaoImpl;
import lk.ijse.pos.dto.SystemUserDto;
import lk.ijse.pos.entity.SystemUser;

import java.sql.SQLException;

public class SystemUserBoImpl implements SystemUserBo {

    private final SystemUserDaoImpl systemUserDao= DaoFactory.getInstance().getDao(DaoFactory.DaoType.SYSTEM_USER);

    @Override
    public boolean createSystemUser(SystemUserDto systemUserDto) throws SQLException, ClassNotFoundException {
        return systemUserDao.save(new SystemUser(
                systemUserDto.getName(),
                systemUserDto.getEmail(),
                systemUserDto.getPassword()
        ));
    }

    @Override
    public SystemUserDto getSystemUser(String email) throws SQLException, ClassNotFoundException {
        SystemUser systemUser= systemUserDao.get(email);
        return new SystemUserDto(
                systemUser.getName(),
                systemUser.getEmail(),
                systemUser.getPassword()
        );
    }

    @Override
    public void resetPassword(SystemUserDto systemUserDto) throws SQLException, ClassNotFoundException {
        systemUserDao.resetPassword(new SystemUser(
                systemUserDto.getName(),
                systemUserDto.getEmail(),
                systemUserDto.getPassword()
        ));
    }
}
