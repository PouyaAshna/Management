package ir.core.lib.management.service;

import ir.core.lib.management.service.dto.BaseDTO;
import ir.core.lib.management.web.filter.BaseFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseFilteringService<D extends BaseDTO, F extends BaseFilter> extends BaseService<D> {

    Page<D> findAll(F filter, Pageable pageable);
}
