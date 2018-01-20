package com.appc.book.common.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

public class Springs implements ApplicationContextAware {

    private static final String APPLICATION_CONTEXT_KEY = "applicationContext";

    private static Map<String, ApplicationContext> applicationContext = new HashMap<>();


    private static ApplicationContext getApplicationContext() {
        return applicationContext.get(APPLICATION_CONTEXT_KEY);
    }


    public void setApplicationContext(ApplicationContext applicationContext) {
        Springs.applicationContext.put(APPLICATION_CONTEXT_KEY, applicationContext);
    }


    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> baseType) {

        return getApplicationContext().getBean(baseType);

    }

    public static <T> Map<String, T> getBeansOfType(Class<T> baseType) {
        return getApplicationContext().getBeansOfType(baseType);
    }
}