package employeedatabasewithdbclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.DatabaseHandler;
/* Class ที่สัมพันธ์กับEmployee Table: "จัดเตรียมคำสั่งSQL Command", ทำกับTable ที่ต้องการ */
public class EmployeeTable {
    /* เป็นStatuc เพราะ ไม่มีการสร้างObject, ไม่มีการเก็บState แค่เรียกใช้งาน */
    public static int updateEmployee(DatabaseHandler dbHandler, Employee emp) {
        String sql = "update employee set name=?, salary=? where id=?";
        int rowUpdated;
        try {
            rowUpdated = dbHandler.update(sql, emp.getName(), emp.getSalary(), emp.getId());
        }
        catch (SQLException ex ) {
            
            rowUpdated = 0;
        }     
        return rowUpdated;
    }  

    public static int removeEmployee(DatabaseHandler dbHandler, Employee emp) {
        String sql ="delete from employee where id = ?";
        int rowDeleted;
        try {
            rowDeleted = dbHandler.update(sql, emp.getId());
        }
        catch (SQLException ex ) {
            rowDeleted = 0;
        }
        return rowDeleted;
    }

    public static int insertEmployee(DatabaseHandler dbHandler, Employee emp) {
        String sql = "insert into employee (id, name, salary)" + 
                     " values (?,?,?)";
        int rowInserted;
        try {
            rowInserted = dbHandler.update(sql, emp.getId(), emp.getName(), emp.getSalary());
        }
        catch(SQLException ex ) {
            rowInserted = 0;
        }
        return rowInserted;
    } 

    public static Employee findEmployeeById(DatabaseHandler dbHandler, int id) throws SQLException {
        String sql = "select * from employee where id = ?";
        ResultSet rs;
        Employee emp = null;
        rs = dbHandler.query(sql, id);
        if (rs.next()) {
           emp = new Employee();
           emp.setId(rs.getInt("id"));
           emp.setName(rs.getString("name"));
           emp.setSalary(rs.getDouble("salary"));
        }
        return emp;   
    }

    public static ArrayList<Employee> findEmployeeByName(DatabaseHandler dbHandler, String name) throws SQLException {
        String sql = "select * from employee where name = ?";
        ResultSet rs;
        ArrayList<Employee> empList = null;
        rs = dbHandler.query(sql, name);
        empList = extractEmployee(rs);
        return empList;  
    } 
    
    public static ArrayList<Employee> findAllEmployee(DatabaseHandler dbHandler) throws SQLException {
        String sql = "select * from employee order by id";
        ResultSet rs; 
        ArrayList<Employee> empList = null;
        rs = dbHandler.query(sql);
        empList = extractEmployee(rs);
        return empList;
    }

    private static ArrayList<Employee> extractEmployee(ResultSet rs) {
        ArrayList<Employee> empList = new ArrayList<>();
        try {
            while(rs.next()) {
                Employee employee = new Employee();
                try {
                    employee.setId(rs.getInt("id"));
                    employee.setName(rs.getString("name"));
                    employee.setSalary(rs.getDouble("salary"));
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeTable.class.getName()).log(Level.SEVERE, null, ex);
                }
                empList.add(employee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(empList.size() == 0) {
            empList = null;
        }
        return empList;
    }
}
