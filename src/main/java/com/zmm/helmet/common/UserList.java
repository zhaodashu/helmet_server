package com.zmm.helmet.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class UserList {
    private Long userid;

    private String username;

    private String create_time;

    private String access_level;

}
