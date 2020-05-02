package ir.core.lib.management.web.hateoas;


import ir.core.lib.management.service.dto.BaseDTO;

public interface BaseHateoas<D extends BaseDTO> {
    D save(boolean include, D dto);

    D update(boolean include, D dto);

    D findById(boolean include, Long id);
}
