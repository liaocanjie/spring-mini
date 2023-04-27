package com.minis.beans.factory.config;

/**
 * 管理单例的Bean实例
 * @author lcj
 * @date 2023/4/25
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    String[] getSingletonNames();
}
