package com.minis.context;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.BeansException;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.support.AbstractAutowireCapableBeanFactory;
import com.minis.beans.factory.support.DefaultListableBeanFactory;
import com.minis.core.AutowiredAnnotationBeanPostProcessor;
import com.minis.core.BeanFactoryPostProcessor;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;
import com.minis.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.ArrayList;
import java.util.List;

/**
 * xml容器对象
 * 解析类路径下的 XML 来构建应用上下文
 *
 * 使用代理模式 ClassPathXmlApplicationContext代理beanFactory
 *
 * @author lcj
 * @date 2023/4/24
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    AbstractAutowireCapableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    /**
     * context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
     * @param fileName
     * @param isRefresh
     */
    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
        Resource resource = new ClassPathXmlResource(fileName);
        AbstractAutowireCapableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if(isRefresh){
            refresh();
        }
    }

    public ClassPathXmlApplicationContext(String fileName){
        this(fileName, true);
    }

    public void refresh(){
        // 给beanFactory注册bean处理器
        registerBeanPostProcessors(this.beanFactory);
        // 实例化所有bean
        onRefresh();

    }

    public void onRefresh(){
        this.beanFactory.refresh();
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }

    @Override
    public Boolean containsBean(String beanName) {
        return null;
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class getType(String name) {
        return null;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return this.beanFactoryPostProcessors;
    }

    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    private void registerBeanPostProcessors(AbstractAutowireCapableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }
}
