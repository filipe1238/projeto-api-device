package com.app.projetoapi.entity;

import com.app.projetoapi.utils.ParentEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDomain extends ParentEntity {
    @Column(unique = true)
    private String name;
    private String brand;
    private StateEnum state;

    public DeviceDomain(int id) {
        super(id);
    }
}