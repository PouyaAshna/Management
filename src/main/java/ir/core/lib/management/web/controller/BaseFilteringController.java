package ir.core.lib.management.web.controller;


import ir.core.lib.management.service.BaseFilteringService;
import ir.core.lib.management.service.dto.BaseDTO;
import ir.core.lib.management.web.filter.BaseFilter;
import ir.core.lib.management.web.hateoas.BaseFilteringHateoas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public abstract class BaseFilteringController<D extends BaseDTO, F extends BaseFilter>
        extends BaseController<D> {

    private final BaseFilteringHateoas<D, F> hateoas;


    protected BaseFilteringController(BaseFilteringService<D, F> service,
                                      BaseFilteringHateoas<D, F> hateoas) {
        super(service, hateoas);
        this.hateoas = hateoas;
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<D>> findAll(Pageable pageable,
                                           @RequestBody @Valid F filter,
                                           @RequestParam(name = "include", required = false) boolean include) {
        Page<D> result = hateoas.findAll(include, filter, pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
