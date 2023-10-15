package nonentitysuperclass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class NonEntitySuperClass {
    public static void main(String[] args) {
       FulltimeEmployee emp1 = new FulltimeEmployee();
       ParttimeEmployee emp2 = new ParttimeEmployee();
       emp1.setTemp("Emp1");
       emp2.setTemp("Emp2");
       emp1.setSalary(5000);
       emp2.setHoursWork(3);
       
       persist(emp1);
       persist(emp2);
    }

    public static void persist(Object object) {
        EntityManagerFactory emf = 
                Persistence.createEntityManagerFactory("NonEntitySuperClassPU");
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
