package com.example.demo.web.util;


import com.example.demo.entity.Charge;
import com.example.demo.entity.Params;
import com.example.demo.service.util.ChargeService;
import com.example.demo.util.MyException.Result;
import com.example.demo.util.MyException.ResultUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lwx
 * @since 2019-05-28
 */
@CrossOrigin
@Controller
@RequestMapping("/api/util")
public class ChargeController {

    @Autowired
    private ChargeService chargeService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/ChargeList.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getChargeList(@RequestBody Params param) {
        logger.info(param.getParameter()+","+param.getPageNum()+","+param.getPageSize());
        HashMap<String, Object> Map = new HashMap<String, Object>();
        Page<Charge> page = chargeService.chargeList(param.getParameter(), Integer.parseInt(param.getPageNum()), Integer.parseInt(param.getPageSize()));
        Map.put("userData", page);
        Map.put("number", page.getTotal());
        Result result = ResultUtil.success();
        result.setData(Map);
        return result;
    }

    @RequestMapping(value = "/Charge.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result addCharge(@RequestBody Charge charge) {
        logger.info(charge.getStatus()+","+charge.getPeakTime()+","+charge.getRavineTime());
        boolean isSuccess = chargeService.addCharge(charge);
        Result result = ResultUtil.success();
        result.setData(isSuccess);
        return result;
    }


    @RequestMapping(value = "/Charge.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.DELETE})
    @ResponseBody
    public Result deleteCharge(@RequestBody Charge charge) {
        boolean isSuccess = chargeService.deleteCharge(charge);
        Result result = ResultUtil.success();
        result.setData(isSuccess);
        return result;
    }

    @RequestMapping(value = "/Charge.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.PUT})
    @ResponseBody
    public Result updateCharge(@RequestBody Charge charge) {
        boolean isSuccess = chargeService.updateCharge(charge);
        Result result = ResultUtil.success();
        result.setData(isSuccess);
        return result;
    }
}
