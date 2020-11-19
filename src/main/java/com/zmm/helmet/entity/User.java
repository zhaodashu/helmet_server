package com.zmm.helmet.entity;


import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity /*声明类为实体或表*/
@Table(name = "user_model") /*声明表名*/
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, columnDefinition = "BIGINT COMMENT 'user_model主键'")
    private Long userid;

    @Column(name = "username", length = 32)/*指定持久属性栏属性。*/
    private String username;

    @Column(name = "password", length = 32)
    private String password;

    @Column(name = "create_time")
    private String  create_time;

    @Column(name = "access_level")
    private String access_level;

}
