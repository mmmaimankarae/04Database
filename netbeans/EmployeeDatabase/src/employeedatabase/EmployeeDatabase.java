package employeedatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmployeeDatabase {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
            /* Driver of Derby: 1. ระบุDriver ที่จะใช้ */
        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";
        /* String mySqlDriver = "com.mysql.cj.jdbc.Driver"; */

        /* Driver of DBMS
         * String derbyEmbeddedDriver = "org.apache.derby.jdbc.EmbeddedDriver";
         * String msAccessDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
         * String msSQlDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
         * String oracleDriver = "oracle.jdbc.driver.OracleDriver";
         */
        
            /* 2. โหลดDriver */
        Class.forName(derbyClientDriver);
        /* Class.forName(mySqlDriver); */
        
            /* 3. ระบุURL ของDatabase (เหมือนURL บนหน้าServeice) */
        String url = "jdbc:derby://localhost:1527/employee";
        /* String url="jdbc:mysql://localhost:3306/employee?serverTimezone=UTC"; */
        
        /*
         * String url= "jdbc:mysql://server[:port]/databaseName"; // for mySQL
         * String url= "jdbc:derby:databaseName"; // for DerbyEmbedded
         * String url= "jdbc:odbc:Driver=:datasourceNameOfODBC"; // for MS Accces
         * String url= "jdbc:sqlserver://server[:port]:database="databaseName"; // for MS SQL Server 
         * String url= "jdbc:oracle:thin:@server:port:databaseName"; // for Oracle
         */
        
            /* 4. ระบุusername, password */
        String user = "app";
        String passwd = "app";

            /* 5. สร้างConnection */
        Connection con = DriverManager.getConnection(url, user, passwd);
        
            /* 6. สร้างstatement(คำสั่งsql) */
        Statement stmt = con.createStatement();
        
            /* สร้างObject ของข้อมูล และส่งข้อมูลไปทำงานแบบObject */
        Employee emp1 = new Employee(1, "John", 12345);
        Employee emp2 = new Employee(2, "Marry", 45678);
        
        /* 1 insert = เอา 1 Object ไปใส่เป็นข้อมูล 1 record ในตาราง */
        
            /* (1) Use "statement" */
        /* insertEmployee(stmt, emp1);
         * insertEmployee(stmt, emp2);
         * emp1.setSalary(12346);
         * updateEmployeeSalary(stmt, emp1);
         * emp1.setName("Jack");
         * updateEmployeeName(stmt, emp1);
         * deleteEmployee(stmt, emp1); */
        
            /* (2) Use connection */
        insertEmployeePreparedStatement(con, emp1);
        insertEmployeePreparedStatement(con, emp2);
        Employee emp = getEmployeeByIdPreparedStatement(con, 1);
        emp.setName("Jack");
        emp.setSalary(98765);
        updateEmployeeNamePreparedStatement(con, emp);
        updateEmployeeSalaryPreparedStatement(con, emp);
        deleteEmployeePreparedStatement(con, emp);
        
            /* GET DATA FROM DATABASE */
        ArrayList<Employee> employeeList = getAllEmployee(con);
        printAllEmployee(employeeList);
            /* 7. ปิดConnection & Statement */
        stmt.close();
        con.close();
    }
    
    public static void printAllEmployee(ArrayList<Employee> employeeList) {
        for(Employee emp : employeeList) {
           System.out.print(emp.getId() + " ");
           System.out.print(emp.getName() + " ");
           System.out.println(emp.getSalary() + " ");
       }
    }
    /* JDBC: method ต้องสร้างSQL Command เพื่อทำงานเอง */
        /* METHOD WITH USING STATEMENT */
    public static Employee getEmployeeById(Statement stmt, int id) throws SQLException {
        Employee emp = null;
        String sql = "select * from employee where id = " + id;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            emp = new Employee();
            emp.setId(rs.getInt("id"));
            emp.setName(rs.getString("name"));
            emp.setSalary(rs.getDouble("salary"));
        }
        return emp;
    }
        /* รับข้อมูลเป็นObject เลยต้องแตกแต่ละAttribute */
    public static void insertEmployee(Statement stmt, Employee emp) throws SQLException {
        /* String sql = "insert into employee (id, name, salary)" +
                        " values (5, 'Mark', 12345)"; */
        String sql = "insert into employee (id, name, salary)" +
                     " values (" + emp.getId() + "," + "'" + emp.getName() + 
                     "'" + "," + emp.getSalary() + ")";
        int result = stmt.executeUpdate(sql);
        System.out.println("Insert " + result + " row");
    }
    
    public static void deleteEmployee(Statement stmt, Employee emp) throws SQLException {
        String sql = "delete from employee where id = " + emp.getId();
        int result = stmt.executeUpdate(sql);
        System.out.println("delete " + result + " row");
    }
    
    public static void updateEmployeeSalary(Statement stmt, Employee emp) throws SQLException {
        String sql = "update employee set salary  = " + emp.getSalary() + 
                     " where id = " + emp.getId();
        int result = stmt.executeUpdate(sql);
        System.out.println("update " + result + " row");
    }
    
    public static void updateEmployeeName(Statement stmt, Employee emp) throws SQLException {
        String sql = "update employee set name  = '" + emp.getName() + "'" + 
                     " where id = " + emp.getId();
        int result = stmt.executeUpdate(sql);
        System.out.println("update " + result + " row");
    }
    
        /* METHOD WITH USING CONNECTION */
    /* มีmethod เตรียมไว้ให้สำหรับstatement แล้ว ซึ่งทำให้set ค่าได้ง่ายกว่า */
    public static void insertEmployeePreparedStatement(Connection con, Employee emp) throws SQLException {
        String sql = "insert into employee (id, name, salary)" + " values (?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
            /* Set ค่า parameter ว่าเป็น ? ตัวที่เท่าไหร่ */
        ps.setInt(1, emp.getId());
        ps.setString(2, emp.getName());
        ps.setDouble(3, emp.getSalary());
        int result = ps.executeUpdate();
        System.out.println("Insert " + result + " row");
    }
    
    public static void deleteEmployeePreparedStatement(Connection con, Employee emp) throws SQLException {
        String sql ="delete from employee where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, emp.getId());
        int result = ps.executeUpdate();
        System.out.println("Delete " + result + " row");
    }
    
    public static void updateEmployeeSalaryPreparedStatement(Connection con, Employee emp) throws SQLException {
        String sql = "update employee set salary  = ? where id = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, emp.getSalary());
        ps.setInt(2, emp.getId());
        int result = ps.executeUpdate();
        System.out.println("update " + result + " row");
    }
    
    public static void updateEmployeeNamePreparedStatement(Connection con, Employee emp) throws SQLException {
        String sql = "update employee set name  = ? where id = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, emp.getName());
        ps.setInt(2, emp.getId());
        int result = ps.executeUpdate();
        System.out.println("update " + result + " row");
    }
    
    public static Employee getEmployeeByIdPreparedStatement(Connection con, int id) throws SQLException {
        Employee emp = null;
        String sql = "select * from employee where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            emp = new Employee();
            emp.setId(rs.getInt("id"));
            emp.setName(rs.getString("name"));
            emp.setSalary(rs.getDouble("salary"));
        }
        return emp;
    }
    /* method ดึงข้อมูลทั้งตาราง: ต้องดึงข้อมูลใส่ArrayList แล้วสร้างObject เอง */
    public static ArrayList<Employee> getAllEmployee (Connection con) throws SQLException {
        String sql = "select * from employee order by id";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Employee> employeeList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
           Employee employee = new Employee();
           employee.setId(rs.getInt("id"));
           employee.setName(rs.getString("name"));
           employee.setSalary(rs.getDouble("salary"));
           employeeList.add(employee);
        }
        rs.close();
        return employeeList;
    }
}
