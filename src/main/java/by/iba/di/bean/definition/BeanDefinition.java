package by.iba.di.bean.definition;

import by.iba.di.annotations.scopes.ScopeType;

public class BeanDefinition {

    private boolean isProxy = false;

    private Class classType;

    private ScopeType scope = ScopeType.SINGLETON;

    private String beanName;

    public Class getClassType() {
        return classType;
    }

    public ScopeType getScope() {
        return scope;
    }

    public String getBeanName() {
        return beanName;
    }

    public boolean isProxy() {
        return isProxy;
    }


    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public void setScope(ScopeType scope) {
        this.scope = scope;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setProxy(boolean proxy) {
        isProxy = proxy;
    }


}
