package io.dropwizard.db;

import io.dropwizard.core.Customer;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class CustomerDAO extends AbstractDAO<Customer> {
    public CustomerDAO(SessionFactory sessionFactory){
        super(sessionFactory);
    }
    public List<Customer> findAll(){
        return list(namedTypedQuery("io.dropwizard.core.Customer.findAll"));
    }
    public Optional<Customer> findById(int ID){
        return Optional.ofNullable(get(ID));
    }

    public Customer create(Customer customer){
        return persist(customer);
    }

    public Customer update(Customer customer, int id){
        Customer customer_temp = findById(id).get();
        customer_temp.setFirst_name(customer.getFirst_name());
        customer_temp.setLast_name(customer.getLast_name());
        customer_temp.setPhone(customer.getPhone());
        return persist(customer_temp);
    }

    public void delete(int id){
        Customer customer = findById(id).get();
        currentSession().delete(customer);
    }
}
