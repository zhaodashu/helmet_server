package com.zmm.helmet.utils;

import com.zmm.helmet.common.ChildrenMenu;
import com.zmm.helmet.common.Menu;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MenuUtils {
    public static ChildrenMenu user_manage = new ChildrenMenu(102,"普通用户管理","users");
    public static ChildrenMenu admin_manage = new ChildrenMenu(103,"管理员管理","admins");
    public static ChildrenMenu device_list = new ChildrenMenu(202, "设备列表", "devices");

    //超级管理员
    public static Menu menu_super(){
        Set<ChildrenMenu> menus = new HashSet<>();
        menus.add(user_manage);
        menus.add(admin_manage);
        return new Menu(101,"用户管理",null,menus);
    }
    //管理员
    public static Menu menu_admin(){
        Set<ChildrenMenu> menus = new HashSet<>();
        menus.add(user_manage);
        return new Menu(101,"用户管理",null,menus);
    }
    // 设备管理
    public static Menu menu_device(){
        Set<ChildrenMenu> menus = new HashSet<>();
        menus.add(device_list);
        return new Menu(201,"设备管理",null, menus);
    }


}
