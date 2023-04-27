package com.minis.test;

/**
 * @author lcj
 * @date 2023/4/24
 */
public class AServiceImpl implements AService{

    private String property1;

    private String name;

    private int level;

    public AServiceImpl() {

    }

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
        System.out.println(this.name + "," + this.level);
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void sayHello() {
        System.out.println("a service 1 say hello");
        System.out.println(this.property1 + "," + this.getName());
    }
}
