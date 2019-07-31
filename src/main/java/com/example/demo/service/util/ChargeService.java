package com.example.demo.service.util;

import com.example.demo.entity.Charge;
import com.baomidou.mybatisplus.service.IService;
import com.example.demo.entity.User;
import com.github.pagehelper.Page;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwx
 * @since 2019-05-28
 */
public interface ChargeService extends IService<Charge> {

    Page<Charge> chargeList(String parameter, int pageNum, int pageSize);

    boolean addCharge(Charge charge);

    boolean deleteCharge(Charge charge);

    boolean updateCharge(Charge charge);
}
