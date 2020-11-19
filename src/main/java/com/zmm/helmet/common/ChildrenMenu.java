package com.zmm.helmet.common;

import lombok.Data;

import java.util.Set;

@Data
public class ChildrenMenu{
    private Integer id;
    private String authName;
    private String path;
    private Set<Menu> children;

    public ChildrenMenu(Integer id, String authName, String path) {
        this.id = id;
        this.authName = authName;
        this.path = path;
    }
}
