package com.minis.beans.factory;

import com.minis.beans.BeansException;

import java.util.Map;

/**
 * 将 Factory 内部管理的 Bean 作为一个集合来对待，获取 Bean 的数量，得到所有 Bean 的名字，按照某个类型获取 Bean 列表等等
 * @author lcj
 * @date 2023/4/27
 */
public interface ListableBeanFactory extends BeanFactory {

    boolean containsBeanDefinition(String baenName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    /**
     * ?
     * @param type
     * @return
     */
    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
