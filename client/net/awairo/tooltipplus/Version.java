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

    /**
     * set mod id and version.properties.
     */
    static void setVersion(String pref, Properties prop)
    {
        major = prop.getProperty(pref + ".version.major");
        minor = prop.getProperty(pref + ".version.minor");
        revision = prop.getProperty(pref + ".version.revision");

        build = prop.getProperty(pref + ".build");
        githash = prop.getProperty(pref + ".githash");

        version = String.format("%s.%s.%s #%s", major, minor, revision, build);
    }

    public static String getVersion()
    {
        return version;
    }

    public static String getGithash()
    {
        return githash;
    }

    private Version()
    {
    }
}
