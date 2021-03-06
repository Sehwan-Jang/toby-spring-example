package user.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.assertj.ApplicationContextAssert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import user.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {


    @BeforeEach
    void setUp() {

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//        UserDao dao2 = new DaoFactory().userDao();
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("aaron");
        user.setName("장세환");
        user.setPassword("solo");

        dao.delete(user);
        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
    }

    @Test
    void beanTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao1 = context.getBean("userDao", UserDao.class);
        UserDao dao2 = context.getBean("userDao", UserDao.class);

        System.out.println(dao1 == dao2);
        System.out.println(dao1.equals(dao2));
        assertThat(dao1==dao2).isTrue();
    }
}