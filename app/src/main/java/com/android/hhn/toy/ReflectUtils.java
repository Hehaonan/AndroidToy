package com.android.hhn.toy;

import java.lang.reflect.Method;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/5/7,3:53 PM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class ReflectUtils {

    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(final Object obj, final String name) {
        return invokeMethod(obj, name, new Class[0], new Object[0]);
    }

    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(final Object obj, final String name, final Class[] types, final Object[] args) {
        if (null != obj && null != name && null != types && null != args && types.length == args.length) {
            try {
                final Method method = getMethod(obj.getClass(), name, types);
                if (null != method) {
                    method.setAccessible(true);
                    return (T) method.invoke(obj, args);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        return null;
    }

    private static Method getMethod(final Class<?> klass, final String name, final Class<?>[] types) {
        try {
            return klass.getDeclaredMethod(name, types);
        } catch (final NoSuchMethodException e) {
            final Class<?> parent = klass.getSuperclass();
            if (null == parent) {
                return null;
            }
            return getMethod(parent, name, types);
        }
    }

}
