package ir.core.lib.management.service.specification;


import ir.core.lib.management.domain.entity.BaseEntity;
import ir.core.lib.management.web.filter.BaseFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import java.time.ZonedDateTime;
import java.util.List;


public abstract class BaseSpecification<E extends BaseEntity, F extends BaseFilter> {

    private static final String WILDCARD = "%";

    /**
     * generate the specification with the given filter
     *
     * @param request must not be {@literal null}
     * @return the generated specification; will never be {@literal null}.
     */
    public abstract Specification<E> getFilter(F request);

    private boolean checkGivenValue(Object value) {
        return value == null;
    }

    protected String containsLowerCase(String searchField) {
        return WILDCARD + searchField.toLowerCase() + WILDCARD;
    }

    protected Specification<E> attributeContains(String attribute, Object value) {
        return (root, query, cb) -> {
            if (checkGivenValue(value)) return null;
            Path<Object> path = null;
            for (String item : attribute.split("\\.")) {
                if (path == null) {
                    path = root.get(item);
                } else {
                    path = path.get(item);
                }
            }
            return cb.equal(
                    path,
                    value
            );
        };
    }

    protected Specification<E> attributeGreaterThan(String attribute, ZonedDateTime value) {
        return (root, query, cb) -> {
            if (checkGivenValue(value)) return null;
            Path<ZonedDateTime> path = null;
            for (String item : attribute.split("\\.")) {
                if (path == null) {
                    path = root.get(item);
                } else {
                    path = path.get(item);
                }
            }
            return cb.greaterThanOrEqualTo(
                    path,
                    value
            );
        };
    }

    protected Specification<E> attributeGreaterThan(String attribute, Long value) {
        return (root, query, cb) -> {
            if (checkGivenValue(value)) return null;
            Path<Long> path = null;
            for (String item : attribute.split("\\.")) {
                if (path == null) {
                    path = root.get(item);
                } else {
                    path = path.get(item);
                }
            }
            return cb.greaterThanOrEqualTo(
                    path,
                    value
            );
        };
    }

    protected Specification<E> attributeLessThan(String attribute, ZonedDateTime value) {
        return (root, query, cb) -> {
            if (checkGivenValue(value)) return null;
            Path<ZonedDateTime> path = null;
            for (String item : attribute.split("\\.")) {
                if (path == null) {
                    path = root.get(item);
                } else {
                    path = path.get(item);
                }
            }
            return cb.lessThanOrEqualTo(
                    path,
                    value
            );
        };
    }

    protected Specification<E> attributeLessThan(String attribute, Long value) {
        return (root, query, cb) -> {
            if (checkGivenValue(value)) return null;
            Path<Long> path = null;
            for (String item : attribute.split("\\.")) {
                if (path == null) {
                    path = root.get(item);
                } else {
                    path = path.get(item);
                }
            }
            return cb.lessThanOrEqualTo(
                    path,
                    value
            );
        };
    }

    protected Specification<E> attributeIn(String attribute, List value) {
        return (root, query, cb) -> {
            if (checkGivenValue(value)) return null;
            Path<Object> path = null;
            for (String item : attribute.split("\\.")) {
                if (path == null) {
                    path = root.get(item);
                } else {
                    path = path.get(item);
                }
            }
            CriteriaBuilder.In<Object> in = cb.in(path);
            for (Object aLong : value) {
                in.value(aLong);
            }
            return in;
        };
    }

    protected Specification<E> nameAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (checkGivenValue(value)) return null;
            Path<String> path = null;
            for (String item : attribute.split("\\.")) {
                if (path == null) {
                    path = root.get(item);
                } else {
                    path = path.get(item);
                }
            }
            return cb.like(
                    cb.lower(path),
                    containsLowerCase(value)
            );
        };
    }
}
