package ir.core.lib.management.service.impl;

import ir.core.lib.errorhandling.error.notfound.CommonNotFoundException;
import ir.core.lib.management.domain.entity.BaseEntity;
import ir.core.lib.management.repository.BaseRepository;
import ir.core.lib.management.service.BaseService;
import ir.core.lib.management.service.dto.BaseDTO;
import ir.core.lib.management.service.mapper.BaseMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;

public class BaseServiceImpl<D extends BaseDTO, E extends BaseEntity> implements BaseService<D> {

    private final BaseRepository<E> repository;
    private final BaseMapper<D, E> baseMapper;
    private Class<D> dtoClass = ((Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

    public BaseServiceImpl(BaseRepository<E> repository,
                           BaseMapper<D, E> baseMapper) {
        this.repository = repository;
        this.baseMapper = baseMapper;
    }

    /**
     * Saves a given dto
     *
     * @param dto must not be {@literal null}
     * @return the saved dto; will never be {@literal null}.
     */
    @Override
    public D save(D dto) {
        E entity = baseMapper.toEntity(dto);
        entity = repository.save(entity);
        return baseMapper.toDTO(entity);
    }

    /**
     * Updates a given dto
     *
     * @param dto must not be {@literal null}
     * @return the updated dto; will never be {@literal null}.
     * @throws org.zalando.problem.AbstractThrowableProblem if the {@literal entity} doesn't {@literal exist}.
     * @throws IllegalArgumentException                     if {@literal id} is {@literal null}.
     */
    @Override
    public D update(D dto) {
        if (dto.getId() != null) {
            if (repository.existsById(dto.getId())) {
                E entity = baseMapper.toEntity(dto);
                entity = repository.save(entity);
                return baseMapper.toDTO(entity);
            } else throw new CommonNotFoundException().byId(dtoClass, dto.getId());
        } else throw new IllegalArgumentException("The given id must not be null");
    }

    /**
     * Retrieves a dto by its id.
     *
     * @param id must not be {@literal null}.
     * @return the dto with the given id.
     * @throws org.zalando.problem.AbstractThrowableProblem if the {@literal entity} doesn't {@literal exist}.
     * @throws IllegalArgumentException                     if {@literal id} is {@literal null}.
     */
    @Override
    public D findById(Long id) {
        if (id != null) {
            Optional<E> byId = repository.findById(id);
            if (byId.isPresent()) {
                return byId.map(baseMapper::toDTO).get();
            } else throw new CommonNotFoundException().byId(dtoClass, id);
        } else throw new IllegalArgumentException("The given id must not be null");
    }

    /**
     * Retrieves a dto by its id.
     *
     * @param id must not be {@literal null}.
     * @return the dto with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    @Override
    public Optional<D> findByIdOptional(Long id) {
        if (id != null) {
            Optional<E> byId = repository.findById(id);
            if (byId.isPresent()) {
                return byId.map(baseMapper::toDTO);
            } else return Optional.empty();
        } else throw new IllegalArgumentException("The given id must not be null");
    }

    /**
     * Returns whether a dto with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if a dto with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    @Override
    public boolean existById(Long id) {
        if (id != null) {
            return repository.existsById(id);
        } else throw new IllegalArgumentException("The given id must not be null");
    }

    /**
     * Checks whether the data store contains elements that match the given {@link Example}.
     *
     * @param dto to use for the existence check. Must not be {@literal null}.
     * @return {@literal true} if the data store contains elements that match the given {@link Example}.
     * @throws IllegalArgumentException if {@literal dto} is {@literal null}.
     */
    @Override
    public boolean exist(D dto) {
        if (dto != null) {
            E entity = baseMapper.toEntity(dto);
            ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                    .withIgnorePaths("include");
            Example<E> entityExample = Example.of(entity, exampleMatcher);
            return repository.exists(entityExample);
        } else throw new IllegalArgumentException("The given object must not be null");
    }

    /*
     * (non-Javadoc)
     * @see ir.core.lib.management.service.impl.BaseServiceImpl#exist();
     */
    @Override
    public boolean exist(D dto, String... ignorePath) {
        E entity = baseMapper.toEntity(dto);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnorePaths(ignorePath).withIgnorePaths("include");
        Example<E> entityExample = Example.of(entity, exampleMatcher);
        return repository.exists(entityExample);
    }

    /**
     * Deletes the dto with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws org.zalando.problem.AbstractThrowableProblem if the {@literal entity} doesn't {@literal exist}.
     * @throws IllegalArgumentException                     if {@literal id} is {@literal null}.
     */
    @Override
    public void delete(Long id) {
        if (id != null) {
            boolean existsByIdAndDeletedIsFalse = repository.existsById(id);
            if (existsByIdAndDeletedIsFalse) {
                repository.deleteById(id);
            } else throw new CommonNotFoundException().byId(dtoClass, id);
        } else throw new IllegalArgumentException("The given id must not be null");
    }

    /**
     * Deletes a given dto.
     *
     * @param dto must not be {@literal null}.
     * @throws org.zalando.problem.AbstractThrowableProblem if the {@literal entity} doesn't {@literal exist}.
     * @throws IllegalArgumentException                     if {@literal dto} is {@literal null}.
     */
    @Override
    public void delete(D dto) {
        if (dto != null) {
            boolean existsByIdAndDeletedIsFalse = repository.existsById(dto.getId());
            if (existsByIdAndDeletedIsFalse) {
                repository.deleteById(dto.getId());
            } else throw new CommonNotFoundException().byId(dtoClass, dto.getId());
        } else throw new IllegalArgumentException("The given object must not be null");
    }

}
