package com.delta.service;

import com.delta.EchartResult;
import com.delta.domain.ApiModule;
import com.delta.PageInfo;

import java.util.List;

public interface ApiModuleService {

    List<ApiModule> findAll();

    int add(ApiModule apiModule);

    ApiModule findById(Integer id);

    int update(ApiModule apiModule);

    void delete(Integer id);

    PageInfo<ApiModule> pageHelperList(int page, int size);

    PageInfo<ApiModule> searchByName(int page, int size, String xname);

    List<EchartResult> echart();
}
