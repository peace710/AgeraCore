package me.peace.agera;

/**
 * Created by User_Kira on 2016/8/18.
 */
public interface ProxyHandler {
    Object exec(ServiceMethod method, Object[] args);
}
