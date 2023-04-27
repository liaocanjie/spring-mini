package com.minis.core;

/**
 * @author lcj
 * @date 2023/4/25
 */
public class PropertyValue {

    private String type;

    private String name;

    private Object value;

    private boolean isRef;

    public PropertyValue(String type, String name, Object value, boolean isRef) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.isRef = isRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsRef() {
        return isRef;
    }

    public void setRef(boolean ref) {
        isRef = ref;
    }
}
