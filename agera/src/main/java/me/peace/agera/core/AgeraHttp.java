package me.peace.agera.core;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.agera.Function;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.RepositoryConfig;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.net.HttpFunctions;
import com.google.android.agera.net.HttpRequest;
import com.google.android.agera.net.HttpRequests;
import com.google.android.agera.net.HttpResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static me.peace.agera.Utils.checkNotNull;


/**
 * Created by User_Kira on 2016/8/17.
 */
public class AgeraHttp<T> implements HttpMethod{
    private String url;
    private String params;
    private int method;
    private Converter<HttpResponse,T> converter;
    private Repository<T> repository;

    public AgeraHttp(String url, String params, int method){
        this.url = url;
        this.params = params;
        this.method = method;
    }

    public void setConverter(@NonNull Converter<HttpResponse,T> converter) {
        this.converter = converter;
    }

    private Executor request = Executors.newSingleThreadExecutor();
    private Executor response = Executors.newSingleThreadExecutor();

    private Supplier<HttpRequest> supplier = new Supplier<HttpRequest>() {
        @NonNull
        @Override
        public HttpRequest get() {
            if (GET == method){
                if (!TextUtils.isEmpty(params)){
                    StringBuffer address = new StringBuffer();
                    address.append(url).append("?").append(params);
                    return HttpRequests.httpGetRequest(address.toString()).compile();
                }
                return HttpRequests.httpGetRequest(url).compile();
            }

            if (POST == method){
                if (!TextUtils.isEmpty(params)){
                    return HttpRequests.httpPostRequest(url).body(params.getBytes()).compile();
                }
                return HttpRequests.httpPostRequest(url).compile();
            }

            if (REST == method){
                return HttpRequests.httpGetRequest(url).compile();
            }
            throw new RuntimeException("no GET,POST or REST request");
        }
    };


    private Function<HttpResponse,T> function = new Function<HttpResponse, T>() {
        @NonNull
        @Override
        public T apply(@NonNull HttpResponse httpResponse) {
            checkNotNull(converter);
            try {
                return converter.convert(httpResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return (T) Result.failure(e);
            }
        }
    };

    private Function<Throwable,T> err = new Function<Throwable, T>() {
        @NonNull
        @Override
        public T apply(@NonNull Throwable e) {
            return (T) Result.failure(e);
        }
    };

    public Repository<T> obtain(T t){
        repository = Repositories.repositoryWithInitialValue(t).observe().onUpdatesPerLoop().getFrom(supplier).goTo(request)
                .attemptTransform(HttpFunctions.httpFunction()).orEnd(err).goTo(response).thenTransform(function).onDeactivation(RepositoryConfig.SEND_INTERRUPT).compile();
        return repository;
    }

}
