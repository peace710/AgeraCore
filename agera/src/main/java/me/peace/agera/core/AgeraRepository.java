package me.peace.agera.core;

import com.google.android.agera.Repository;
import com.google.android.agera.Result;

/**
 * Created by User_Kira on 2016/8/19.
 */
public class AgeraRepository<T> {
    private AgeraHttp<Result<T>> agera;

    public AgeraRepository(Converter converter,String url,String params,int method){
        agera = new AgeraHttp<>(url,params,method);
        agera.setConverter(converter);
    }

    public Repository obtainRepository(){
        return agera.obtain(Result.<T>absent());
    }
}
