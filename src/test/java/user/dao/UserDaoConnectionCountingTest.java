package user.dao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import user.dao.connectionMaker.CountingConnectionMaker;
import user.domain.User;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("aaron");
        user.setName("장세환");
        user.setPassword("solo");

        userDao.delete(user);
        userDao.add(user);
        userDao.delete(user);
        userDao.add(user);
        userDao.delete(user);
        userDao.add(user);

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection count : " + ccm.connectionCount());
    }
}
