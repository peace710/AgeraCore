package me.peace.agera.bitmap;

import com.google.android.agera.net.HttpResponse;

import java.lang.reflect.Type;

import me.peace.agera.core.Converter;

/**
 * Created by User_Kira on 2016/8/31.
 */
public class BitmapConverterFactory extends Converter.Factory {
    private BitmapConverterFactory(){
    }

    public static BitmapConverterFactory create(){
        return new BitmapConverterFactory();
    }

    @Override
    public Converter<HttpResponse, ?> responseConverter(Type type) {
        return new BitmapConverter<>();
    }
}
