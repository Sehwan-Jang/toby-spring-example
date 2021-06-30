package user.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import user.domain.User;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoFactory.class})
public class UserDaoJdbcTest {

    User user1 = new User("1", "장세환", "no1");
    User user2 = new User("2", "박병욱", "no2");
    User user3 = new User("3", "김태완", "no3");

    @Autowired
    private ApplicationContext context;
    private UserDao dao;

    @BeforeEach
    void setUp() {
        this.dao = this.context.getBean("userDao", UserDaoJdbc.class);
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        dao.deleteAll();

        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        User userget1 = dao.get(user1.getId());
        assertThat(user1.getName()).isEqualTo(userget1.getName());
        assertThat(user1.getPassword()).isEqualTo(userget1.getPassword());

        User userget2 = dao.get(user2.getId());
        assertThat(user2.getName()).isEqualTo(userget2.getName());
        assertThat(user2.getPassword()).isEqualTo(userget2.getPassword());
    }

    @Test
    void count() throws SQLException, ClassNotFoundException {
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
    void getAll() {
        dao.deleteAll();

        assertThat(dao.getAll()).hasSize(0);

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user3, users3.get(2));
    }

    private void checkSameUser(User user1, User user) {
        assertThat(user1.getId()).isEqualTo(user.getId());
        assertThat(user1.getName()).isEqualTo(user.getName());
        assertThat(user1.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void duplicateError() {
        dao.deleteAll();

        dao.add(user1);
        assertThatThrownBy(() -> dao.add(user1))
                .isInstanceOf(DataAccessException.class);
    }

    @Test
    void beanTest() {
        UserDaoJdbc dao2 = context.getBean("userDao", UserDaoJdbc.class);

        System.out.println(dao == dao2);
        System.out.println(dao.equals(dao2));
        assertThat(dao == dao2).isTrue();
    }

}