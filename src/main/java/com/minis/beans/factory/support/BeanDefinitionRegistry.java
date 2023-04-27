package com.minis.beans.factory.support;

import com.minis.beans.factory.config.BeanDefinition;

/**
 * 一个存放 BeanDefinition 的仓库
 * 可以存放、移除、获取及判断 BeanDefinition 对象
 * @author lcj
 * @date 2023/4/25
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);
}
