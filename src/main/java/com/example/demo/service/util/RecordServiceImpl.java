package com.example.demo.service.util;

import com.example.demo.entity.Record;
import com.example.demo.mapper.util.RecordDao;
import com.example.demo.mapper.util.UtilDao;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx
 * @since 2019-04-25
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordDao, Record> implements RecordService {

    @Autowired
    private UtilDao utilDao;

    @Override
    public Page<Record> recordList(String parameter, int pageNum, int pageSize) {
        Page<Record> page = PageHelper.startPage(pageNum, pageSize);
        utilDao.recordList(parameter);
        return page;
    }
}
