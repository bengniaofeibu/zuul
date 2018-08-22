package com.jiujiu.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel(value="user对象",description="用户对象user")
public class UserInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一标识",required = true)
    private String id;

    @ApiModelProperty(value = "名字",required = true,example = "小明")
    private String name;
}


