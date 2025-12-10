
package dk.easv.connor.mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class ConnectionManager {

    private final SQLServerDataSource dataSource;

    public ConnectionManager() {
        dataSource = new SQLServerDataSource();

        SQLServerDataSource ds;
        ds = new SQLServerDataSource();
        ds.setDatabaseName("MyKTunes"); // make this unique as names are shared on server
        ds.setUser("CS2025b_e_1"); // Use your own username
        ds.setPassword("CS2025bE1#23"); // Use your own password
        ds.setServerName("10.176.111.34");
        ds.setPortNumber(1433);
        ds.setTrustServerCertificate(true);

    }

    public Connection connect() throws SQLServerException {
        return dataSource.getConnection();
    }
}
