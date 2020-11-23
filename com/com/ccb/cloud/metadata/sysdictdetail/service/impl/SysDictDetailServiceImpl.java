package com.ccb.cloud.metadata.sysdictdetail.service.impl;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import com.alibaba.fastjson.JSONObject;
import com.ccb.cloud.metadata.sysdictdetail.dos.SysDictDetailDo;
import com.ccb.cloud.metadata.sysdictdetail.vo.SysDictDetailVo;
import com.ccb.cloud.metadata.sysdictdetail.service.SysDictDetailService;
import com.ccb.cloud.log.Logger;
import com.ccb.cloud.log.LoggerFactory;
import com.ccb.openframework.exception.CommonRuntimeException;
import com.ccb.cloud.basic.common.consts.ErrorCodeEnum;
import com.ccb.cloud.basic.common.entity.ComInfoEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("portal.SysDictDetailService")
public class SysDictDetailServiceImpl implements SysDictDetailService {
    private Logger log = LoggerFactory.getLogger(SysDictDetailServiceImpl.class);
    
    @Autowired
    SysDictDetailService sysDictDetailService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createSysDictDetail(String jsonString) throws Exception {
        SysDictDetailDo sysDictDetailDo = null;
        try{
            JSONObject request = JSONObject.parseObject(jsonString);
            JSONObject body = JSONObject.parseObject(request.getString("txnBodyCom"));
            ComInfoEntity common = getReqTxnComm(jsonString);
            sysDictDetailDo = JSONObject.parseObject(body.getString("sysDictDetail"), SysDictDetailDo.class);
            Map doMap = JSONObject.parseObject(body.getString("sysDictDetail"), HashMap.class);
            sysDictDetailDo.setDetailId(String.valueOf(System.currentTimeMillis()));
            sysDictDetailDo.insertSysDictDetail();
            
            JSONObject jsonCommon = new JSONObject();
            jsonCommon.put("tStsTraceId", common.gettStsTraceId());
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("message", "创建成功");
            return getObject(jsonCommon, jsonBody);
        } catch(Exception e) {
            log.info("create sysDictDetail occure exception, detailId: {}, error: {}", sysDictDetailDo.getDetailId(), e.getMessage());
            throw new CommonRuntimeException(ErrorCodeEnum.COM_DYNAMIC_ERROR.getCode(), "创建数据字典详情失败");
        }
    }
    
    @Override
    public String getList(String jsonString) throws Exception {
        try{
            ComInfoEntity common = getReqTxnComm(jsonString);
            JSONObject rtc = new JSONObject();
            rtc.put("stsTraceId", common.gettStsTraceId());
            rtc.put("tCurrTotalPage", common.gettPageJump());
            rtc.put("tCurrTotalRec", common.gettRecInPage());
            Map<String, Object> map = new HashMap();
            Integer pageNo = common.gettPageJump() == null ? 1 : common.gettPageJump();
            Integer pageSize = common.gettRecInPage() == null ? 10 : common.gettRecInPage();
            map.put("tPageJump", (pageNo - 1) * pageSize);
            map.put("tRecInPage", pageSize);
            SysDictDetailDo sysDictDetailDo = new SysDictDetailDo();
            int total = sysDictDetailDo.queryDataCount(map);
            List<SysDictDetailVo> dataList = sysDictDetailDo.queryDataList(map);

            JSONObject request = JSONObject.parseObject(jsonString);
            JSONObject body = JSONObject.parseObject(request.getString("txnBodyCom"));
            JSONObject jsonCommon = new JSONObject();
            jsonCommon.put("tStsTraceId", common.gettStsTraceId());
            this.setPage(common, rtc, total);
            return getObject(rtc, dataList);
        } catch(Exception e) {
            log.info("getList sysDictDetail occure exception, error: {}", e.getMessage());
            throw new CommonRuntimeException(ErrorCodeEnum.COM_DYNAMIC_ERROR.getCode(), "查询数据字典详情列表失败");
        }
    }

