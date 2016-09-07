package me.peace.agera;

import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User_Kira on 2016/8/31.
 */
public class Utils {

    private Utils() {
    }

    public static Class<?> getRawType(Type type) {
        if (type == null) throw new NullPointerException("type == null");

        if (type instanceof Class<?>) {
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) throw new IllegalArgumentException();
            return (Class<?>) rawType;
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        }

        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                + "GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
    }

    public static <T> T checkNotNull(T reference) {
        if(null == reference) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static String generateParams(String[] keys, String[] values) {
        checkNotNull(keys);
        checkNotNull(values);
        int keySize = keys.length;
        int valueSize = values.length;
        if (keySize != valueSize){
            throw new IllegalArgumentException();
        }
        StringBuffer params = new StringBuffer();
        if (keySize > 0) {
            for (int i = 0; i < keySize; i++) {
                params.append(keys[i]).append("=").append(values[i]);
                if (i != keySize - 1){
                    params.append("&");
                }
            }
        }
        return params.toString();
    }

    public static Map<String,String> generateMapParams(String[] keys, String[] values){
        checkNotNull(keys);
        checkNotNull(values);
        int keySize = keys.length;
        int valueSize = values.length;
        if (keySize != valueSize){
            throw new IllegalArgumentException();
        }
        Map<String,String> params = null;
        if (keySize > 0) {
            params = new HashMap<>();
            for (int i = 0; i < keySize; i++) {
                params.put(keys[i],values[i]);
            }
        }
        return params;
    }

    public static void checkState(final boolean expression, @NonNull final String errorMessage) {
        if (!expression) {
            throw new IllegalStateException(errorMessage);
        }
    }
}
