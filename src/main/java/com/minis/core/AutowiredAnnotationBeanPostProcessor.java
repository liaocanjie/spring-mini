package com.minis.core;

import com.minis.beans.BeansException;
import com.minis.beans.factory.annotation.Autowired;
import com.minis.beans.factory.support.AutowireCapableBeanFactory;

import java.lang.reflect.Field;

/**
 * @author lcj
 * @date 2023/4/27
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor{

    private AutowireCapableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if(fields != null){
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if(isAutowired){
                    String fieldName = field.getName();
                    Object autowiredObj = this.getBeanFactory().getBean(fieldName);
                    try {
                        field.setAccessible(true);
                        field.set(bean, autowiredObj);
                        System.out.println("autowire " + fieldName + " for bean " + beanName);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
