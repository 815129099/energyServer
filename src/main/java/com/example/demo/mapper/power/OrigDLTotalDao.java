package com.example.demo.mapper.power;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.entity.OrigDL;
import com.example.demo.entity.Params;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx
 * @since 2019-05-08
 */
@Mapper
public interface OrigDLTotalDao{

    List<Map> getEDepartmentData(Params params);

}
