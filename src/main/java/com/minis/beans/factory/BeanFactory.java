package com.minis.beans.factory;

import com.minis.beans.BeansException;
import com.minis.beans.factory.config.BeanDefinition;

/**
 * 管理Bean
 * @author lcj
 * @date 2023/4/25
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    void registerBean(String beanName, Object obj);

    void registerBeanDefinition(BeanDefinition beanDefinition);

    Boolean containsBean(String beanName);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class getType(String name);

}
