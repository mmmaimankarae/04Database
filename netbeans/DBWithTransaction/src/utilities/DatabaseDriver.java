package utilities;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDriver {
    String url, user, passwd;
    public DatabaseDriver(String driver, String url, String user, String passwd ) 
            throws ClassNotFoundException, SQLException {
       Class.forName(driver);
       this.url = url;
       this.user = user;
       this.passwd = passwd;
    }
    public String getUrl() {
        return url;
    }
    public String getUser() {
        return user;
    }
    public String getPasswd() {
        return passwd;
    }    
}
