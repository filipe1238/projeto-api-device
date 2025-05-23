package com.app.projetoapi.services;

import com.app.projetoapi.entity.DeviceDomain;
import com.app.projetoapi.entity.StateEnum;
import com.app.projetoapi.repositories.DeviceDomainRepository;
import com.app.projetoapi.utils.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class DeviceDomainService extends ParentService<DeviceDomain> {

    @Autowired
    private DeviceDomainRepository repository;

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
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "You cannot change the name or brand of a device that is in use.");
                }
            }
            updatedDevice.setCreatedAt(existingDevice.getCreatedAt());
        }
    }

    @Override
    public Boolean canDelete(DeviceDomain object) {
        if (object.getState().equals(StateEnum.INUSE)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You cannot delete a device that is in use.");
        } else {
            return true;
        }
    }

    public DeviceDomain partialUpdate(DeviceDomain deviceDomain) {
        DeviceDomain existingDevice = findById(deviceDomain.getId());
        if (existingDevice != null) {
            if (deviceDomain.getName() != null) {
                existingDevice.setName(deviceDomain.getName());
            }
            if (deviceDomain.getBrand() != null) {
                existingDevice.setBrand(deviceDomain.getBrand());
            }
            if (deviceDomain.getState() != null) {
                existingDevice.setState(deviceDomain.getState());
            }
            return saveUpdate(existingDevice);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found");
        }
    }
}
