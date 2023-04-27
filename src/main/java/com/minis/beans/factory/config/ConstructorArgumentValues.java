package com.minis.beans.factory.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lcj
 * @date 2023/4/25
 */

public class ConstructorArgumentValues {
    private final List<ConstructorArgumentValue> indexedArgumentValues = new ArrayList<>();
    private final List<ConstructorArgumentValue> genericArgumentValues = new LinkedList<>();

    public void addArgumentValue(ConstructorArgumentValue newValue) {
        this.indexedArgumentValues.add(newValue);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    public void addGenericArgumentValue(Object value, String type, String name) {
        this.genericArgumentValues.add(new ConstructorArgumentValue(type, name, value));
    }

    private void addGenericArgumentValue(ConstructorArgumentValue newValue) {
        if (newValue.getName() != null) {
            Iterator<ConstructorArgumentValue> it;
            for (it = this.genericArgumentValues.iterator(); it.hasNext(); ) {
                ConstructorArgumentValue currentValue = it.next();
                if (newValue.getName().equals(currentValue.getName())) {
                    it.remove();
                }
            }
        }
        this.genericArgumentValues.add(newValue);
    }
    public ConstructorArgumentValue getGenericArgumentValue(String requiredName) {
        for (ConstructorArgumentValue valueHolder : this.genericArgumentValues) {
            if (valueHolder.getName() != null && (requiredName == null || !valueHolder.getName().equals(requiredName))) {
                continue;
            }
            return valueHolder;
        }
        return null;
    }
    public int getArgumentCount() {
        return this.genericArgumentValues.size();
    }
    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();
    }
}
