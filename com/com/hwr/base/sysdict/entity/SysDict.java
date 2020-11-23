package com.hwr.base.sysdict.entity;

import lombok.Data;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
 * @author hwr
 * @version 1.0
 * @Description SysDict
 * @date 2020-10-26
 */
@Data
public class SysDict {
/** 
 *ID 
 */ 
private Long dictId;

/** 
 *字典名称 
 */ 
private String name;

/** 
 *描述 
 */ 
private String description;

/** 
 *创建者 
 */ 
private String createBy;

/** 
 *更新者 
 */ 
private String updateBy;

/** 
 *创建日期 
 */ 
private Date createTime;

/** 
 *更新时间 
 */ 
private Date updateTime;


}