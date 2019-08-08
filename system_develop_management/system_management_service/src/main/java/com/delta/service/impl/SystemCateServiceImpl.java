package com.delta.service.impl;

import com.delta.dao.SystemCateDao;
import com.delta.service.SystemCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemCateServiceImpl implements SystemCateService {
    @Autowired
    private SystemCateDao systemCateDao;

    @Override
    public String findByNum(String number) {
        return systemCateDao.findByNumber(number);
    }
}
