package me.peace.agera.gson;

import android.text.TextUtils;

import com.google.android.agera.Result;
import com.google.android.agera.net.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import me.peace.agera.core.Converter;

/**
 * Created by User_Kira on 2016/8/31.
 */
public class GsonConverter<T> implements Converter<HttpResponse,T> {
    private final TypeAdapter<T> adapter;

    GsonConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(HttpResponse response) throws Exception {
        String data = new String(response.getBody());
        if (TextUtils.isEmpty(data)){
            return (T) Result.absentIfNull(data);
        }
        return (T)Result.absentIfNull(adapter.fromJson(data));
    }

}
