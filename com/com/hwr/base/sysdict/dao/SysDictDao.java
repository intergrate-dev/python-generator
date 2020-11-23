package com.hwr.base.sysdict.dao;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;
import com.hwr.base.sysdict.entity.SysDict;
import com.hwr.base.sysdict.vo.SysDictVO;
import com.hwr.base.sysdict.dto.SysDictDTO;
import java.util.List;


 /**
 * @Description 数据库访问类
 * @author admin
 * @version V1.0
 * @since V1.0
 * @date 2020-10-26
 *
 */
@Component
public interface SysDictDao{
    /**
	 * @Description: 新增
	 * @since V1.0
	 * @param sysDict
	 */
     void insert(SysDictDTO sysDict);

     /**
	 * @Description: 批量新增
	 * @since V1.0
	 * @param list
	 */
     void batchInsert(List<SysDictDTO> list);

    /**
	 * @Description: 修改
	 * @since V1.0
	 * @param sysDict
	 */
	 void update(SysDictDTO sysDict);

    /**
	 * @Description: 批量删除
	 * @since V1.0
	 * @param ids
	 */
	 void delete(List<Long> ids);

    /**
	 * @Description: 查询列表
	 * @since V1.0
	 * @param sysDict
	 */
	 List<SysDictVO> list(SysDictDTO sysDict);


    /**
	 * @Description: 通过ID查询
	 * @since V1.0
	 * @param id
	 */
	 SysDictVO findById(String id);
}
