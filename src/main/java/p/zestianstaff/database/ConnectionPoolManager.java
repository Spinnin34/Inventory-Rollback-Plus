package p.zestianstaff.database;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import p.zestianstaff.ZestianStaff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolManager {

    private HikariDataSource dataSource;

    private String hostname;
    private String port;
    private String database;
    private String username;
    private String password;

    private int minimumConnections;
    private int maximumConnections;
    private long connectionTimeout;
    private String testQuery;

    public ConnectionPoolManager() {
        init();
        setupPool();
    }

    private void init() {
        hostname = "129.159.94.131";
        port = "3306";
        database = "s5_staffplusplus";
        username = "u5_x2aBl7Qu5o";
        password = "Vyy1@a2NqAk5=i=2=aqb=XgG";
    }

    private void setupPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:mysql://" +
                        hostname +
                        ":" +
                        port +
                        "/" +
                        database
        );
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setUsername(username);
        config.setPassword(password);
        config.setIdleTimeout(870000000);
        config.setMaxLifetime(870000000);
        config.setConnectionTimeout(870000000);
        config.setMinimumIdle(20);
        config.setRegisterMbeans(true);
        config.setMaximumPoolSize(10);
        config.setConnectionTestQuery("SELECT 1");
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
        if (res != null) try { res.close(); } catch (SQLException ignored) {}
    }

}
