package com.minis.test;

import com.minis.beans.factory.annotation.Autowired;

/**
 * @author lcj
 * @date 2023/4/24
 */
public class BServiceImpl implements AService{

    @Autowired
    private AServiceImpl aservice;

    @Override
    public void sayHello() {
        aservice.sayHello();
        System.out.println("b service say hello");
    }

    public AServiceImpl getAservice() {
        return aservice;
    }

    public void setAservice(AServiceImpl aservice) {
        this.aservice = aservice;
    }
}
