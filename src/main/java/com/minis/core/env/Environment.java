package com.minis.core.env;

/**
 * @author lcj
 * @date 2023/4/27
 */
public interface Environment {

    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    boolean acceptsProfiles(String... profiles);
}
