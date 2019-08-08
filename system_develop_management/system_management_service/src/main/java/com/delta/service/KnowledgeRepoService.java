package com.delta.service;


import com.delta.EchartResult;
import com.delta.domain.KnowledgeRepository;
import com.delta.PageInfo;

import java.util.List;


public interface KnowledgeRepoService {

    List<KnowledgeRepository> findAll();

    int add(KnowledgeRepository knowledgeRepository);

    KnowledgeRepository findById(Integer id);

    int update(KnowledgeRepository knowledgeRepository);

    void delete(Integer id);

    PageInfo<KnowledgeRepository> pageHelperList(int page, int size);

    PageInfo<KnowledgeRepository> searchByName(int page, int size, String xname);

    List<EchartResult> echarts();

}
