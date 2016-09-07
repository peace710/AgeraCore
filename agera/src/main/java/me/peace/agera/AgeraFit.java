package me.peace.agera;

import android.support.annotation.NonNull;

import java.lang.reflect.Proxy;

import me.peace.agera.converter.DefaultConverterFactory;
import me.peace.agera.core.Converter;

import static me.peace.agera.Utils.checkState;

/**
 * Created by User_Kira on 2016/8/18.
 */
public class AgeraFit {

    private ServiceProxyHandler handler;

    private AgeraFit(ServiceProxyHandler handler){
        this.handler = handler;
    }

    public <T> T create(Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new RuntimeException("Service not interface");
        }
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[] { clazz },new ServiceProxy(handler));
    }

    public static class Builder{
        private Converter.Factory factory;
        private String baseUrl;

        public Builder(){

        }

        public Builder setConverterFactory(@NonNull Converter.Factory factory){
            this.factory = factory;
            return this;
        }

        public Builder setBaseUrl(@NonNull String baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        public AgeraFit build(){
            checkState(baseUrl != null,"Base URL required.");
//            checkState(factory != null,"Converter factory required.");
            if (factory == null){
                factory = DefaultConverterFactory.create();
            }
            return  new AgeraFit(new ServiceProxyHandler(baseUrl,factory));
        }

    }

}
