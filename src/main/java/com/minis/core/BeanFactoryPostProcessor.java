package com.minis.core;

import com.minis.beans.BeansException;

/**
 * @author lcj
 * @date 2023/4/27
 */
public class BeanFactoryPostProcessor implements BeanPostProcessor{

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
