package com.example.demo.service.power;

import com.example.demo.entity.OrigRtv;
import com.baomidou.mybatisplus.service.IService;
import com.example.demo.entity.Params;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwx
 * @since 2019-05-08
 */
public interface OrigRtvService extends IService<OrigRtv> {

    Map getInstantPowerAnalyze(Params params);

}
