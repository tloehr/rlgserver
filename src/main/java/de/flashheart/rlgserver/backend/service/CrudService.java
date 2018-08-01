package de.flashheart.rlgserver.backend.service;

import de.flashheart.rlgserver.backend.data.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class CrudService<T extends AbstractEntity> {

    protected abstract CrudRepository<T, Long> getRepository();

    public T save(T entity) {
        return getRepository().save(entity);
    }

    public void delete(long id) throws NoSuchElementException {
        getRepository().delete(load(id));
    }

    public T load(long id) throws NoSuchElementException {
        return getRepository().findById(id).get();
    }

    public abstract long countAnyMatching(Optional<String> filter);

    public abstract Page<T> findAnyMatching(Optional<String> filter, Pageable pageable);

}
