package com.ccb.cloud.metadata.sysdictdetail.vo;

import java.util.Date;
import com.ccb.cloud.log.Logger;
import com.ccb.cloud.log.LoggerFactory;

public class SysDictDetailVo {
    private Logger log = LoggerFactory.getLogger(SysDictDetailVo.class);
    
    /** 
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

      

    
    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }
    
    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public Integer getDictSort() {
        return dictSort;
    }

    public void setDictSort(Integer dictSort) {
        this.dictSort = dictSort;
    }
    
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
      
}