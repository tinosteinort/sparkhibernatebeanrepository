package de.tse.example.sparkhibernatebeanrepository.server.base;

import com.github.tinosteinort.beanrepository.BeanRepository;
import com.github.tinosteinort.beanrepository.PostConstructible;

public class JwtPasswordProvider implements PostConstructible {

    private final Configuration configuration;
    private byte[] passwordAsBytes;

    public JwtPasswordProvider(final Configuration configuration) {
        this.configuration = configuration;
    }

    public byte[] password() {
        return passwordAsBytes;
    }

    @Override public void onPostConstruct(final BeanRepository beanRepository) {
        passwordAsBytes = configuration.getJwtPassword().getBytes();
    }
}
