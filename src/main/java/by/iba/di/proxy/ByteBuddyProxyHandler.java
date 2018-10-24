package by.iba.di.proxy;

import net.bytebuddy.ByteBuddy;

public class ByteBuddyProxyHandler {

    public Object getProxiedObject(Class clazz) throws Exception {

        return new ByteBuddy()
                .subclass(clazz)
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded().newInstance();
    }

}
