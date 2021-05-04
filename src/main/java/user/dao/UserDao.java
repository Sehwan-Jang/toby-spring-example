package user.dao;

import user.domain.User;

import java.sql.*;

public class UserDao {
    private final ConnectionMaker simpleConnectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.simpleConnectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException, ClassNotFoundException {
        Connection c = simpleConnectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public void delete(User user) throws SQLException, ClassNotFoundException {
        Connection c = simpleConnectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "delete from users where id = ?");
        ps.setString(1, user.getId());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws SQLException, ClassNotFoundException {
        Connection c = simpleConnectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id =?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
}
