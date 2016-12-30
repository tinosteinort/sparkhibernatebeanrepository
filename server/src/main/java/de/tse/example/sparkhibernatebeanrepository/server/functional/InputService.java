package de.tse.example.sparkhibernatebeanrepository.server.functional;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.SavedInputBO;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.server.technical.DbService;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class InputService extends DbService {

    public InputService(final BeanAccessor beans) {
        super(beans);
    }

    public SavedInputBO create(final CreateInputTO input, final String owner) {
        final SavedInputBO savedInput = new SavedInputBO();
        savedInput.setOwner(owner);
        savedInput.setCreated(LocalDateTime.now());
        savedInput.setData(input.getInput());

        getSession().save(savedInput);

        return savedInput;
    }

    public List<SavedInputBO> listAll() {
        final Query query = getSession().createQuery("FROM SavedInputBO");
        return query.list();
    }
}
