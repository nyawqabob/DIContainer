package by.iba.di;

import by.iba.di.bean.factory.impl.BeanFactoryImpl;
import by.iba.di.entity.*;
import by.iba.di.runner.DIRunner;

public class Main {

    public static void main(String[] args) {

        BeanFactoryImpl beanFactory = DIRunner.getBeanFactory();
        Object object = beanFactory.getBean(Main.class);
        Object secondObject = beanFactory.getBean(Car.class);
        Object thirdObject = beanFactory.getBean("by.iba.di.entity.Car");
        Wheel fourthObject = beanFactory.getBean(Wheel.class);
        Wheel anWheel = beanFactory.getBean(Wheel.class);
        Test fifthObject = beanFactory.getBean("by.iba.di.entity.Test", "A", "B");
        Class classzz = fifthObject.getClass();
        Abc sixthObject = beanFactory.getBean(Abc.class, 10,"50",'1');
        ClassLoader cl = Main.class.getClassLoader();
        Object objecfft = beanFactory.getBean("AFSf");
        Prototype pr = beanFactory.getBean(Prototype.class);
        Class clazz = sixthObject.getClass();
        Prototype secPr = beanFactory.getBean(Prototype.class);
        Prototype thirdPr = beanFactory.getBean(Prototype.class, "A", "B");
        fourthObject.sound();


    }
}
