package com.example.demo.web.power;


import com.example.demo.entity.Params;
import com.example.demo.service.power.OrigRtvService;
import com.example.demo.util.MyException.Result;
import com.example.demo.util.MyException.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lwx
 * @since 2019-05-08
 */

@Controller
@RequestMapping("/api/power")
public class OrigRtvController {

    @Autowired
    private OrigRtvService origRtvService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/InstantPowerAnalyze.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getInstantPowerAnalyze(@RequestBody Params param) {
        logger.info(param.getEMeterName()+","+param.getDateType()+","+param.getBeginTime()+","+param.getPageNum()+","+param.getPageSize());
        Map<String,Object> map = origRtvService.getInstantPowerAnalyze(param);
        Result result = ResultUtil.success();
        result.setData(map);
        return result;
    }


}
