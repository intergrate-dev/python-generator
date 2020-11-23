package com.ccb.cloud.metadata.sysdictdetail.entity; import lombok.Data; import java.util.Date; import javax.validation.constraints.NotNull; import javax.validation.constraints.NotBlank; /** * @author hwr * @version 1.0 * @Description SysDictDetail * @date 2020-10-26 */ public
class SysDictDetail { /** 
 *ID 
 */ 
private Long detailId;

/** 
 *字典id 
 */ 
private Long dictId;

/** 
 *字典标签 
 */ 
private String label;

/** 
 *字典值 
 */ 
private String value;

/** 
 *排序 
 */ 
private Integer dictSort;

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