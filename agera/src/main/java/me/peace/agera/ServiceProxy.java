package me.peace.agera;

import android.support.annotation.NonNull;
import static me.peace.agera.Utils.checkNotNull;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by User_Kira on 2016/8/17.
 */
public class ServiceProxy implements InvocationHandler {

    private ProxyHandler handler;

    public ServiceProxy(@NonNull ProxyHandler handler){
        this.handler = checkNotNull(handler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        ServiceMethod serviceMethod = new ServiceMethod(method);
        return handler.exec(serviceMethod,args);
    }

}
