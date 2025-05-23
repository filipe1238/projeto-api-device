package com.app.projetoapi.controllers;

import com.app.projetoapi.entity.DeviceDomain;
import com.app.projetoapi.services.DeviceDomainService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping(value = "api/v1/device-domain")
public class DeviceDomainController {
    @Autowired
    private DeviceDomainService domainService;

    @GetMapping("/{id}")
    public DeviceDomain getById(@PathVariable Integer id) {
        return domainService.findById(id);
    }

    @GetMapping
    public Iterable<DeviceDomain> list(
            @RequestParam(required = false, name = "filter") String filterStr,
            @RequestParam(required = false, name = "range") String rangeStr,
            @RequestParam(required = false, name = "sort") String sortStr
    ) {
        return domainService.filterBy(filterStr, rangeStr, sortStr, null, List.of("name", "brand"));
    }

    @GetMapping("/by-state")
    public Iterable<DeviceDomain> listByState(
            @RequestParam(required = false, name = "filter") String filterStr,
            @RequestParam(required = false, name = "range") String rangeStr,
            @RequestParam(required = false, name = "sort") String sortStr
    ) {
        return domainService.filterBy(filterStr, rangeStr, sortStr, "state", null);
    }

    @GetMapping("/by-brand")
    public Iterable<DeviceDomain> listByBrand(
            @RequestParam(required = false, name = "filter") String filterStr,
            @RequestParam(required = false, name = "range") String rangeStr,
            @RequestParam(required = false, name = "sort") String sortStr
    ) {
        return domainService.filterBy(filterStr, rangeStr, sortStr, "brand", null);
    }


    @PostMapping
    public DeviceDomain create(@RequestBody DeviceDomain deviceDomain) {
        return domainService.saveUpdate(deviceDomain);
    }

    @PutMapping("/{id}")
    public DeviceDomain update(@PathVariable Integer id, @RequestBody DeviceDomain deviceDomain) {
        if (deviceDomain.getId() == null || !deviceDomain.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return domainService.saveUpdate(deviceDomain);
    }

    @PatchMapping("/{id}")
    public DeviceDomain partialUpdate(@PathVariable Integer id, @RequestBody DeviceDomain deviceDomain) {
        if (deviceDomain.getId() == null || !deviceDomain.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return domainService.partialUpdate(deviceDomain);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        domainService.deleteById(id);
    }

    @DeleteMapping
    public List<Integer> deleteByIds(@Valid @RequestBody List<Integer> ids) {
        return domainService.deleteByIds(ids);
    }
}
