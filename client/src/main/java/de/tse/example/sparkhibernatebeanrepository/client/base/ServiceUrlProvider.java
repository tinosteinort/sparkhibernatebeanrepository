package de.tse.example.sparkhibernatebeanrepository.client.base;

import com.github.tinosteinort.beanrepository.BeanAccessor;

public class ServiceUrlProvider {

    private final Configuration configuration;
    private final String servicePart;

    public ServiceUrlProvider(final BeanAccessor beans, final String servicePart) {
        this.configuration = beans.getBean(Configuration.class);
        this.servicePart = servicePart;
    }

    public String provide(final String... parts) {
        final StringBuilder sb = new StringBuilder(configuration.getBaseUrl());
        sb.append("/").append(servicePart);

        if (parts != null) {
            for (String part : parts) {
                sb.append("/").append(part);
            }
        }

        return sb.toString();
    }
}
