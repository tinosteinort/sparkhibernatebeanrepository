package de.tse.example.sparkhibernatebeanrepository.server.functional;

import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.SavedInputBO;
import de.tse.example.sparkhibernatebeanrepository.server.technical.DbService;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class InputService {

    private final DbService dbService;

    public InputService(final DbService dbService) {
        this.dbService = dbService;
    }

    public SavedInputBO create(final CreateInputTO input, final String owner) {
        final SavedInputBO savedInput = new SavedInputBO();
        savedInput.setOwner(owner);
        savedInput.setCreated(LocalDateTime.now());
        savedInput.setData(input.getInput());

        dbService.save(savedInput);

        return savedInput;
    }

    public List<SavedInputBO> listAll() {
        return dbService.list("FROM SavedInputBO ORDER BY created DESC");
    }

    public List<SavedInputBO> findAllContaining(final String searchValue) {
        final Query query = dbService.createQuery("FROM SavedInputBO " +
                "where lower(data) LIKE :SEARCHVALUE ORDER BY created DESC");

        query.setParameter("SEARCHVALUE", "%" + searchValue.toLowerCase() + "%");
        return dbService.list(query);
    }

    public SavedInputBO load(final long id) {
        return dbService.loadById(SavedInputBO.class, id);
    }

    public void delete(final SavedInputBO saveInput) {
        dbService.delete(saveInput);
    }
}
