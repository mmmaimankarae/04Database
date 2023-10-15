package nonentitysuperclass;
/* Non-Entity Superclass */
public class Employee {
    /* class ธรรมดา: อาจมีการใช้งานในช่วงRun time
     * attibute จะไม่ถูกเขียนลงในDatabase */
    protected String temp;
    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
