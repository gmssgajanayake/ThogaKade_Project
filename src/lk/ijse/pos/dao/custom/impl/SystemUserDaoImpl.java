package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.SystemUserDao;
import lk.ijse.pos.entity.SystemUser;
import lk.ijse.pos.util.SecurityConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SystemUserDaoImpl implements SystemUserDao {

    @Override
    public boolean save(SystemUser systemUser) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO system_user VALUES (?,?,?)",
                systemUser.getName(), systemUser.getEmail(),
                /*To encrypt password*/
                SecurityConfig.encrypt(systemUser.getPassword(),SecurityConfig.holdingSecretKey)
        );
    }

    @Override
    public SystemUser get(String email) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM system_user WHERE email=?", email);
        return resultSet.next() ? new SystemUser(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3)
        ): null;
    }

    @Override
    public void resetPassword(SystemUser systemUser) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("UPDATE system_user SET password=? WHERE email=?",
                /*To encrypt password*/
                SecurityConfig.encrypt(systemUser.getPassword(),SecurityConfig.holdingSecretKey),
                systemUser.getEmail()
        );
    }

    @Override
    public boolean update(SystemUser systemUser) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<SystemUser> getAll() {
        return null;
    }
}
