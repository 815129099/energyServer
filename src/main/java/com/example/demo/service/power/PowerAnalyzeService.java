package com.example.demo.service.power;

import com.example.demo.entity.ExportPeak;
import com.example.demo.entity.Params;
import com.example.demo.entity.PowerAnalyze;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwx
 * @since 2019-05-30
 */
public interface PowerAnalyzeService extends IService<PowerAnalyze> {

    Map getPowerAnalyze(Params params);

    List<ExportPeak> exportAllPowerAnalyze(String month);

    List<ExportPeak> exportPowerByErtuID(int ErtuID,String month);

    List<ExportPeak> exportPower(Params params);
}
