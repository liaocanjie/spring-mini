package com.minis.beans.factory.xml;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;
import com.minis.core.PropertyValue;
import com.minis.core.PropertyValues;
import com.minis.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据外部配置Resouce注册BeanDefinition
 * @author lcj
 * @date 2023/4/25
 */
public class XmlBeanDefinitionReader {

    BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource){
        while (resource.hasNext()){
            Element element = (Element) resource.next();
            // 获取bean的基本信息 创建BeanDefinition
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);

            // 处理属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                String ref = e.attributeValue("ref");
                String V = "";
                boolean isRef = false;
                if(value != null && !value.equals("")){
                    V = value;
                }else if(ref != null && !ref.equals("")){
                    isRef = true;
                    V = ref;
                    refs.add(ref);
                }
                PVS.addPropertyValue(new PropertyValue(type, name, value, isRef));
            }
            beanDefinition.setPropertyValues(PVS);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);

            // 处理构造器参数
            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues AVS = new ConstructorArgumentValues();
            for (Element e : constructorElements) {
                String aType = e.attributeValue("type");
                String aName = e.attributeValue("name");
                String aValue = e.attributeValue("value");
                AVS.addArgumentValue(new ConstructorArgumentValue(aType, aName, aValue));
            }
            beanDefinition.setConstructorArgumentValues(AVS);

            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
