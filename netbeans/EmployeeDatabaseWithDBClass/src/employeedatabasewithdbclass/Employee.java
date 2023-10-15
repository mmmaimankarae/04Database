package employeedatabasewithdbclass;
/* ส่วนข้อมูลของตารางที่จะสร้าง */
public class Employee {
    private int id;
    private String name;
    private double salary;
    public Employee() {
        /* ต้องสร้างเองถ้ามีconstructor ตัวอื่นด้วย */
    }
    
    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
}
