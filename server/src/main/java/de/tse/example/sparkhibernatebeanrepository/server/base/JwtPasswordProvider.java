package de.tse.example.sparkhibernatebeanrepository.server.base;

import com.github.tinosteinort.beanrepository.BeanRepository;
import com.github.tinosteinort.beanrepository.PostConstructible;

public class JwtPasswordProvider implements PostConstructible {

    private final String password;
    private byte[] passwordAsBytes;

    public JwtPasswordProvider(final String password) {
        this.password = password;
    }

    public byte[] password() {
        return passwordAsBytes;
    }

    @Override public void onPostConstruct(final BeanRepository beanRepository) {
        passwordAsBytes = password.getBytes();
    }
}
