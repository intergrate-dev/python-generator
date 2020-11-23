package com.hwr.base.sysdict.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;

import com.hwr.common.base.PageData;
import com.hwr.base.sysdict.dao.SysDictDao;
import com.hwr.base.sysdict.entity.SysDict;
import com.hwr.base.sysdict.service.SysDictService;
import com.hwr.base.sysdict.vo.SysDictVO;
import com.hwr.base.sysdict.dto.SysDictDTO;
import com.hwr.base.sysdict.dto.SysDictPageDTO;

import java.util.List;

/**
 * @author hwr
 * @version V1.0
 * @Description 业务类
 * @date 2020-10-26
 * @since V1.0
 */
@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictDao sysDictDao;

    /**
	 * @Description: 新增
	 * @since V1.0
	 * @param sysDict
	 */
	public SysDictDTO save(SysDictDTO sysDict) {
        sysDictDao.insert(sysDict);
		return sysDict;
	}

    /**
	 * @Description: 修改
	 * @since V1.0
	 * @param sysDict
	 */
	public SysDictDTO update(SysDictDTO sysDict) {
        sysDictDao.update(sysDict);
		return sysDict;
	}

    /**
	 * @Description: 批量删除
	 * @since V1.0
	 * @param ids
	 */
	public void delete(List<Long> ids) {
        sysDictDao.delete(ids);
	}

    /**
	 * @Description: 查询列表
	 * @since V1.0
	 * @param sysDict
	 */
	public List<SysDictVO> list(SysDictDTO sysDict) {
        return sysDictDao.list(sysDict);
	}

    /**
	 * @Description: 分页查询列表
	 * @since V1.0
	 * @param sysDict
	 */
	public PageData<SysDictVO> page(SysDictPageDTO sysDict) {
            PageData<SysDictVO> pageData = new PageData<>(sysDict.getPageNo(), sysDict.getLimit());
            PageHelper.startPage(pageData.getPageNo(), pageData.getLimit());
            List<SysDictVO> list = sysDictDao.list(sysDict);
            pageData.setData(list);
            return pageData;
	}

    /**
	 * @Description: 通过ID查询
	 * @since V1.0
	 * @param id
	 */
	public SysDictVO findById(String id) {
            return sysDictDao.findById(id);
	}

}