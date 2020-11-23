package com.hwr.base.sysdict.service;

import com.hwr.base.sysdict.entity.SysDict;
import com.hwr.base.sysdict.vo.SysDictVO;
import com.hwr.base.sysdict.dto.SysDictDTO;
import com.hwr.base.sysdict.dto.SysDictPageDTO;
import com.hwr.common.base.PageData;

import java.util.List;

/**
 * @author hwr
 * @version V1.0
 * @Description 业务类
 * @date 2020-10-26
 * @since V1.0
 */
public interface SysDictService {

    /**
	 * @Description: 新增
	 * @since V1.0
	 * @param sysDict
	 */
     SysDictDTO save(SysDictDTO sysDict);

    /**
	 * @Description: 修改
	 * @since V1.0
	 * @param sysDict
	 */
	 SysDictDTO update(SysDictDTO sysDict);

    /**
	 * @Description: 批量删除
	 * @since V1.0
	 * @param id
	 */
	 void delete(List<Long> id);

    /**
	 * @Description: 查询列表
	 * @since V1.0
	 * @param sysDict
	 */
	 List<SysDictVO> list(SysDictDTO sysDict);

    /**
	 * @Description: 分页查询列表
	 * @since V1.0
	 * @param sysDict
	 */
	 PageData<SysDictVO> page(SysDictPageDTO sysDict);

    /**
	 * @Description: 通过ID查询
	 * @since V1.0
	 * @param id
	 */
	 SysDictVO findById(String id);

}