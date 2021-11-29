package hu.javorekdenes.hwtracer.repository;

import hu.javorekdenes.hwtracer.model.raw.Videocard;
import hu.javorekdenes.hwtracer.repository.firabase.RepositoryException;

import java.time.LocalDate;
import java.util.List;

public interface VideocardRepository {

    List<Videocard> findAllWhereDay(LocalDate date) throws RepositoryException;

}
