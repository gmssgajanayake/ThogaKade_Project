package lk.ijse.pos.bo.custom;

import lk.ijse.pos.dto.SystemUserDto;

import java.sql.SQLException;

public interface SystemUserBo {

    public boolean createSystemUser(SystemUserDto systemUserDto) throws SQLException, ClassNotFoundException;

    public SystemUserDto getSystemUser(String email) throws SQLException, ClassNotFoundException;

    public void resetPassword(SystemUserDto systemUserDto) throws SQLException, ClassNotFoundException;
}
