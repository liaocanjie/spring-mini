package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.factory.ConfigurableListableBeanFactory;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.core.BeanPostProcessor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * IoC 引擎
 * @author lcj
 * @date 2023/4/27
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {

    }

    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {

    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return new String[0];
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return new String[0];
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return (String[])this.beanDefinitionNames.toArray();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();
        for (String beanName : this.beanDefinitionNames) {
            boolean matchFound = false;
            // ?
            BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
            Class<? extends BeanDefinition> classToMatch = beanDefinition.getClass();
            if(type.isAssignableFrom(classToMatch)){
                matchFound = true;
            }else {
                matchFound = false;
            }

            if(matchFound){
                result.add(beanName);
            }
        }

        return (String[]) result.toArray();
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object bean = getBean(beanName);
            result.put(beanName, (T) bean);
        }
        return result;
    }
}
