package ir.core.lib.management.web.hateoas.impl;

import ir.core.lib.management.service.BaseService;
import ir.core.lib.management.service.dto.BaseDTO;
import ir.core.lib.management.web.hateoas.BaseHateoas;

public abstract class BaseHateoasImpl<D extends BaseDTO> implements BaseHateoas<D> {

    private final BaseService<D> service;

    public BaseHateoasImpl(BaseService<D> service) {
        this.service = service;
    }

    @Override
    public D save(boolean include, D dto) {
        D result = service.save(dto);
        if (include)
            this.generateInclude(result);
        return result;
    }

    @Override
    public D update(boolean include, D dto) {
        D result = service.update(dto);
        if (include)
            this.generateInclude(result);
        return result;
    }

    @Override
    public D findById(boolean include, Long id) {
        D result = service.findById(id);
        if (include)
            this.generateInclude(result);
        return result;
    }

    /**
     * add extra info for the given dto with addInclude method
     *
     * @param dto must not be {@literal null}
     */
    public abstract void generateInclude(D dto);
}
