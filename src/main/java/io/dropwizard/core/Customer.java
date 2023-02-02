package io.dropwizard.core;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "customers")
@NamedQuery(
        name = "io.dropwizard.core.Customer.findAll",
        query = "SELECT c FROM Customer c"
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String first_name;
    private String last_name;
    private int phone;

    public Customer(){}

    public Customer(String first_name, String last_name, int phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return ID == customer.ID && phone == customer.phone && Objects.equals(first_name, customer.first_name) && Objects.equals(last_name, customer.last_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, first_name, last_name, phone);
    }
}