    @Override
    public String getDetail(String jsonString) throws Exception {
        String detailId = null;
        try{
            ComInfoEntity common = getReqTxnComm(jsonString);
            JSONObject request = JSONObject.parseObject(jsonString);
            JSONObject body = JSONObject.parseObject(request.getString("txnBodyCom"));
            JSONObject jsonCommon = new JSONObject();
            jsonCommon.put("tStsTraceId", common.gettStsTraceId());
            detailId = body.getString("detailId");

            Map<String, Object> map = new HashMap<>();
            map.put("detail_id", detailId);
            com.ccb.cloud.metadata.sysdictdetail.vo.SysDictDetailVo detail = new SysDictDetailDo().queryDetail(map);
            return getObject(jsonCommon, detail);
        } catch(Exception e) {
            log.info("getDetail sysDictDetail occure exception, detailId: {}, error: {}", detailId, e.getMessage());
            throw new CommonRuntimeException(ErrorCodeEnum.COM_DYNAMIC_ERROR.getCode(), "查询数据字典详情详情失败");
        }
    }
    
    public SysDictDetailDo getSysDictDetailById(String id) throws Exception {
        return new SysDictDetailDo().findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(String jsonString) throws Exception {
        Map doMap = null;
        try{
            JSONObject request = JSONObject.parseObject(jsonString);
            JSONObject body = JSONObject.parseObject(request.getString("txnBodyCom"));
            ComInfoEntity common = getReqTxnComm(jsonString);
            doMap = JSONObject.parseObject(body.getString("sysDictDetail"), HashMap.class);

            new SysDictDetailDo().updateSysDictDetail(doMap);
            JSONObject jsonCommon = new JSONObject();
            jsonCommon.put("tStsTraceId", common.gettStsTraceId());
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("message", "修改成功");
            return getObject(jsonCommon, jsonBody);
        } catch(Exception e) {
            log.info("update sysDictDetail occure exception, detailId: {}, error: {}", doMap.get("detail_id"), e.getMessage());
            throw new CommonRuntimeException(ErrorCodeEnum.COM_DYNAMIC_ERROR.getCode(), "修改数据字典详情信息失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String publish(String jsonString) throws Exception {
        Map doMap = null;
        try{
            JSONObject request = JSONObject.parseObject(jsonString);
            JSONObject body = JSONObject.parseObject(request.getString("txnBodyCom"));
            ComInfoEntity common = getReqTxnComm(jsonString);
            doMap = JSONObject.parseObject(body.getString("sysDictDetail"), HashMap.class);
            doMap.put("state", "1");
            new SysDictDetailDo().updateSysDictDetail(doMap);
            JSONObject jsonCommon = new JSONObject();
            jsonCommon.put("tStsTraceId", common.gettStsTraceId());
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("message", "发布成功");
            return getObject(jsonCommon, jsonBody);
        } catch(Exception e) {
            log.info("publish sysDictDetail occure exception, detailId: {}, error: {}", doMap.get("detail_id"), e.getMessage());
            throw new CommonRuntimeException(ErrorCodeEnum.COM_DYNAMIC_ERROR.getCode(), "发布数据字典详情失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String revocate(String jsonString) throws Exception {
        Map doMap = null;
        try{
            JSONObject request = JSONObject.parseObject(jsonString);
            JSONObject body = JSONObject.parseObject(request.getString("txnBodyCom"));
            ComInfoEntity common = getReqTxnComm(jsonString);
            doMap = JSONObject.parseObject(body.getString("sysDictDetail"), HashMap.class);
            doMap.put("state", "0");
            new SysDictDetailDo().updateSysDictDetail(doMap);
            JSONObject jsonCommon = new JSONObject();
            jsonCommon.put("tStsTraceId", common.gettStsTraceId());
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("message", "注销成功");
            return getObject(jsonCommon, jsonBody);
        } catch(Exception e) {
            log.info("revocate sysDictDetail occure exception, detailId: {}, error: {}", doMap.get("detail_id"), e.getMessage());
            throw new CommonRuntimeException(ErrorCodeEnum.COM_DYNAMIC_ERROR.getCode(), "注销数据字典详情失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(String jsonString) throws Exception {
        return null;
    }

    private ComInfoEntity getReqTxnComm(String jsonString) {
        JSONObject request = JSONObject.parseObject(jsonString);
        return JSONObject.parseObject(request.getString("txnCommCom"), ComInfoEntity.class);
    }

    private String getObject(Object common, Object body) {
        JSONObject result = new JSONObject();
        result.put("txnCommCom", common);
        result.put("txnBodyCom", body);
        return result.toString();
    }

    private void setPage(ComInfoEntity common, JSONObject rtc, Integer total) {
        Integer pageSize = common.gettRecInPage();
        rtc.put("totalPage", String.valueOf((total + pageSize - 1)/ pageSize));
        rtc.put("totalRec", String.valueOf(total));
    }

}
