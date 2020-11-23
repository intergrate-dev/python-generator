package com.hwr.base.sysdict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;

import com.hwr.base.sysdict.service.SysDictService;
import com.hwr.base.sysdict.entity.SysDict;

import com.hwr.common.base.BaseApiService;
import com.hwr.common.base.BaseResponse;
import javax.validation.Valid;
import java.util.List;
import com.hwr.base.sysdict.vo.SysDictVO;
import com.hwr.base.sysdict.dto.SysDictDTO;
import com.hwr.base.sysdict.dto.SysDictPageDTO;


/**
 * @author hwr
 * @version V1.0
 * @Description 控制器
 * @date 2020-10-26
 * @since V1.0
 */
@RestController
@RequestMapping("/wechat/sysDict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private BaseApiService baseApiService;

    /**
	 * @Description: 新增
	 * @since V1.0
	 * @param sysDict
	 */
	@PostMapping(value = "/save" )
    public BaseResponse save(@RequestBody @Valid SysDictDTO sysDict){
		return baseApiService.setResultSuccess(sysDictService.save(sysDict));
	 }

    /**
	 * @Description: 修改
	 * @since V1.0
	 * @param sysDict
	 */
	@PostMapping(value = "/update" )
    public BaseResponse update(@RequestBody @Valid SysDictDTO sysDict){
		return baseApiService.setResultSuccess(sysDictService.update(sysDict));
    }

    /**
	 * @Description: 删除
	 * @since V1.0
	 * @param ids
	 */
	@PostMapping(value = "/delete" )
    public BaseResponse delete(@RequestBody List<Long> ids){
		sysDictService.delete(ids);
		return baseApiService.setResultSuccessMsg("刪除成功");
    }

    /**
	 * @Description: 查询列表
	 * @since V1.0
	 * @param sysDict
	 */
	@GetMapping(value = "/list" )
    public BaseResponse list(@Valid SysDictDTO sysDict){
		List<SysDictVO> list = sysDictService.list(sysDict);
		return baseApiService.setResultSuccess(list);
    }

    /**
	 * @Description: 分页查询列表
	 * @since V1.0
	 * @param sysDict
	 */
	@GetMapping(value = "/page" )
    public BaseResponse page(@Valid SysDictPageDTO sysDict){
		return baseApiService.setResultSuccess(sysDictService.page(sysDict));
    }


    /**
	 * @Description: 通过ID查询
	 * @since V1.0
	 * @param id
	 */
	@GetMapping(value = "/findById" )
    public BaseResponse findById(@NotNull(message = "id不能为空") String id){
        return baseApiService.setResultSuccess(sysDictService.findById(id));
    }

}