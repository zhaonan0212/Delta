package com.delta.service.impl;

import com.delta.EchartResult;
import com.delta.dao.KnowledgeRepoDao;
import com.delta.domain.KnowledgeRepository;
import com.delta.service.KnowledgeRepoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.delta.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeRepoServiceImpl implements KnowledgeRepoService {

    @Autowired
    private KnowledgeRepoDao knowledgeRepoDao;

    @Override
    public List<KnowledgeRepository> findAll() {
        return knowledgeRepoDao.findAll();
    }

    @Override
    public int add(KnowledgeRepository knowledgeRepository) {
        return knowledgeRepoDao.add(knowledgeRepository);
    }

    @Override
    public KnowledgeRepository findById(Integer id) {
        return knowledgeRepoDao.findById(id);
    }

    @Override
    public int update(KnowledgeRepository knowledgeRepository) {
        return knowledgeRepoDao.update(knowledgeRepository);
    }

    @Override
    public void delete(Integer id) {
        knowledgeRepoDao.delete(id);
    }

    @Override
    public PageInfo<KnowledgeRepository> pageHelperList(int page, int size) {
        PageHelper.startPage(page, size);
        Page<KnowledgeRepository> newPage = (Page<KnowledgeRepository>) knowledgeRepoDao.findAll();
        return new PageInfo<KnowledgeRepository>(newPage.getPageNum(), newPage.getPageSize(), newPage.getTotal(), newPage.getPages(), newPage.getResult());
}

    @Override
    public PageInfo<KnowledgeRepository> searchByName(int page, int size, String xname) {
        PageHelper.startPage(page, size);
        Page<KnowledgeRepository> newPage = (Page<KnowledgeRepository>) knowledgeRepoDao.searchByName(xname);
        return new PageInfo<KnowledgeRepository>(newPage.getPageNum(), newPage.getPageSize(), newPage.getTotal(), newPage.getPages(), newPage.getResult(),xname);
    }

    @Override
    public  List<EchartResult> echarts() {
        return knowledgeRepoDao.echarts();
    }
}
