package dbwithtransaction;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.DatabaseDriver;
import utilities.DatabaseHandler;

public class DBWithTransaction {

    public static void main(String[] args) {
        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";
        String url = "jdbc:derby://localhost:1527/account";
        String user = "app";
        String passwd = "app";
        DatabaseHandler dbHandler = null;
        try {
            DatabaseDriver dbDriver = new 
                DatabaseDriver(derbyClientDriver, url, user, passwd);
            dbHandler = new DatabaseHandler(dbDriver);
        }
        catch (SQLException ex){        
        }
        catch (ClassNotFoundException ex) {        
        }
        String withdrawSql = "update account set balance = balance - ? where id =?";
        String depostSql = "update account set balance = balance + ? where id =?";
        
        try {
            dbHandler.beginTransaction();
            dbHandler.update(withdrawSql, 200, 1);
            //int i = 1 /0;
            dbHandler.update(depostSql, 200, 2);
            dbHandler.commit();
        } catch (Exception ex) {
        try {
            dbHandler.rollback();
        } catch (Exception ex1) {
            Logger.getLogger(DBWithTransaction.class.getName()).log(Level.SEVERE, null, ex1);
        }
        Logger.getLogger(DBWithTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
