package dk.easv.connor.mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class ConnectionManager {

    private final SQLServerDataSource dataSource;

    public ConnectionManager() {
        dataSource = new SQLServerDataSource();

        // Configure the data source
        dataSource.setServerName("10.176.111.34"); // Server IP
        dataSource.setPortNumber(1433); // Port

        dataSource.setDatabaseName("MyKTunes"); 
        dataSource.setUser("CS2025b_e_1"); // Username
        dataSource.setPassword("CS2025bE1#23"); // Password
        dataSource.setTrustServerCertificate(true);
        dataSource.setEncrypt(false);


    }

    public Connection connect() throws SQLServerException {
        return dataSource.getConnection();
    }
}