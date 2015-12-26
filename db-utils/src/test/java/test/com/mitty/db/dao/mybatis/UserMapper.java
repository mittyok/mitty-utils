package test.com.mitty.db.dao.mybatis;

import test.com.mitty.db.po.User;

import java.util.List;

/**
 * Created by PILIANG on 2015/11/20.
 */
public interface UserMapper {

    public void addUser(User user);

    public List<User> queryUserList();

}
