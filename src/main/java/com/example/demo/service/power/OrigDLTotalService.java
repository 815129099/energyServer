package com.example.demo.service.power;

import com.example.demo.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface OrigDLTotalService {

    public PageInfo<Map> getEDepartmentData(Params params);
}
