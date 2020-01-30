package org.xialei.beans.factory;

public interface BeanFactory {
    <T> T getBean(Class<T> clazz) throws Exception;
}
