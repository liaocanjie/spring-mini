package com.minis.beans.factory.config;

import com.minis.core.PropertyValues;

/**
 * Bean定义
 * @author lcj
 * @date 2023/4/24
 */
public class BeanDefinition {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    private boolean lazyInit = false;
    /**
     * 记录 Bean 之间的依赖关系 保存ref标签的值
     */
    private String[] dependsOn;

    private ConstructorArgumentValues constructorArgumentValues;
    private PropertyValues propertyValues;

    private String initMethodName;
    private volatile Object beanClass;
    private String scope = SCOPE_SINGLETON;

    private String id;
    private String className;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public boolean isLazyInit(){
        return this.lazyInit;
    }

    public boolean isSingleton(){
        return this.scope.equals(SCOPE_SINGLETON);
    }

    public boolean isPrototype(){
        return this.scope.equals(SCOPE_PROTOTYPE);
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ConstructorArgumentValues getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    public void setConstructorArgumentValues(ConstructorArgumentValues constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String[] dependsOn) {
        this.dependsOn = dependsOn;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }
}
