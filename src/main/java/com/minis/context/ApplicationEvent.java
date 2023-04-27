package com.minis.context;

import java.util.EventObject;

/**
 * @author lcj
 * @date 2023/4/25
 */
public class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
