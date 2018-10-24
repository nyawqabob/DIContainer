package by.iba.di.runner;

import by.iba.di.Scanner;
import by.iba.di.bean.factory.impl.BeanFactoryImpl;

import java.util.Set;

public class DIRunner {

    private static Scanner scanner;
    private static BeanFactoryImpl beanFactory;

    public static BeanFactoryImpl getBeanFactory() {
        scanner = new Scanner();
        scanner.scan();
        Set<Class<?>> annotatedClasses = scanner.getAnnotatedClasses();
        beanFactory = new BeanFactoryImpl(annotatedClasses);
        return beanFactory;
    }

}
