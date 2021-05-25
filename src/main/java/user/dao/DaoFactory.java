package user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import user.dao.context.JdbcContext;
import user.domain.User;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {
//    @Bean
//    public UserDao userDao() {
//        return new UserDao(connectionMaker());
//    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:13306/toby?serverTimezone=UTC&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }
}