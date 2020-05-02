package ir.core.lib.management.service.impl;


import ir.core.lib.management.domain.entity.BaseEntity;
import ir.core.lib.management.repository.BaseRepository;
import ir.core.lib.management.service.BaseFilteringService;
import ir.core.lib.management.service.dto.BaseDTO;
import ir.core.lib.management.service.mapper.BaseMapper;
import ir.core.lib.management.service.specification.BaseSpecification;
import ir.core.lib.management.web.filter.BaseFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class BaseFilteringServiceImpl<D extends BaseDTO, E extends BaseEntity, F extends BaseFilter>
        extends BaseServiceImpl<D, E> implements BaseFilteringService<D, F> {

    private final BaseRepository<E> repository;
    private final BaseMapper<D, E> baseMapper;
    private final BaseSpecification<E, F> specification;

    public BaseFilteringServiceImpl(BaseRepository<E> repository,
                                    BaseMapper<D, E> baseMapper,
                                    BaseSpecification<E, F> specification) {
        super(repository, baseMapper);
        this.repository = repository;
        this.baseMapper = baseMapper;
        this.specification = specification;
    }

    /**
     * Returns a {@link Page} of dtos meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param filter   must not be {@literal null}
     * @param pageable can be {@literal null}.
     * @return a page of dtos
     */
    @Override
    public Page<D> findAll(F filter, Pageable pageable) {
        return repository
                .findAll(specification.getFilter(filter), pageable)
                .map(baseMapper::toDTO);
    }
}
