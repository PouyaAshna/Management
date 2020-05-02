package ir.core.lib.management.web.hateoas.impl;


import ir.core.lib.management.service.BaseFilteringService;
import ir.core.lib.management.service.dto.BaseDTO;
import ir.core.lib.management.web.filter.BaseFilter;
import ir.core.lib.management.web.hateoas.BaseFilteringHateoas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class BaseFilteringHateoasImpl<D extends BaseDTO, F extends BaseFilter>
        extends BaseHateoasImpl<D> implements BaseFilteringHateoas<D, F> {

    private final BaseFilteringService<D, F> service;

    protected BaseFilteringHateoasImpl(BaseFilteringService<D, F> service) {
        super(service);
        this.service = service;
    }

    @Override
    public Page<D> findAll(boolean include, F filter, Pageable pageable) {
        Page<D> resultPage = service.findAll(filter, pageable);
        if (include) {
            for (D dto : resultPage) {
                this.generateInclude(dto);
            }
        }
        return resultPage;
    }
}
