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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * my mod logger.
 * 
 * <ul>
 * <li>severe: error</li>
 * <li>warning: illegal settings</li>
 * <li>info: information</li>
 * <li>fine: debug</li>
 * <li>finer: common debug</li>
 * <li>finest: trace</li>
 * </ul>
 * 
 * @author alalwww
 * @version 1.0.0
 */
public class Log
{
    /** always logging. */
    public static boolean DEBUG = false;

    /** logger. */
    private static Logger LOG;

    private static Handler handler;

    public static void initialize(String modName, boolean debugMode)
    {
        DEBUG = debugMode;
        LOG = Logger.getLogger(modName);

        if (ReflectionHelper.findMod("net.minecraft.src.ModLoader"))
        {
            // FML or ModLoader logger.
            LOG.setParent(net.minecraft.src.ModLoader.getLogger());
        }

        if (DEBUG)
        {
            LOG.setUseParentHandlers(false);
            LOG.setLevel(Level.FINER);

            if (handler == null)
            {
                handler = new ConsoleHandler();
                handler.setFormatter(new LogFormatter());
                handler.setLevel(Level.FINER);
            }

            LOG.addHandler(handler);
        }
        else
        {
            LOG.setUseParentHandlers(true);

            if (handler != null)
            {
                LOG.removeHandler(handler);
            }
        }
    }

    public static Logger getLogger()
    {
        return LOG;
    }

    public static void log(Level level, String format, Object... data)
    {
        if (DEBUG || canLogging(level))
        {
            LOG.log(level, String.format(format, data));
        }
    }

    public static void log(Level level, Throwable e, String format, Object... data)
    {
        if (DEBUG || canLogging(level))
        {
            LOG.log(level, String.format(format, data), e);
        }
    }

    public static void severe(String format, Object... data)
    {
        log(Level.SEVERE, format, data);
    }

    public static void severe(Throwable e, String format, Object... data)
    {
        log(Level.SEVERE, e, format, data);
    }

    public static void warning(String format, Object... data)
    {
        log(Level.WARNING, format, data);
    }

    public static void info(String format, Object... data)
    {
        log(Level.INFO, format, data);
    }

    // FMLのロガーがログレベルの出力で config に対応してないので
    // public static void config(String format, Object... data)
    // {
    // log(Level.CONFIG, format, data);
    // }

    public static void fine(String format, Object... data)
    {
        log(Level.FINE, format, data);
    }

    public static void finer(String format, Object... data)
    {
        log(Level.FINER, format, data);
    }

    public static void finest(String format, Object... data)
    {
        log(Level.FINEST, format, data);
    }

    private static boolean canLogging(Level level)
    {
        return getLogLevel(LOG).intValue() <= level.intValue();
    }

    private static Level getLogLevel(Logger logger)
    {
        if (logger == null)
        {
            return Level.INFO;
        }

        Level level = logger.getLevel();

        if (level == null)
        {
            return getLogLevel(logger.getParent());
        }

        return level;
    }

    private static final class LogFormatter extends Formatter
    {
        static final String LINE_SEPARATOR = System.getProperty("line.separator");

        @Override
        public String format(LogRecord record)
        {
            StringBuilder msg = new StringBuilder();
            Level lvl = record.getLevel();

            if (lvl == Level.FINEST)
            {
                msg.append("[FINEST ]");
            }
            else if (lvl == Level.FINER)
            {
                msg.append("[FINER  ]");
            }
            else if (lvl == Level.FINE)
            {
                msg.append("[FINE   ]");
            }
            else if (lvl == Level.CONFIG)
            {
                msg.append("[CONFIG ]");
            }
            else if (lvl == Level.INFO)
            {
                msg.append("[INFO   ]");
            }
            else if (lvl == Level.WARNING)
            {
                msg.append("[WARNING]");
            }
            else if (lvl == Level.SEVERE)
            {
                msg.append("[SEVERE ]");
            }
            else
            {
                msg.append("[" + lvl.getLocalizedName() + "]");
            }

            if (record.getLoggerName() != null)
            {
                msg.append("(" + record.getLoggerName() + ") ");
            }

            msg.append(record.getMessage());
            msg.append(LINE_SEPARATOR);
            Throwable t = record.getThrown();

            if (t != null)
            {
                StringWriter thrDump = new StringWriter();
                t.printStackTrace(new PrintWriter(thrDump));
                msg.append(thrDump.toString());
            }

            return msg.toString();
        }
    }
}
