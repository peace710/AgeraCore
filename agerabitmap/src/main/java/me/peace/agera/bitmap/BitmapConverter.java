package me.peace.agera.bitmap;

import android.graphics.BitmapFactory;

import com.google.android.agera.Result;
import com.google.android.agera.net.HttpResponse;

import me.peace.agera.core.Converter;

/**
 * Created by User_Kira on 2016/8/31.
 */
public class BitmapConverter<T> implements Converter<HttpResponse,T> {
    BitmapConverter() {
    }

    @Override
    public T convert(HttpResponse response) throws Exception {
        byte[] data = response.getBody();
        return (T)Result.absentIfNull(BitmapFactory.decodeByteArray(data, 0, data.length));
    }

}
