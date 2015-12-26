package test.com.mitty.db.dao;

import test.com.mitty.db.po.User;

import java.util.List;

/**
 * Created by PILIANG on 2015/11/20.
 */
public interface UserDao {

    public List<User> queryForAll();

    void addUser(User user);
}
