package com.minis.test;

import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;

/**
 * @author lcj
 * @date 2023/4/24
 */
public class Main {

    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        AService aService = (AService) ctx.getBean("aservice");
        AService bService = (AService) ctx.getBean("bservice");
        aService.sayHello();
        bService.sayHello();
    }
}
