package com.example.demo.service.util;

import com.example.demo.entity.Record;
import com.baomidou.mybatisplus.service.IService;
import com.example.demo.entity.User;
import com.github.pagehelper.Page;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwx
 * @since 2019-04-25
 */
public interface RecordService extends IService<Record> {

    Page<Record> recordList(String parameter, int pageNum, int pageSize);

}
