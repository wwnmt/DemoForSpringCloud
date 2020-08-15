package edu.nuaa.wwn.springcloud.product.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPo implements Serializable {

    private static final long serialVersionUID = 3641841051427034390L;

    private Long id;

    private String userName;

    private int level;

    private String note;
}
