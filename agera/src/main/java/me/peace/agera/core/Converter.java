package me.peace.agera.core;

import com.google.android.agera.net.HttpResponse;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by User_Kira on 2016/8/31.
 */
public interface Converter<F,T> {
    T convert(F value) throws Exception;

    abstract class Factory{
        public Converter<HttpResponse,?> responseConverter(Type type){
            return null;
        }
    }
}
