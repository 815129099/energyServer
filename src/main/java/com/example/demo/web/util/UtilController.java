package com.example.demo.web.util;


import com.example.demo.entity.Params;
import com.example.demo.entity.PowerParam;
import com.example.demo.entity.Record;
import com.example.demo.entity.User;
import com.example.demo.service.util.RecordService;
import com.example.demo.service.util.UtilService;
import com.example.demo.util.MyException.ExceptionHandle;
import com.example.demo.util.MyException.Result;
import com.example.demo.util.MyException.ResultUtil;
import com.example.demo.util.date.DateUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lwx
 * @since 2019-03-21
 */
@CrossOrigin
@Controller
@RequestMapping("/api/util")
public class UtilController {

    @Autowired
    private ExceptionHandle exceptionHandle;

    @Autowired
    private UtilService utilService;

    @Autowired
    private RecordService recordService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/menu.do",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result getMenuJson() {
        Object menu = utilService.getMenuJson();
        Result result = ResultUtil.success();
        result.setData(menu);
        return result;
    }

    @RequestMapping(value = "/ertus.do",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result getErtusJson() {
        Object menu = utilService.getErtusJson();
        Result result = ResultUtil.success();
        result.setData(menu);
        return result;
    }

    @RequestMapping(value = "/RecordList.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getRecordList(@RequestBody Params param) {
        logger.info(param.getParameter()+","+param.getPageNum()+","+param.getPageSize());
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        Page<Record> page = recordService.recordList(param.getParameter(), Integer.parseInt(param.getPageNum()), Integer.parseInt(param.getPageSize()));
        resultMap.put("recordData", page);
        resultMap.put("number", page.getTotal());
        Result result = ResultUtil.success();
        result.setData(resultMap);
        return result;
    }


    @RequestMapping(value = "/getTimeAndIp.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getTimeAndIp(@RequestBody User user) {
        logger.info(user.getGeNumber());
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        //近七日用户访问记录
        //resultMap.put("list",this.utilService.getRecordNumberList());
        Record record = this.utilService.getTimeAndIp(user.getGeNumber());
        resultMap.put("recordTotal",this.utilService.getTotalNumber());
        resultMap.put("userTotal",this.utilService.getTotalUserNumber());
        if(StringUtils.isEmpty(record)){
            resultMap.put("ip","127.0.0.1");
            resultMap.put("time", DateUtil.getNow());
        }else {
            resultMap.put("ip",record.getIP());
            resultMap.put("time",record.getCreatedDateTime());
        }
        Result result = ResultUtil.success();
        result.setData(resultMap);
        return result;
    }

    @RequestMapping(value = "/getDataByErtuID.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getDataByErtuID(@RequestBody PowerParam param) {
        byte[] tm = param.getTm();
        int[] eList = param.geteList();
        int type = param.getType();
        List<List> mList = param.getmList();
        System.out.println(param.toString());
        boolean success = false;
        if(null==mList || mList.size()==0){
            success= this.utilService.getDataByErtuID(tm, eList, type);
        }else {
            success= this.utilService.getDataByErtuID(tm,eList,mList,type);
        }
        Result result = ResultUtil.success();
        result.setData(success);
        return result;
    }

    @RequestMapping(value = "/getTotalPower.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getTotalPower() {
        Map map = this.utilService.getTotalPower();
        Result result = ResultUtil.success();
        result.setData(map);
        return result;
    }

}
