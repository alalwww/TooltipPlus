package net.awairo.minecraft.tooltipplus.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import net.minecraft.client.Minecraft;

/**
 * settings helper.
 *
 * @author alalwww
 * @version 1.0.0
 */
public class SettingsHelper
{
    private static File configDir;

    /**
     * load configure file to properties.
     *
     * @param properties
     *            properties
     * @param configFile
     *            config file
     *
     * @throws RuntimeException
     *             It's so bug ridden.
     */

    public static synchronized void load(Properties properties, File configFile) throws RuntimeException
    {
        try
        {
            checkFile(configFile);
            properties.load(new FileInputStream(configFile));
        }
        catch (Exception e)
        {
            Log.severe(e, "config load failed. (file=%s)", configFile);

            if (e instanceof RuntimeException)
            {
                throw(RuntimeException) e;
            }

            throw new RuntimeException(e);
        }
    }

    /**
     * store properties to configure file.
     *
     * @param properties
     *            properties
     * @param configFile
     *            config file
     * @param comments
     *            comments
     *
     * @throws RuntimeException
     *             It's so bug ridden.
     */

    public static synchronized void store(Properties properties, File configFile, String comments)
    throws RuntimeException
    {
        try
        {
            checkFile(configFile);

            if (!configFile.canWrite())
            {
                throw new IllegalStateException("could not write file." + configFile.getAbsolutePath());
            }

            FileOutputStream fos = null;

            try
            {
                fos = new FileOutputStream(configFile);
                properties.store(fos, comments);
            }
            finally
            {
                if (fos != null)
                {
                    fos.close();
                }
            }
        }
        catch (Exception e)
        {
            Log.severe(e, "config store failed. (file=%s)", configFile);

            if (e instanceof RuntimeException)
            {
                throw(RuntimeException) e;
            }

            throw new RuntimeException(e);
        }
    }

    /**
     * get "%appdata%\.minecraft\config" directory.
     *
     * @return config directory
     */
    public static File getConfigDir()
    {
        if (configDir != null)
        {
            return configDir;
        }

        if (ReflectionHelper.findMod("cpw.mods.fml.common.Loader"))
        {
            // for FML
            configDir = cpw.mods.fml.common.Loader.instance().getConfigDir();
            return configDir;
        }

        if (ReflectionHelper.findMod("net.minecraft.src.ModLoader"))
        {
            // for original ModLoader
            configDir = ReflectionHelper.getPrivateValue(net.minecraft.src.ModLoader.class, null, "cfgdir");
            return configDir;
        }

        configDir = new File(Minecraft.getMinecraftDir(), "config");
        return configDir;
    }

    /**
     * get value and cast.
     *
     * @param properties
     *            properties
     * @param key
     *            property key
     * @param defaultValue
     *            default value (not null)
     * @return casted value
     *
     * @throws RuntimeException
     *             It's so bug ridden.
     */
    public static <V> V getValue(Properties properties, String key, V defaultValue) throws RuntimeException
    {
        assert defaultValue != null;
        String value = properties.getProperty(key);

        if (value == null)
        {
            properties.setProperty(key, defaultValue.toString());
            return defaultValue;
        }

        if (defaultValue instanceof String)
        {
            return (V) value;
        }

        Object ret;

        if (defaultValue instanceof Boolean)
        {
            ret = Boolean.parseBoolean(value);
        }
        else if (defaultValue instanceof Integer)
        {
            ret = Integer.parseInt(value);
        }
        else if (defaultValue instanceof Long)
        {
            ret = Long.parseLong(value);
        }
        else if (defaultValue instanceof Float)
        {
            ret = Float.parseFloat(value);
        }
        else if (defaultValue instanceof Double)
        {
            ret = Double.parseDouble(value);
        }
        else if (defaultValue instanceof Short)
        {
            ret = Short.parseShort(value);
        }
        else if (defaultValue instanceof Byte)
        {
            ret = Byte.parseByte(value);
        }
        else
        {
            Log.severe("cannot cast. (key=%s, value=%s, type=%s)", key, value, defaultValue.getClass());
            throw new IllegalArgumentException();
        }

        return (V) ret;
    }

    private static void checkFile(File file) throws IOException
    {
        if (file.isDirectory())
        {
            throw new IllegalArgumentException("'file' is directory. :" + file.getAbsolutePath());
        }

        if (!file.exists())
        {
            if (file.createNewFile())
            {
                Log.info("config file created. (path=%s)", file.getAbsolutePath());
            }
            else
            {
                if (!file.exists())
                {
                    throw new IllegalStateException("File creation failed. :" + file.getAbsolutePath());
                }
            }
        }
    }
}
