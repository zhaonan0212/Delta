package com.delta.service.impl;

import com.delta.dao.KnowledgeCateDao;
import com.delta.service.KnowledgeCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeCateServiceImpl implements KnowledgeCateService {

    @Autowired
    private KnowledgeCateDao knowledgeCateDao;

    @Override
    public String findByKid(String kid) {
        return knowledgeCateDao.findByKid(kid);
    }
}
