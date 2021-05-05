package user.dao.connectionMaker;

import user.dao.connectionMaker.ConnectionMaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost:13306/toby?serverTimezone=UTC&characterEncoding=UTF-8", "root", "root");
        return c;
    }
}
