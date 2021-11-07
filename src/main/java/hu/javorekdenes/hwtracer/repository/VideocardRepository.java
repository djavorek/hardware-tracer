package hu.javorekdenes.hwtracer.repository;

import hu.javorekdenes.hwtracer.model.Hardwares;

import java.time.LocalDate;

public interface VideocardRepository {

    Hardwares findAllWhereDay(LocalDate date);

}
