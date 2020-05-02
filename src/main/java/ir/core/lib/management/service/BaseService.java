package ir.core.lib.management.service;

import ir.core.lib.management.service.dto.BaseDTO;

import java.util.Optional;

public interface BaseService<D extends BaseDTO> {

    D save(D dto);

    D update(D dto);

    D findById(Long id);

    Optional<D> findByIdOptional(Long id);

    boolean existById(Long id);

    boolean exist(D dto);

    boolean exist(D dto, String... ignorePath);


    void delete(Long id);

    void delete(D dto);
}
