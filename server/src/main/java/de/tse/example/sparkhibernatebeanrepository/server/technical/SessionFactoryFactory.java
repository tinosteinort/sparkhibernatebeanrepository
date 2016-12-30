package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.github.tinosteinort.beanrepository.Factory;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.SavedInputBO;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.UserBO;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryFactory implements Factory<SessionFactory> {

    @Override public SessionFactory createInstance() {
        final BootstrapServiceRegistryBuilder builder = new BootstrapServiceRegistryBuilder();
        final BootstrapServiceRegistry bootstrapRegistry = builder.build();
        final StandardServiceRegistryBuilder standardRegistryBuilder = new StandardServiceRegistryBuilder(bootstrapRegistry);
        standardRegistryBuilder.applySetting("hibernate.connection.driver_class", "org.h2.Driver");
        standardRegistryBuilder.applySetting("hibernate.connection.url", "jdbc:h2:mem:test");
        standardRegistryBuilder.applySetting("hibernate.connection.username", "sa");
        standardRegistryBuilder.applySetting("hibernate.connection.password", "");
        standardRegistryBuilder.applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        standardRegistryBuilder.applySetting("hibernate.hbm2ddl.auto", "create-drop");
        standardRegistryBuilder.applySetting("hibernate.current_session_context_class", "org.hibernate.context.internal.ManagedSessionContext");
        standardRegistryBuilder.applySetting("hibernate.transaction.coordinator_class", "jdbc");
        final StandardServiceRegistry standardRegistry = standardRegistryBuilder.build();

        final MetadataSources sources = new MetadataSources(standardRegistry);
        sources.addAnnotatedClass(SavedInputBO.class);
        sources.addAnnotatedClass(UserBO.class);

        final Metadata metadata = sources.buildMetadata();

//        final Map<String, Type> types = new HashMap<>();
//        types.put("owner", LongType.INSTANCE);
//        metadata.getFilterDefinitions().put("UserFilter", new FilterDefinition("UserFilter", "owner = :NAME", types));

        return metadata.buildSessionFactory();
    }
}
