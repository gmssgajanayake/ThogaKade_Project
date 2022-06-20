package lk.ijse.pos.view.tm;

import javafx.scene.control.Button;

public class CustomerTM {
    private String id;
    private String name;
    private String address;
    private double salary;
    private Button delete;

    public CustomerTM() {}

    public CustomerTM(String id, String name, String address, double salary, Button delete) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", delete=" + delete +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
