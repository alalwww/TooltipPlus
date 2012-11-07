package net.awairo.tooltipplus;

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
            revision = prop.getProperty(pref + ".version.revision");

            build = prop.getProperty(pref + ".build");
            githash = prop.getProperty(pref + ".githash");

            version = String.format("%s.%s.%s #%s", major, minor, revision, build);
        }
    }

    public static void initializeFromModLoader()
    {
        if (initialized)
        {
            initialized = true;

            version = "2.0.0 or higher version for ModLoader user.(non-supported)";
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
