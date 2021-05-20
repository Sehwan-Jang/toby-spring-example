package user.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import user.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoFactory.class})
public class UserDaoTest {

    @Autowired
    private ApplicationContext context;
    private UserDao dao;

    @BeforeEach
    void setUp() {
        this.dao = this.context.getBean("userDao", UserDao.class);
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {

        User user = new User("aaron", "장세환", "springno1");
        User user2 = new User("woogi", "박병욱", "springno2");

        dao.deleteAll();

        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        User userget1 = dao.get(user.getId());
        assertThat(user.getName()).isEqualTo(userget1.getName());
        assertThat(user.getPassword()).isEqualTo(userget1.getPassword());

        User userget2 = dao.get(user2.getId());
        assertThat(user2.getName()).isEqualTo(userget2.getName());
        assertThat(user2.getPassword()).isEqualTo(userget2.getPassword());
    }

    @Test
    void count() throws SQLException, ClassNotFoundException {
        //given
        User user1 = new User("aaron", "장세환", "no1");
        User user2 = new User("woogi", "박병욱", "no2");
        User user3 = new User("papi", "김태완", "no3");
        //when then
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        assertThat(dao.getCount()).isEqualTo(1);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);
        dao.add(user3);
        assertThat(dao.getCount()).isEqualTo(3);

    }

    @Test
    void beanTest() {
        UserDao dao2 = context.getBean("userDao", UserDao.class);

        System.out.println(dao == dao2);
        System.out.println(dao.equals(dao2));
        assertThat(dao == dao2).isTrue();
    }

}