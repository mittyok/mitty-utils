package test.com.mitty.db.utils;

import org.h2.tools.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import test.com.mitty.db.dao.UserDao;
import test.com.mitty.db.dao.mybatis.UserMapper;
import test.com.mitty.db.po.User;

/**
 * Created by PILIANG on 2015/11/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration
public class JDBCTemplateUtilsTest {

    @Autowired
    UserDao userDao;

    @Autowired
    UserMapper userMapper;

    @Test
    public void testH2ServerStart() {
        try {
            Server.createTcpServer(new String[] { "-tcp", "-tcpAllowOthers", "-tcpPort", "9092" }).start();
        } catch (Exception e) {
            // TODO 需要分析确定此异常是否需要处理
            e.printStackTrace();
        }

    }


    @Before
    public void setUp() {

    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setName("Mitty");
        userDao.addUser(user);
    }

    @Test
    public void testInsertUserWithMyBatis() {
        User user = new User();
        user.setName("MittyWithMybatis");
        userMapper.addUser(user);
        userMapper.queryUserList();
    }

}
