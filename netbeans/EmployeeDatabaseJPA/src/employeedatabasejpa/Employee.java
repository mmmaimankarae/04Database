package employeedatabasejpa;
/* ส่วนข้อมูลของตารางที่จะสร้าง */
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
/* 1. Entity Class: การประกาศว่าคลาสนี้ มีความสัมพันธ์กับตาราง 
 * ทำโดยการสร้างObject ที่เป็นตัวรับเก็บข้อมูล */
@Entity
@Table(name = "EMPLOYEE") /* 2. Mapping: บอกว่ามีความสัมพันธ์กับตารางอะไร */
@XmlRootElement
@NamedQueries({ /* 3. ประกาศภาษาSQL ให้อ้างถึงBy name (Generate ให้) */
    @NamedQuery(name = "Employee.findAll", 
            query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findById", 
            query = "SELECT e FROM Employee e WHERE e.id = :id"),
    @NamedQuery(name = "Employee.findByName", 
            query = "SELECT e FROM Employee e WHERE e.name = :name"),
    @NamedQuery(name = "Employee.findBySalary", 
            query = "SELECT e FROM Employee e WHERE e.salary = :salary")})
public class Employee implements Serializable {
        /* Persistent Fields (กำหนดว่าattribute ไหนคู่กับcolumn ไหน) */
    private static final long serialVersionUID = 1L;
    @Id /* 4. Annotation: ประกาศว่าเป็นตาราง primary key "Simple" */
    @Basic(optional = false)
    @Column(name = "ID") /* 5. Mapping: บอกว่ามีความสัมพันธ์กับcolumn อะไร */
    private Integer id; /* 6. โดยใช้attribute อะไรจับคู่ด้วย */
    
    @Column(name = "NAME")
    private String name;
    
    /* @Max(value=?)  @Min(value=?) */
    @Column(name = "SALARY")
    private Double salary;
    public Employee() {
        /* ต้องสร้างเองถ้ามีconstructor ตัวอื่นด้วย */
    }
    
    public Employee(Integer id) {
        this.id = id;
    }

    public Employee(Integer id, String name, Double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
    /* 7. ต้องมีการImplement method นี้ เพื่อคำนวณตำแหน่งในการใส่ข้อมูล */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    /* 8. ต้องมีการImplement method นี้ เพื่อเช็คความเท่ากันของObject */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) 
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "employeedatabasejpa.Employee[ id=" + id + " ]";
    }
}
