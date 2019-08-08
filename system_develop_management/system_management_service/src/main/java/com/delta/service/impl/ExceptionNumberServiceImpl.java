package com.delta.service.impl;

import com.delta.dao.ExceptionNumberDao;
import com.delta.service.ExceptionNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExceptionNumberServiceImpl implements ExceptionNumberService {

    @Autowired
    private ExceptionNumberDao exceptionNumberDao;
    @Override
    public String findByNum(String num) {
        return exceptionNumberDao.findByNum(num);
    }
}
