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
package net.awairo.minecraft.tooltipplus;

import java.util.Properties;

/**
 * Version class.
 * 
 * @author alalwww
 */
public final class Version
{
    private static String major;
    private static String minor;
    private static String revision;

    private static String build;
    private static String githash;

    private static String mcversion;

    private static String version;

    private static boolean initialized = false;;

    /**
     * set mod id and version.properties.
     */
    static void setVersion(String pref, Properties prop)
    {
        if (!initialized)
        {
            initialized = true;

            major = prop.getProperty(pref + ".version.major");
            minor = prop.getProperty(pref + ".version.minor");
            build = prop.getProperty(pref + ".version.build");
            revision = prop.getProperty(pref + ".version.revision");

            githash = prop.getProperty(pref + ".version.githash");

            version = String.format("%s.%s.%s #%s", major, minor, build, revision);
        }
    }

    public static void initializeFromModLoader()
    {
        if (initialized)
        {
            initialized = true;

            version = "non-supported ModLoader version.";
            githash = null;
        }
    }

    public static String getVersionString()
    {
        return version;
    }

    public static String getGithash()
    {
        return githash;
    }

    public static boolean isInitialized()
    {
        return initialized;
    }

    private Version()
    {
    }
}
