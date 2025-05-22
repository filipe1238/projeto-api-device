package com.app.projetoapi.entity;

public enum StateEnum {
    AVAILABLE("Available"),
    INACTIVE("Inactive"),
    INUSE("In-use");


    private String name;

    StateEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
