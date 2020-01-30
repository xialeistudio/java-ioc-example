package org.xialei.beans.factory;

import org.xialei.beans.InitializingBean;
import org.xialei.beans.annotation.Autowired;
import org.xialei.beans.annotation.Bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class AnnotationBeanFactory implements BeanFactory {
    private Map<Class<?>, Object> beans = new HashMap<>();

    /**
     *
     * @param classes Configuration class list
     */
    public AnnotationBeanFactory(Class<?>... classes) throws Exception {
        for (Class<?> clazz : classes) {
            Object instance = clazz.newInstance();
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                Bean beanAnnotation = method.getAnnotation(Bean.class);
                if (beanAnnotation != null) {
                    // todo: dependency process
                    Object bean = method.invoke(instance);
                    Class<?> returnType = method.getReturnType();
                    if (bean instanceof BeanFactoryAware) {
                        ((BeanFactoryAware) bean).setBeanFactory(this);
                    }
                    if (bean instanceof InitializingBean) {
                        ((InitializingBean) bean).afterPropertiesSet();
                    }
                    beans.put(returnType, bean);
                }
            }
        }
    }

    @Override
    public <T> T getBean(Class<T> clazz) throws Exception {
        if (beans.containsKey(clazz)) {
            return (T) beans.get(clazz);
        }
        Constructor<?>[] constructors = clazz.getConstructors();
        // iterate constructors, scan for none-params constructor or constructor with {@link Autowired}
        for (Constructor<?> constructor : constructors) {
            Autowired autowired = constructor.getAnnotation(Autowired.class);
            if (autowired != null || constructor.getParameterCount() == 0) {
                // iterate parameters, inject dependency from beans map.
                Parameter[] parameters = constructor.getParameters();
                Object[] constructorArgs = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    Class<?> paramClass = parameters[i].getType();
                    if (beans.containsKey(paramClass)) {
                        constructorArgs[i] = beans.get(paramClass);
                    }
                }
                T instance = (T) constructor.newInstance(constructorArgs);
                if (instance instanceof BeanFactoryAware) {
                    ((BeanFactoryAware) instance).setBeanFactory(this);
                }
                if (instance instanceof InitializingBean) {
                    ((InitializingBean) instance).afterPropertiesSet();
                }
                beans.put(clazz, instance);
                return instance;
            }
        }
        throw new Exception("No available bean found");
    }
}
