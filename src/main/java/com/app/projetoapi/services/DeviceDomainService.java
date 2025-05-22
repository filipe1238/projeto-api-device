package com.app.projetoapi.services;

import com.app.projetoapi.entity.DeviceDomain;
import com.app.projetoapi.entity.StateEnum;
import com.app.projetoapi.repositories.DeviceDomainRepository;
import com.app.projetoapi.utils.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DeviceDomainService extends ParentService<DeviceDomain> {

    @Autowired
    private DeviceDomainRepository repository;
    @Autowired
    private FilterService<DeviceDomain, Integer> filterService;

    @Override
    public BaseRepository<DeviceDomain, Integer> getRepository() {
        return repository;
    }

    public Page<DeviceDomain> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void beforeUpdate(DeviceDomain updatedDevice) {
        DeviceDomain existingDevice = findById(updatedDevice.getId());
        if (existingDevice != null) {
            if (updatedDevice.getState().equals(StateEnum.INUSE)){
                if (!updatedDevice.getName().equals(existingDevice.getName()) ||
                        !updatedDevice.getBrand().equals(existingDevice.getBrand())) {
                    throw new RuntimeException("You cannot change the name or brand of a device that is in use.");
                }
            }
        }
    }

    @Override
    public Boolean canDelete(DeviceDomain object) {
        if (object.getState().equals(StateEnum.INUSE)){
            throw new RuntimeException("You cannot delete a device that is in use.");
        } else {
            return true;
        }
    }

    @Override
    public Iterable<DeviceDomain> filterBy(String filterStr, String rangeStr, String sortStr) {
        QueryParamWrapper wrapper = QueryParamExtractor.extract(filterStr, rangeStr, sortStr);
        // este searchFields é uma lista de campos que serão usados para filtrar a busca
        List<String> searchFields = List.of("name");
        return filterService.filterBy(wrapper, getRepository(), searchFields);
    }
}
