package com.agera.gson;

import com.google.android.agera.net.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import me.peace.agera.core.Converter;

/**
 * Created by User_Kira on 2016/8/31.
 */
public class GsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    private GsonConverterFactory(Gson gson){
        this.gson  = gson;
    }

    public static GsonConverterFactory create(){
        return new GsonConverterFactory(new Gson());
    }

    @Override
    public Converter<HttpResponse, ?> responseConverter(Type type) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonConverter<>(adapter);
    }
}
