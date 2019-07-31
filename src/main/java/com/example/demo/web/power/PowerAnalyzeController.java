package com.example.demo.web.power;


import com.example.demo.entity.ExportPeak;
import com.example.demo.entity.Params;
import com.example.demo.service.power.PowerAnalyzeService;
import com.example.demo.util.MyException.Result;
import com.example.demo.util.MyException.ResultUtil;
import com.example.demo.util.date.DateUtil;
import com.wuwenze.poi.ExcelKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lwx
 * @since 2019-05-30
 */

@Controller
@RequestMapping("/api/power")
public class PowerAnalyzeController {
    @Autowired
    private PowerAnalyzeService powerAnalyzeService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/PowerPeak.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getPowerData(@RequestBody Params param) {
        logger.info(param.getEMeterName()+","+param.getBeginTime()+","+param.getEStationName()+","+param.getDateType());
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        Map page = powerAnalyzeService.getPowerAnalyze(param);
        resultMap.put("data", page);
        Result result = ResultUtil.success();
        result.setData(resultMap);
        return result;
    }



    // 生成导入模板（含3条示例数据）
    @RequestMapping(value = "/exportPower.do", method = RequestMethod.GET)
    public void exportPower(HttpServletRequest request, HttpServletResponse response) {
        Params params = new Params();
        String beginTime = request.getParameter("beginTime");
        params.setEMeterName(request.getParameter("emeterName"));
        params.setEStationName(request.getParameter("estationName"));
        params.setDateType(request.getParameter("dateType"));
        params.setBeginTime(DateUtil.parseGMT(beginTime));
        List<ExportPeak> list = powerAnalyzeService.exportPower(params);
        ExcelKit.$Export(ExportPeak.class, response).downXlsx(list, false);
    }

    // 生成导入模板（含3条示例数据）
    @RequestMapping(value = "/exportAllPowerAnalyze.do", method = RequestMethod.GET)
    public void exportAllPowerAnalyze(String month, HttpServletResponse response) {
        System.out.println(month);
        List<ExportPeak> list = powerAnalyzeService.exportAllPowerAnalyze(month);
        ExcelKit.$Export(ExportPeak.class, response).downXlsx(list, false);
    }


    // 生成导入模板（含3条示例数据）
    @RequestMapping(value = "/exportPowerByErtuID.do", method = RequestMethod.GET)
    public void exportPowerByErtuID(int ErtuID,String month, HttpServletResponse response) {
        System.out.println(ErtuID+","+month);
        List<ExportPeak> list = powerAnalyzeService.exportPowerByErtuID(ErtuID,month);
        ExcelKit.$Export(ExportPeak.class, response).downXlsx(list, false);
    }
}
