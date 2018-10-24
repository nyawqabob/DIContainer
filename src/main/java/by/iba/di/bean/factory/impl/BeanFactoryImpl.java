package by.iba.di.bean.factory.impl;

import by.iba.di.bean.BeansHandler;
import by.iba.di.bean.definition.BeanDefinitionsHandler;
import by.iba.di.bean.exception.BeanException;
import by.iba.di.bean.factory.BeanFactory;
import by.iba.di.bean.parameter_type.ParameterType;
import by.iba.di.bean.parameter_type.ParameterTypeHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Map;
import java.util.Set;

public class BeanFactoryImpl implements BeanFactory {

    private BeansHandler beansHandler;
    private Map<String, Object> singletonBeans;

    private static final Logger LOGGER = LogManager.getLogger(BeanFactoryImpl.class);

    public BeanFactoryImpl(Set<Class<?>> scannedClasses) {
        beansHandler = BeansHandler.getInstance(scannedClasses);
        singletonBeans = beansHandler.getSingletonBeans();
    }


    public <T> T getBean(String beanName) {
        T returnedBean = getBeanByParameterType(beanName);
        return returnedBean;
    }

    public <T> T getBean(Class<T> requiredType) {
        T returnedBean = getBeanByParameterType(requiredType);
        return returnedBean;
    }


    public <T> T getBean(Class<T> requiredType, Object... constructParams) {
        T returnedBean = getBeanByParameterType(requiredType, constructParams);
        return returnedBean;
    }

    public <T> T getBean(String beanName, Object... constructParams) {
        T returnedBean = getBeanByParameterType(beanName, constructParams);
        return returnedBean;
    }

    private <T> T getBeanByParameterType(Object parameter, Object... argumentParameters) {
        ParameterType parameterType = ParameterTypeHandler.chooseType(parameter);
        switch (parameterType) {
            case STRING:
                return getObjectByStringParameter(parameter, argumentParameters);
            case CLASS:
                return getObjectByClassParameter(parameter, argumentParameters);
        }
        return null;
    }

    private <T> T getObjectByStringParameter(Object beanName, Object... constructParams) {
        T returnedBean = null;
        for (Map.Entry<String, Object> bean : singletonBeans.entrySet()) {
            if (bean.getKey().equals(beanName)) {
                if (constructParams == null || constructParams.length == 0) {
                    returnedBean = (T) bean.getValue();
                    break;
                } else {
                    try {
                        returnedBean = (T) createNotSingletonObject(bean.getValue().getClass(), constructParams);
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                        throw new BeanException(e.getMessage(), e);
                    }
                }
            }
        }
        if (returnedBean == null) {
            if (isExistBeanDefinitionWithBeanName(beanName)) {
                try {
                    Class classType = BeanDefinitionsHandler.getInstance().getClassTypeByBeanDefinitionName((String) beanName);
                    returnedBean = (T) createNotSingletonObject(classType, constructParams);
                } catch (Exception e) {
                    throw new BeanException(e.getMessage(), e);
                }
            } else {
                LOGGER.info("Bean with class type " + beanName + " was not found");
            }

        }
        return returnedBean;
    }

    private <T> T getObjectByClassParameter(Object classType, Object... constructParams) {
        T returnedBean = null;
        for (Map.Entry<String, Object> bean : singletonBeans.entrySet()) {
            if (bean.getValue().getClass().equals(classType)) {
                if (constructParams == null || constructParams.length == 0) {
                    returnedBean = (T) bean.getValue();
                    break;
                } else {
                    try {
                        returnedBean = (T) createNotSingletonObject(bean.getValue().getClass(), constructParams);
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                        throw new BeanException(e.getMessage(), e);
                    }
                    break;
                }
            }
        }
        if (returnedBean == null) {
            if (isExistBeanDefinitionWithClassType(classType)) {
                try {
                    returnedBean = (T) createNotSingletonObject((Class) classType, constructParams);
                } catch (Exception e) {
                    throw new BeanException(e.getMessage(), e);
                }
            } else {
                LOGGER.info("Bean with class type " + classType + " was not found");
            }

        }
        return returnedBean;
    }

    private boolean isExistBeanDefinitionWithBeanName(Object beanName) {
        BeanDefinitionsHandler beanDefinitionsHandler = BeanDefinitionsHandler.getInstance();
        return beanDefinitionsHandler.isExistBeanDefinitionWithBeanName(beanName);
    }

    private boolean isExistBeanDefinitionWithClassType(Object classType) {
        BeanDefinitionsHandler beanDefinitionsHandler = BeanDefinitionsHandler.getInstance();
        return beanDefinitionsHandler.isExistBeanDefinitionWithClassType(classType);
    }

    private Object createNotSingletonObject(Class clazz, Object... constructParams) throws Exception {
        Class[] types = new Class[constructParams.length];
        for (int i = 0; i < constructParams.length; i++) {
            types[i] = constructParams[i].getClass();
        }
        return clazz.getDeclaredConstructor(types).newInstance(constructParams);
    }


}
