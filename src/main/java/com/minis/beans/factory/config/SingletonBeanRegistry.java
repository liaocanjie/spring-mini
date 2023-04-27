package com.minis.beans.factory.config;

/**
 * @author lcj
 * @date 2023/4/25
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    String[] getSingletonNames();
}
