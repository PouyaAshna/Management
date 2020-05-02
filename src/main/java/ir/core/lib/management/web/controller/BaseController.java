package ir.core.lib.management.web.controller;

import ir.core.lib.management.service.BaseService;
import ir.core.lib.management.service.dto.BaseDTO;
import ir.core.lib.management.web.hateoas.BaseHateoas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public abstract class BaseController<D extends BaseDTO> {

    private final BaseService<D> service;
    private final BaseHateoas<D> hateoas;

    protected BaseController(BaseService<D> service,
                             BaseHateoas<D> hateoas) {
        this.service = service;
        this.hateoas = hateoas;
    }

    @PostMapping
    public ResponseEntity<D> save(@RequestBody @Valid D dto,
                                  @RequestParam(name = "include", required = false) boolean include) {
        if (dto.getId() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        D result = hateoas.save(include, dto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<D> update(@RequestBody @Valid D dto,
                                    @RequestParam(name = "include", required = false) boolean include) {
        if (dto.getId() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        D result = hateoas.update(include, dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<D> findById(@PathVariable("id") Long id,
                                      @RequestParam(name = "include", required = false) boolean include) {
        D result = hateoas.findById(include, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/exist/{id}")
    public ResponseEntity<Boolean> existById(@PathVariable("id") Long id) {
        boolean exist = service.existById(id);
        return new ResponseEntity<>(exist, HttpStatus.OK);
    }

    @PostMapping("/exist")
    public ResponseEntity<Boolean> exist(@RequestBody D dto) {
        boolean exist = service.exist(dto);
        return new ResponseEntity<>(exist, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
