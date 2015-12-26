package test.com.mitty.db.dao.impl;

import com.mitty.db.utils.dao.jdbctemplate.AbstractJDBCTemplateBaseDao;
import org.springframework.stereotype.Component;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import test.com.mitty.db.dao.UserDao;
import test.com.mitty.db.po.User;

import java.util.List;

/**
 * Created by PILIANG on 2015/11/20.
 */
@Component("userDao")
@TransactionConfiguration
public class UserDaoImpl extends AbstractJDBCTemplateBaseDao implements UserDao {

    public List<User> queryForAll() {

        getJdbcTemplate().queryForList("select * from user");
        return null;
    }

    @Transactional
    public void addUser(User user) {
        String sql = "insert into USER(name) VALUES (?)";
        this.getJdbcTemplate().update(sql, user.getName());
        throw new RuntimeException("lllllllllllllllllllllllkskskksssssssssssssssskkkkk");
    }

}
