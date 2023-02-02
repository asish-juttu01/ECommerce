package io.dropwizard.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.core.Customer;
import io.dropwizard.core.User;
import io.dropwizard.db.CustomerDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomersResource {
    private CustomerDAO customerDAO;

    public CustomersResource(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Customer> findById(@PathParam("id") IntParam id, @Auth User user) {
        return customerDAO.findById(id.get());
    }

    @RolesAllowed({ "ADMIN" })
    @POST
    @UnitOfWork
    public Customer createCustomer(@Valid Customer customer, @Auth User user) {
        return customerDAO.create(customer);
    }
    @RolesAllowed({ "ADMIN", "USER" })
    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Customer updateCustomer(@Valid Customer customer, @PathParam("id") IntParam id, @Auth User user){
        return customerDAO.update(customer,id.get());
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @GET
    @UnitOfWork
    public List<Customer> listPeople(@Auth User user) {
        return customerDAO.findAll();
    }
    @RolesAllowed({ "ADMIN" })
    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public void deleteCustomer(@PathParam("id") IntParam id, @Auth User user){
        customerDAO.delete(id.get());
    }
}
