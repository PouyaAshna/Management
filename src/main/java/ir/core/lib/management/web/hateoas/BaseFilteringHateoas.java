package ir.core.lib.management.web.hateoas;

import ir.core.lib.management.service.dto.BaseDTO;
import ir.core.lib.management.web.filter.BaseFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseFilteringHateoas<D extends BaseDTO, F extends BaseFilter>
        extends BaseHateoas<D> {

    Page<D> findAll(boolean include, F filter, Pageable pageable);
}
