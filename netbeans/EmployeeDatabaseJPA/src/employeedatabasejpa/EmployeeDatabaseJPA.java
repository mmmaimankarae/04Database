package employeedatabasejpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmployeeDatabaseJPA {
    public static void main(String[] args) {
            /* สร้างObject ของClass */
        /* Employee emp1 = new Employee(1, "John", 12345.0);
         * Employee emp2 = new Employee(2, "Marry", 45678.0);
         * EmployeeTable.insertEmployee(emp1);
         * EmployeeTable.insertEmployee(emp2); */
            /* หา ถ้ามีข้อมูลUpdate, Delete */
        Employee emp;
        emp = EmployeeTable.findEmployeeById(1);
        if (emp != null) {
           emp.setName("Jack");
           /* EmployeeTable.removeEmployee(emp); */
           EmployeeTable.updateEmployee(emp);
        }
        /* List<Employee> empList = EmployeeTable.findEmployeeByName("Marry"); */
        List<Employee> empList = EmployeeTable.findAllEmployee();
        printAllEmployee(empList);
    }
    
    public static void printAllEmployee(List<Employee> employeeList) {
        for(Employee emp : employeeList) {
           System.out.print(emp.getId() + " ");
           System.out.print(emp.getName() + " ");
           System.out.println(emp.getSalary() + " ");
       }
    }
    /* Entity Manager (Default Generate) */
    public static void persist(Object object) {
        EntityManagerFactory emf = 
                Persistence.createEntityManagerFactory("EmployeeDatabaseJPAPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
