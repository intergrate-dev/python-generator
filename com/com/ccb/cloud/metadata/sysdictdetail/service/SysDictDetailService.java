package com.ccb.cloud.metadata.sysdictdetail.service;


import com.ccb.cloud.metadata.sysdictdetail.dos.SysDictDetailDo;

public interface SysDictDetailService {
    
    /**
     * 插入数据字典详情
     *
     * @param jsonString
     * @return
     */
    public String createSysDictDetail(String jsonString) throws Exception;
    
    /**
     * 分页查询数据字典详情
     *
     * @param jsonString
     * @return
     */
    public String getList(String jsonString) throws Exception;

    /**
     * 查询数据字典详情详情
     *
     * @param jsonString
     * @return
     */
    public String getDetail(String jsonString) throws Exception;
    
    public SysDictDetailDo getSysDictDetailById(String id) throws Exception;

    /**
     * 更新数据字典详情
     *
     * @param jsonString
     * @return
     */
    public String update(String jsonString) throws Exception;

    /**
     * 发布数据字典详情
     *
     * @param jsonString
     * @return
     */
    public String publish(String jsonString) throws Exception;

    /**
     * 注销数据字典详情
     *
     * @param jsonString
     * @return
     */
    public String revocate(String jsonString) throws Exception;

    /**
     * 删除数据字典详情
     *
     * @param jsonString
     * @return
     */
    public String delete(String jsonString) throws Exception;

}