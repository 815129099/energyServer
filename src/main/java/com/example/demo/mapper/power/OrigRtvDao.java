package com.example.demo.mapper.power;

import com.example.demo.entity.OrigRtv;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.entity.Params;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx
 * @since 2019-05-08
 */
@Mapper
public interface OrigRtvDao extends BaseMapper<OrigRtv> {

    void insertOrigRtv(OrigRtv origRtv);

    List<Map> getInstantPowerAnalyzeByDay(Params params);

    List<Map> getInstantPowerAnalyzeByWeek(Params params);
}
