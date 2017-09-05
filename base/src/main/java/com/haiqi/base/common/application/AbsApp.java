package com.haiqi.base.common.application;

import android.app.Application;


import java.lang.reflect.Method;


/**
 *  增强程序堆内存的处理效率,能很好的解决OOM问题
 */
public class AbsApp extends Application {

    public AbsApp() {
        try {
            Class<?> clazz = this.getClass().getClassLoader().loadClass("dalvik.system.VMRuntime");
            Method method = clazz.getMethod("getRuntime", new Class[]{});
            method.setAccessible(true);
            Object instance = method.invoke(clazz);
            method = instance.getClass().getMethod("setTargetHeapUtilization", new Class[]{float.class});
            method.setAccessible(true);
            method.invoke(instance, new Object[]{0.75f});
//            System.out.println("已优化Dalvik虚拟机的堆内存分配!");
        } catch (Exception e) {
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
//        Mylogger.d("Application", "内存不够了。 maxMemory:" + (((float) Runtime.getRuntime().maxMemory() / (1024 * 1024))) + " MB");
        super.onLowMemory();
    }

}
