package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
    /* ส่วนของการจัดการเชื่อมต่อDatabase !!!ทำกับTable อะไรก็ได้ */
public class DatabaseHandler {
    private Connection connection =  null;    
    public DatabaseHandler(DatabaseDriver dbDriver ) throws ClassNotFoundException, SQLException {
        /* 5. สร้างConnection */
        connection = DriverManager.getConnection(dbDriver.getUrl(), 
                dbDriver.getUser(), dbDriver.getPasswd());
    }
    /* 7. ปิดConnection */
    public void closeConnection() throws SQLException {
        connection.close();
    }
    /* 6. สร้างstatement(คำสั่งsql) โดยใช้statement ที่connection เตรียมไว้ให้ */
        /* ส่วนของการinsert, update, delete */
    public int update(String sql, Object ...args) throws SQLException { 
        PreparedStatement ps = connection.prepareStatement(sql);
        for(int i = 0; i < args.length; i++) {
            ps.setObject(i+1, args[i]); /* IndexของStatement เริ่มที่1 */
        }
        int result = ps.executeUpdate();
        return result;
    }
        /* ส่วนของการดึงข้อมูล */
    /* Object ...args = variable length parameter = มีกี่ตัวก็ได้, ไม่มีก็ได้
     * Type Object = ทุกClass ในJava สืบทอดจากOblect Class */
    public ResultSet query(String sql, Object...args) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        if (args != null ) {
            for(int i =0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }
        }
        ResultSet rs = ps.executeQuery();
        return rs;
    }
        /* ส่วนของการทำTransaction */
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }
    
    public void commit() throws SQLException {
        connection.commit();
    }
    
    public void rollback() throws SQLException {
        connection.rollback();
    }
}
