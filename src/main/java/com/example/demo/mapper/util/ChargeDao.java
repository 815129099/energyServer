package com.example.demo.mapper.util;

import com.example.demo.entity.Charge;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx
 * @since 2019-05-28
 */
@Mapper
public interface ChargeDao extends BaseMapper<Charge> {
    List<Charge> chargeList(@Param("parameter") String parameter);

    void updateCharge(@Param("id") int id);

    void editCharge();

    Charge getCharge();

}
