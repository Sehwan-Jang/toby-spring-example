package user.dao;

import user.dao.context.JdbcContext;
import user.dao.strategy.StatementStrategy;
import user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private JdbcContext jdbcContext;

    public void setDataSource(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }


    public void add(User user) throws SQLException{
        this.jdbcContext.workWithStatementStrategy(connection -> {
            PreparedStatement ps =  connection.prepareStatement(
                    "insert into users(id, name, password) values(?, ?, ?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            return ps;
        });
    }

    public void deleteAll() throws SQLException {
        this.jdbcContext.workWithStatementStrategy(connection -> connection.prepareStatement("delete from users"));
    }

//    public User get(String id) throws SQLException, ClassNotFoundException {
//        Connection c = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            c = getConnection();
//            ps = c.prepareStatement("select * from users where id =?");
//            ps.setString(1, id);
//
//            rs = ps.executeQuery();
//            rs.next();
//
//            User user = new User();
//            user.setId(rs.getString("id"));
//            user.setName(rs.getString("name"));
//            user.setPassword(rs.getString("password"));
//            return user;
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException e) {
//                }
//            }
//        }
//    }
//
//
//    public int getCount() throws SQLException {
//        Connection c = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            c = getConnection();
//            ps = c.prepareStatement("select count(*) from users");
//            try {
//                rs = ps.executeQuery();
//                rs.next();
//                return rs.getInt(1);
//            } catch (SQLException e) {
//                throw e;
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException e) {
//                }
//            }
//        }
//    }
}
