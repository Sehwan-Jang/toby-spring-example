package user.dao.connectionMaker;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
    private int counter = 0;
    private final ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        counter++;
        return realConnectionMaker.makeConnection();
    }

    public int connectionCount() {
        return this.counter;
    }
}
