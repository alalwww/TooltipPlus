/*
 * TooltipPlus
 * (c) 2012 alalwww
 *
 * License is like to the MIT, for more information please read the Japanese.
 *
 * ソースコードの部分流用に関して、一切の制限は設けません。
 * ただしあらゆる意味での保障も行いません。
 * Modとしての再配布については、README.txt を参照ください。
 */
package net.awairo.minecraft.tooltipplus.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import net.minecraft.client.settings.GameSettings;

/**
 * reflection helper.
 * 
 * @author alalwww
 * @version 1.0.0
 */
public class ReflectionHelper
{
    // 難読化されてるかチェック クラスの無意味な依存関係ができる点は目を瞑る方向
    private static final boolean DEBUG = GameSettings.class.getPackage() != null;

    private static enum Type
    {
        FIELD_BY_INDEX, FIELD_BY_NAME, METHOD_BY_INDEX, METHOD_BY_NAME,
    }

    /**
     * check for installed of other mod.
     * 
     * <p>難読化されている場合、引数の完全名の先頭が net.minecraft.src. だった場合、その部分を削除します。</p>
     * 
     * @param modClassName
     *            mod full name.
     * @return true is installed.
     * 
     * @throws RuntimeException
     *             it's so bug ridden!
     */
    public static boolean findMod(String modClassName) throws RuntimeException
    {
        if (!DEBUG && modClassName.startsWith("net.minecraft.src."))
        {
            modClassName = modClassName.substring(18, modClassName.length());
        }

        try
        {
            Class.forName(modClassName);
            return true;
        }
        catch (ClassNotFoundException e)
        {
            return false;
        }
    }

    /**
     * get private value by prop index.
     * 
     * @param clazz
     *            target class
     * @param obj
     *            target class instance or null
     * @param fieldIndex
     *            target class field index
     * @return private value
     * 
     * @throws RuntimeException
     *             it's so bug ridden!
     */
    public static <T, E> T getPrivateValue(Class<? super E> clazz, E instance, int fieldIndex) throws RuntimeException
    {
        return get(Type.FIELD_BY_INDEX, clazz, instance, Integer.valueOf(fieldIndex));
    }

    /**
     * get private value by prop name.
     * 
     * @param clazz
     *            target class
     * @param obj
     *            target class instance or null
     * @param fieldName
     *            target class field name
     * @return private value or null
     * 
     * @throws RuntimeException
     *             it's so bug ridden!
     */
    public static <T, E> T getPrivateValue(Class<? super E> clazz, E instance, String fieldName)
            throws RuntimeException
    {
        return get(Type.FIELD_BY_NAME, clazz, instance, fieldName);
    }

    /**
     * get method by index.
     * 
     * @param clazz
     *            target class
     * @param methodIndex
     *            target class method index
     * @return method
     * 
     * @throws RuntimeException
     *             it's so bug ridden!
     */
    public static <E> Method getMethod(Class<? super E> clazz, int methodIndex) throws RuntimeException
    {
        return get(Type.METHOD_BY_INDEX, clazz, null, Integer.valueOf(methodIndex));
    }

    /**
     * get method by name.
     * 
     * @param clazz
     *            target class
     * @param methodName
     *            target class method name
     * @return method
     * 
     * @throws RuntimeException
     *             it's so bug ridden!
     */
    public static <E> Method getMethod(Class<? super E> clazz, String methodName) throws RuntimeException
    {
        return get(Type.METHOD_BY_NAME, clazz, null, methodName);
    }

    /**
     * invoke method.
     * 
     * @param m
     *            method
     * @param instance
     *            class instance or null
     * @param args
     *            arguments
     * @return result
     * 
     * @throws RuntimeException
     *             it's so bug ridden!
     */
    public static <E, R> R invoke(Method m, Object instance, Object... args) throws RuntimeException
    {
        try
        {
            return (R) m.invoke(instance, args);
        }
        catch (Exception e)
        {
            Object argsValue;

            if (args == null || !args.getClass().isArray())
            {
                argsValue = args;
            }
            else
            {
                argsValue = Arrays.toString(args);
            }

            String f = "reflection failed. (class=%s, method=%s, instance=%s, args=%s)";
            Log.severe(e, f, m.getDeclaringClass().getName(), m.getName(), instance, argsValue);

            if (e instanceof RuntimeException)
            {
                throw (RuntimeException) e;
            }

            throw new RuntimeException(e);
        }
    }

    private static <T, E, V> T get(Type type, Class<? super E> clazz, E instance, V key)
    {
        try
        {
            switch (type)
            {
                case FIELD_BY_INDEX:
                    return (T) getPrivateValueInternalByIndex(clazz, instance, (Integer) key);

                case FIELD_BY_NAME:
                    return (T) getPrivateValueInternalByName(clazz, instance, (String) key);

                case METHOD_BY_INDEX:
                    return (T) getPrivateMethodInternalByIndex(clazz, (Integer) key);

                case METHOD_BY_NAME:
                    return (T) getPrivateMethodInternalByName(clazz, (String) key);
            }

            throw new InternalError("unexpected target value. : " + key);
        }
        catch (Exception e)
        {
            Log.severe(e, "reflection failed. (class=%s, key=%s)", clazz.getName(), key);

            if (e instanceof RuntimeException)
            {
                throw (RuntimeException) e;
            }

            throw new RuntimeException(e);
        }
    }

    private static <E> Method getPrivateMethodInternalByIndex(Class<? super E> clazz, int index)
    {
        Method m = clazz.getDeclaredMethods()[index];
        m.setAccessible(true);
        return m;
    }

    private static <E> Method getPrivateMethodInternalByName(Class<? super E> clazz, String name)
            throws NoSuchMethodException
    {
        Method m = clazz.getDeclaredMethod(name);
        m.setAccessible(true);
        return m;
    }

    private static <T, E> T getPrivateValueInternalByIndex(Class<? super E> clazz, E instance, int index)
            throws IllegalAccessException
    {
        Field f = clazz.getDeclaredFields()[index];
        f.setAccessible(true);
        return (T) f.get(instance);
    }

    private static <T, E> T getPrivateValueInternalByName(Class<? super E> clazz, E instance, String name)
            throws IllegalAccessException, NoSuchFieldException
    {
        Field f = clazz.getDeclaredField(name);
        f.setAccessible(true);
        return (T) f.get(instance);
    }
}
