package com.zmm.helmet.common;

import lombok.Data;

import java.util.Set;

@Data
public class Menu {
    private Integer id;
    private String authName;
    private String path;
    private Set<ChildrenMenu> children;

    public Menu(Integer id, String authName, String path, Set<ChildrenMenu> children) {
        this.id = id;
        this.authName = authName;
        this.path = path;
        this.children = children;
    }
}
