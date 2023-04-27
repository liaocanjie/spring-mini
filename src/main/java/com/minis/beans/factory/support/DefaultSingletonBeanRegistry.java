package com.minis.beans.factory.support;

import com.minis.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理单例的Bean实例
 * 需要保证线程安全
 *
 * ConcurrentHashMap在这里没什么用处
 * @author lcj
 * @date 2023/4/25
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected List<String> beanNames = new ArrayList<>();
    protected Map<String, Object> singletons = new ConcurrentHashMap<>();
    protected Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();


    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons){
            singletons.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) this.beanNames.toArray();
    }

    protected void removeSingleton(String beanName){
        synchronized (this.singletons){
            this.beanNames.remove(beanName);
            this.singletons.remove(beanName);
        }
    }
}
