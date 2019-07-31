package com.example.demo.service.power;

import com.example.demo.entity.ExportPower;
import com.example.demo.entity.OrigDL;
import com.baomidou.mybatisplus.service.IService;
import com.example.demo.entity.Params;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwx
 * @since 2019-05-08
 */
public interface OrigDLService extends IService<OrigDL> {

    PageInfo<Map> getPowerData(Params param);

    Map getPowerAnalyze(Params params);



}
