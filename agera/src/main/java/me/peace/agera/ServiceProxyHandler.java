package me.peace.agera;

import android.support.annotation.NonNull;
import java.lang.reflect.Type;
import static me.peace.agera.Utils.generateParams;
import me.peace.agera.core.AgeraRepository;
import me.peace.agera.core.Converter;
import me.peace.agera.core.HttpMethod;


/**
 * Created by User_Kira on 2016/8/18.
 */
public class ServiceProxyHandler implements ProxyHandler,HttpMethod {
    private final String baseUrl;
    private final Converter.Factory factory;

    public ServiceProxyHandler(@NonNull String baseUrl,@NonNull Converter.Factory factory) {
        this.baseUrl = baseUrl;
        this.factory = factory;
    }

    @Override
    public Object exec(ServiceMethod serviceMethod, Object[] args) {
        String url = serviceMethod.url();
        int method = serviceMethod.requestType();
        String params = "";
        if (REST == method){
            url = obtainUrl(url,serviceMethod,args);
        }else {
            params = obtainParams(serviceMethod, args);
        }
        Type convertType = serviceMethod.convertType;
        return new AgeraRepository<>(factory.responseConverter(convertType),baseUrl + url,params,method).obtainRepository();
    }

    private int obtainArgumentCount(ServiceMethod serviceMethod, Object[] args){
        int argumentCount = args != null ? args.length : 0;
        if (argumentCount != serviceMethod.argumentTypes.length) {
            throw new IllegalArgumentException("Argument count ("
                    + argumentCount + ") doesn't match expected count ("
                    + serviceMethod.argumentTypes.length + ")");
        }
        return argumentCount;
    }

    private String obtainUrl(String url,ServiceMethod serviceMethod, Object[] args){
        int count = obtainArgumentCount(serviceMethod, args);
        for (int i = 0; i < count; i++) {
            String key = serviceMethod.getQueryKey(i);
            String value = args[i].toString();
            String placeHolder = "{" + key +  "}";
            url = url.replace(placeHolder,value);
        }
        return url;
    }

    private String obtainParams(ServiceMethod serviceMethod, Object[] args){
        int count = obtainArgumentCount(serviceMethod, args);
        String[] keys = new String[count];
        String[] value = new String[count];
        for (int i = 0; i < count; i++) {
            keys[i] = serviceMethod.getQueryKey(i);
            value[i] = args[i].toString();
        }
        return generateParams(keys,value);
    }

}
