package employeedatabasejpa;

import employeedatabasejpa.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
/* ส่วนที่เกี่ยวกับการนำข้อมูลลงตาราง (ไม่ต้องมีDriver, SQL เพราะอยู่ในไฟล์persistence.xml แล้ว) */
public class EmployeeTable {
    public static void insertEmployee(Employee emp) {
            /* Database Driver กลายมาเป็น EntityManager (ระบุชื่อPersistence Unit Name ที่ตรงกับฐานข้อมูล) */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeDatabaseJPAPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try { /* ช่วยจัดการในส่วนของTransaction ด้วย */
            em.persist(emp);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    /* Employee ที่ส่งเข้ามาไม่ได้อยู่ในสถานะMange = เป็นObject ธรรมดา (ยังไม่persist) */
    public static void updateEmployee(Employee emp) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeDatabaseJPAPU");
        EntityManager em = emf.createEntityManager();
        /* ค้น(ดึง) ข้อมูล แล้วกลายเป็นสถานะManage (Link Database) */
        Employee fromDb = em.find(Employee.class, emp.getId());
        fromDb.setName(emp.getName());
        fromDb.setSalary(emp.getSalary());
        em.getTransaction().begin();
        try {
            em.persist(fromDb);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public static Employee findEmployeeById(Integer id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeDatabaseJPAPU");
        EntityManager em = emf.createEntityManager();
        Employee emp = em.find(Employee.class, id); /* Method หาที่EntityManager เตรียมมาให้ */
        em.close();
        return emp;
    }
    
    public static List<Employee> findAllEmployee() {
        EntityManagerFactory emf = 
                Persistence.createEntityManagerFactory("EmployeeDatabaseJPAPU");
        EntityManager em = emf.createEntityManager();
        /* Method ที่EntityManager เตรียมมาให้ใส่คำสั่งSQL */
        List<Employee> empList = (List<Employee>) em.createNamedQuery("Employee.findAll").getResultList();
        em.close();
        return empList;
    }
    
    public static List<Employee> findEmployeeByName(String name) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeDatabaseJPAPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Employee.findByName");
        query.setParameter("name", name);
        List<Employee> empList = (List<Employee>) query.getResultList();
        em.close();
        return empList;
    }
    
    public static void removeEmployee(Employee emp) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeDatabaseJPAPU");
        EntityManager em = emf.createEntityManager();
        Employee fromDb = em.find(Employee.class, emp.getId());
        em.getTransaction().begin();
        try {
            em.remove(fromDb); /* Method ลบที่EntityManager เตรียมมาให้ */
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }           
    }
}
