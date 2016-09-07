package me.peace.agera;

import com.google.android.agera.Repository;
import com.google.android.agera.Result;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import me.peace.agera.core.HttpMethod;
import static me.peace.agera.Utils.getRawType;

/**
 * Created by User_Kira on 2016/8/17.
 */
public class ServiceMethod implements HttpMethod {
    protected Method method;
    protected Annotation[] methodAnnotations;
    protected Annotation[][] argumentAnnotations;
    protected Class[] argumentTypes;
    protected Type convertType;

    public ServiceMethod(Method method) {
        this.method = method;
        methodAnnotations = method.getDeclaredAnnotations();
        argumentAnnotations = method.getParameterAnnotations();
        argumentTypes = method.getParameterTypes();
        convertType = obtainReturnType(method.getGenericReturnType());
    }

    public String getQueryKey(int index) {
        for (Annotation annotation : argumentAnnotations[index]) {
            if (annotation instanceof Query) {
                return ((Query) annotation).value();
            }
        }
        return "";
    }

    public String url() {
        for (Annotation annotation : methodAnnotations) {
            if (annotation instanceof Get) {
                return ((Get) annotation).value();
            }
            if (annotation instanceof Post) {
                return ((Post) annotation).value();
            }
            if (annotation instanceof Rest){
                return ((Rest) annotation).value();
            }
        }
        throw new RuntimeException("no GET,POST or REST request");
    }

    public int requestType(){
        for (Annotation annotation : methodAnnotations) {
            if (annotation instanceof Get) {
                return GET;
            }
            if (annotation instanceof Post) {
                return POST;
            }
            if (annotation instanceof Rest){
                return REST;
            }
        }
        throw new RuntimeException("no GET,POST or REST request");
    }

    private Type obtainReturnType(Type returnType){
        if (getRawType(returnType) != Repository.class) {
            throw new IllegalStateException("Return type must be parameterized"
                    + " as Repository<Result<Foo>> or Supplier<Result<? extends Foo>>");
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException("Return type must be parameterized"
                    + " as Repository<Result<Foo>> or Supplier<Result<? extends Foo>>");
        }

        ParameterizedType parameterizedType = (ParameterizedType) returnType;
        Type innerType  = parameterizedType.getActualTypeArguments()[0];
        if (getRawType(innerType) != Result.class) {
            throw new IllegalStateException("Return type must be parameterized"
                    + " as Repository<Result<Foo>> or Supplier<Result<? extends Foo>>");
        }

        if (!(innerType instanceof ParameterizedType)){
            throw new IllegalStateException("Return type must be parameterized"
                    + " as Repository<Result<Foo>> or Supplier<Result<? extends Foo>>");
        }

        ParameterizedType innerTypeOfInnerType = (ParameterizedType) innerType;
        return innerTypeOfInnerType.getActualTypeArguments()[0];
    }
}
