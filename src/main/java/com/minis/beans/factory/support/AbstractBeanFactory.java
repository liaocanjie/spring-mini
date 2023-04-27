package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;
import com.minis.core.PropertyValue;
import com.minis.core.PropertyValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean工厂抽象类
 * @author lcj
 * @date 2023/4/27
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    protected List<String> beanDefinitionNames = new ArrayList<>();

    public void refresh(){
        for (String name : beanDefinitionNames) {
            try {
                getBean(name);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿Bean实例
        Object singleton = singletons.get(beanName);
        // 如果此时还没有这个Bean的实例，则获取它的定义来创建实例
        if(singleton == null){
            // 早期bean 实例化但未设置属性的bean 可解决循环依赖问题
            singleton = this.earlySingletonObjects.get(beanName);
            if(singleton == null){
                System.out.println("get bean null -------------- " + beanName);
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if(beanDefinition == null){
                    throw new BeansException("No bean.");
                }
                singleton = createBean(beanDefinition);
                this.registerBean(beanName, singleton);
                // 预留beanpostprocessor位置
                // step 1: postProcessBeforeInitialization
                applyBeanPostProcessorBeforeInitialization(singleton, beanName);
                // step 2: afterPropertiesSet
                // step 3: init-method
                if(beanDefinition.getInitMethodName() != null && !"".equals(beanDefinition.getInitMethodName())){
                    invokeInitMethod(beanDefinition, singleton);
                }
                // step 4: postProcessAfterInitialization
                applyBeanPostProcessorAfterInitialization(singleton, beanName);
            }
        }
        return singleton;
    }

    /**
     * 执行beandefinition中指定的的初始化方法
     * @param beanDefinition
     * @param obj
     */
    private void invokeInitMethod(BeanDefinition beanDefinition, Object obj) {
        Class<?> clz = beanDefinition.getClass();
        Method method = null;
        try {
            method = clz.getMethod(beanDefinition.getInitMethodName());
            method.invoke(obj);
        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        // 实例化bean
        Object obj = doCreateBean(beanDefinition);
        // 保存早期bean
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);
        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 完善bean，主要是处理属性
        populateBean(beanDefinition, clz, obj);
        return obj;
    }

    private Object doCreateBean(BeanDefinition beanDefinition){
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
            // 处理构造器参数
            ConstructorArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            //如果有参数
            if (!argumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
                Object[] paramValues = new Object[argumentValues.getArgumentCount()];
                //对每一个参数，分数据类型分别处理
                for (int i = 0; i < argumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
                    if ("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    } else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String)argumentValue.getValue());
                    } else if ("int".equals(argumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String)argumentValue.getValue());
                    } else { //默认为string
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    }
                }
                //按照特定构造器创建实例
                con = clz.getConstructor(paramTypes);
                obj = con.newInstance(paramValues);

            } else {
                //如果没有参数，直接创建实例
                obj = clz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(beanDefinition.getId() + " bean created. " + beanDefinition.getClassName() + " : " + obj.toString());
        return obj;
    }

    private void populateBean(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        handleProperties(beanDefinition, clz, obj);
    }

    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        // 处理属性
        System.out.println("handle properties for bean : " + beanDefinition.getId());
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                //对每一个属性，分数据类型分别处理
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pType = propertyValue.getType();
                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.getIsRef();
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];
                if ("String".equals(pType) || "java.lang.String".equals(pType)){
                    paramTypes[0] = String.class;
                } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                    paramTypes[0] = Integer.class;
                } else if ("int".equals(pType)) {
                    paramTypes[0] = int.class;
                } else { // 默认为string
                    paramTypes[0] = String.class;
                }

                if(!isRef){
                    paramValues[0] = pValue;
                }else {
                    // 处理ref的注入
                    try {
                        paramTypes[0] = Class.forName(pType);
                        //再次调用getBean创建ref的bean实例
                        paramValues[0] = getBean(pName);
                    } catch (ClassNotFoundException | BeansException e) {
                        e.printStackTrace();
                    }
                }

                //按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                    method.invoke(obj, paramValues);
                } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        super.registerSingleton(beanName, obj);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
        this.beanDefinitionNames.add(beanDefinition.getId());
//        if(!beanDefinition.isLazyInit()){
//            try {
//                getBean(beanDefinition.getId());
//            } catch (BeansException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public Boolean containsBean(String beanName) {
        return super.containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.getBeanDefinition(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.getBeanDefinition(name).isPrototype();
    }

    @Override
    public Class getType(String name) {
        return this.getBeanDefinition(name).getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {

    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    abstract public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    abstract public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
