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
    private DeviceDomainService albumService;

    @GetMapping("/{id}")
    public DeviceDomain getById(@PathVariable Integer id) {
        return albumService.findById(id);
    }

    @GetMapping
    public Iterable<DeviceDomain> list(
            @RequestParam(required = false, name = "filter") String filterStr,
            @RequestParam(required = false, name = "range") String rangeStr,
            @RequestParam(required = false, name = "sort") String sortStr
    ) {
        return albumService.filterBy(filterStr, rangeStr, sortStr);
    }

    @PostMapping
    public DeviceDomain create(@RequestBody DeviceDomain album) {
        return albumService.save(album);
    }

    @PutMapping("/{id}")
    public DeviceDomain update(@PathVariable Integer id, @RequestBody DeviceDomain album) {
        if (!album.getId().equals(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do album não confere com o id da requisição");
        }
        return albumService.save(album);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        albumService.deleteById(id);
    }

    @DeleteMapping
    public List<Integer> deleteByIds(@Valid @RequestBody List<Integer> ids) {
        return albumService.deleteByIds(ids);
    }
}
