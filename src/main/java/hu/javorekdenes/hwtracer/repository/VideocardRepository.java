package hu.javorekdenes.hwtracer.repository;

import hu.javorekdenes.hwtracer.model.raw.Videocard;

import java.time.LocalDate;
import java.util.List;

public interface VideocardRepository {

    List<Videocard> findAllWhereDay(LocalDate date);

}
