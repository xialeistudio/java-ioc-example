package org.xialei.beans.example;

import org.xialei.beans.factory.AnnotationBeanFactory;
import org.xialei.beans.factory.BeanFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        BeanFactory beanFactory = new AnnotationBeanFactory(Configuration.class);
        Knight knight = beanFactory.getBean(Knight.class);
        knight.use();
    }
}
