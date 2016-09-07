package me.peace.agera.converter;

import com.google.android.agera.net.HttpResponse;

import java.lang.reflect.Type;

import me.peace.agera.core.Converter;

/**
 * Created by Kira on 2016/9/7.
 */
public final class DefaultConverterFactory extends Converter.Factory{
    private DefaultConverterFactory(){
    }

    public static DefaultConverterFactory create(){
        return new DefaultConverterFactory();
    }

    @Override
    public Converter<HttpResponse, ?> responseConverter(Type type) {
        return new DefaultConverter<>();
    }
}
