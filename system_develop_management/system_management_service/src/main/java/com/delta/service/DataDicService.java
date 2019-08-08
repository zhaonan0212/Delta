package com.delta.service;

import com.delta.EchartResult;
import com.delta.domain.DataDictionary;
import com.delta.PageInfo;


import java.util.List;


public interface DataDicService {


    List<DataDictionary> findAll();

    DataDictionary findById(Integer id);

    int add(DataDictionary dataDictionary);

    int delete(Integer id);

    int update(DataDictionary dataDictionary);


    PageInfo<DataDictionary> pageHelperList(int page, int size);

    PageInfo<DataDictionary> searchByName(int page, int size, String xname);

    List<EchartResult> echart();

}

