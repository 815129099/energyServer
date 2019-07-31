package com.example.demo.service.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo.entity.Charge;
import com.example.demo.mapper.util.ChargeDao;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx
 * @since 2019-05-28
 */
@Service
public class ChargeServiceImpl extends ServiceImpl<ChargeDao, Charge> implements ChargeService {

    @Autowired
    private ChargeDao chargeDao;


    @Override
    public Page<Charge> chargeList(String parameter, int pageNum, int pageSize) {
        Page<Charge> page = PageHelper.startPage(pageNum, pageSize);
        chargeDao.chargeList(parameter);
        return page;
    }

    @Override
    @Transactional
    public boolean addCharge(Charge charge) {
        boolean isSuccess = false;
        chargeDao.insert(charge);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean deleteCharge(Charge charge) {
        boolean isSuccess = false;
        charge.setStatus(2);
        chargeDao.update(charge,new EntityWrapper<Charge>().eq("id",charge.getId()));
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean updateCharge(Charge charge) {
        boolean isSuccess = false;
        chargeDao.editCharge();
        chargeDao.updateCharge(charge.getId());
        isSuccess = true;
        return isSuccess;
    }


}
