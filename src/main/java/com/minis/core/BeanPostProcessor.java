package com.minis.core;

import com.minis.beans.BeansException;

/**
 * Bean处理器
 * @author lcj
 * @date 2023/4/27
 */
public interface BeanPostProcessor {

    /**
     * 前置处理
     * @param bean bean对象
     * @param beanName beanId
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 后置处理
     * @param bean bean对象
     * @param beanName beanId
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
