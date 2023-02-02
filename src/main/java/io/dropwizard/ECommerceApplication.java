package io.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.auth.AppAuthorizer;
import io.dropwizard.auth.AppBasicAuthenticator;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.core.Customer;
import io.dropwizard.core.User;
import io.dropwizard.db.CustomerDAO;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.dual.HibernateBundle;
import io.dropwizard.resources.CustomersResource;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class ECommerceApplication extends Application<ECommerceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ECommerceApplication().run(args);
    }

    private final HibernateBundle<ECommerceConfiguration> hibernateBundle =
            new HibernateBundle<ECommerceConfiguration>(Customer.class) {
                @Override
                public PooledDataSourceFactory getReadSourceFactory(ECommerceConfiguration eCommerceConfiguration) {
                    return eCommerceConfiguration.getDatabase();
                }

                @Override
                public DataSourceFactory getDataSourceFactory(ECommerceConfiguration configuration) {
                    return configuration.getDatabase();
                }
            };

    @Override
    public String getName() {
        return "ECommerce";
    }

    @Override
    public void initialize(final Bootstrap<ECommerceConfiguration> bootstrap) {
        // TODO: application initialization
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final ECommerceConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        final CustomerDAO customerDAO = new CustomerDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new CustomersResource(customerDAO));

        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new AppBasicAuthenticator())
                .setAuthorizer(new AppAuthorizer())
                .setRealm("BASIC-AUTH-REALM")
                .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

}
