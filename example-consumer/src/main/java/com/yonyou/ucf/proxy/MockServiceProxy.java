package com.yonyou.ucf.proxy;

import com.yonyou.ucf.common.model.User;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午6:26
 * @description 模拟调用-动态设置一个固定值
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        log.info("mock invoke {}",method.getName());
        return getDefaultObject(returnType);
    }
    /**
     * 生成指定类型的默认对象值
     * */
    private Object getDefaultObject(Class<?> type){
        //基本类型
        if (type.isPrimitive()){
            if (type == boolean.class){
                return false;
            } else if (type == short.class) {
                return 0;
            } else if (type == int.class) {
                return 1;
            } else if (type == long.class) {
                return 1L;
            }
        }
        return null;
    }
}
