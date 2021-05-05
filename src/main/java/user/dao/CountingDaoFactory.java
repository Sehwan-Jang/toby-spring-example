package user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import user.dao.connectionMaker.ConnectionMaker;
import user.dao.connectionMaker.CountingConnectionMaker;
import user.dao.connectionMaker.SimpleConnectionMaker;

@Configuration
public class CountingDaoFactory {

    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new SimpleConnectionMaker();
    }
}
