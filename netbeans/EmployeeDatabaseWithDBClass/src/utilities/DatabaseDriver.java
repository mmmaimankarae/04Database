package utilities;

import java.sql.SQLException;
    /* ส่วนจัดการข้อมูลDriver ที่User ส่งเข้ามา */
public class DatabaseDriver {
    String url, user, passwd;
    public DatabaseDriver(String driver, String url, String user, String passwd) 
            throws ClassNotFoundException, SQLException {
            /* 2. โหลดDriver */
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
