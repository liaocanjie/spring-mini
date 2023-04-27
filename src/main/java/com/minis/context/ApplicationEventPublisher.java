package com.minis.context;

/**
 * @author lcj
 * @date 2023/4/25
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);
}
