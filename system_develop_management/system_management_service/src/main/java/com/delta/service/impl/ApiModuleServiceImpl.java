package com.delta.service.impl;

import com.delta.EchartResult;
import com.delta.dao.ApiModuleDao;
import com.delta.domain.ApiModule;
import com.delta.service.ApiModuleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.delta.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiModuleServiceImpl implements ApiModuleService {

    @Autowired
    private ApiModuleDao apiModuleDao;

    @Override
    public List<ApiModule> findAll() {
        return apiModuleDao.findAll();
    }

    @Override
    public int add(ApiModule apiModule) {
        return apiModuleDao.add(apiModule);
    }

    @Override
    public ApiModule findById(Integer id) {
        return apiModuleDao.findById(id);
    }

    @Override
    public int update(ApiModule apiModule) {
        return apiModuleDao.update(apiModule);
    }

    @Override
    public void delete(Integer id) {
        apiModuleDao.delete(id);

    }

    @Override
    public PageInfo<ApiModule> pageHelperList(int page, int size) {
        PageHelper.startPage(page, size);
        Page<ApiModule> newPage = (Page<ApiModule>) apiModuleDao.findAll();
        return new PageInfo<ApiModule>(newPage.getPageNum(),newPage.getPageSize(),newPage.getTotal(),newPage.getPages(),newPage.getResult());
    }

    @Override
    public PageInfo<ApiModule> searchByName(int page, int size, String xname) {
        PageHelper.startPage(page, size);
        Page<ApiModule> newPage = (Page<ApiModule>) apiModuleDao.searchByName(xname);
        return new PageInfo<ApiModule>(newPage.getPageNum(),newPage.getPageSize(),newPage.getTotal(),newPage.getPages(),newPage.getResult(),xname);
    }

    @Override
    public List<EchartResult> echart() {
        return apiModuleDao.echart();
    }

}
