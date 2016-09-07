package me.peace.agera.converter;

import android.text.TextUtils;

import com.google.android.agera.Result;
import com.google.android.agera.net.HttpResponse;

import me.peace.agera.core.Converter;

/**
 * Created by Kira on 2016/9/7.
 */
final class DefaultConverter<T> implements Converter<HttpResponse,T> {
    DefaultConverter(){}

    @Override
    public T convert(HttpResponse response) throws Exception {
        String data = new String(response.getBody());
        return (T)Result.absentIfNull(data);
    }
}
