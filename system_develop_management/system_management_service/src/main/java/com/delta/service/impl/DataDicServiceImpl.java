package com.delta.service.impl;

import com.delta.EchartResult;
import com.delta.dao.DataDicDao;
import com.delta.domain.DataDictionary;
import com.delta.service.DataDicService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.delta.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataDicServiceImpl implements DataDicService {

    @Autowired
    private DataDicDao dataDicDao;

    @Override
    public List findAll() {
        List<DataDictionary> list = dataDicDao.findAll();
        return list;
    }

    @Override
    public DataDictionary findById(Integer id) {
        DataDictionary dataDictionary =dataDicDao.findById(id);
        return dataDictionary;

    }

    @Override
    public int add(DataDictionary dataDictionary) {
        int i =dataDicDao.add(dataDictionary);
        return i;
    }

    @Override
    public int delete(Integer id) {
        return dataDicDao.delete(id);
    }

    @Override
    public int update(DataDictionary dataDictionary) {
        return dataDicDao.update(dataDictionary);
    }

    @Override
    public PageInfo<DataDictionary> pageHelperList(int page, int size) {
        PageHelper.startPage(page, size);
        Page<DataDictionary> newPage = (Page<DataDictionary>) dataDicDao.findAll();
        return new PageInfo<DataDictionary>(newPage.getPageNum(),newPage.getPageSize(),newPage.getTotal(),newPage.getPages(),newPage.getResult());
    }

    @Override
    public PageInfo<DataDictionary> searchByName(int page, int size, String xname) {
        PageHelper.startPage(page, size);
        Page<DataDictionary> newPage = (Page<DataDictionary>) dataDicDao.searchByName(xname);
        return new PageInfo<DataDictionary>(newPage.getPageNum(),newPage.getPageSize(),newPage.getTotal(),newPage.getPages(),newPage.getResult(),xname);
    }

    @Override
    public List<EchartResult> echart() {
        return dataDicDao.echart();
    }
}
