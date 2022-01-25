package hu.javorekdenes.hwtracer.repository;

import hu.javorekdenes.hwtracer.repository.firabase.RepositoryException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FirebaseRepository<T> {

    List<T> findAllWhereDay(LocalDate date) throws RepositoryException;
    Optional<T> findLatest() throws RepositoryException;
    void saveBatch(List<T> toSave) throws RepositoryException;

}
