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
package net.awairo.mcmod.tooltipplus;

import static net.awairo.mcmod.common.SettingsHelper.*;

import java.awt.Color;
import java.io.File;
import java.util.Properties;

import net.awairo.mcmod.common.ColorUtils;
import net.awairo.mcmod.common.Env;
import net.awairo.mcmod.common.Logger;
import net.awairo.mcmod.common.SettingsHelper;

/**
 * Tooltip Plus Settings
 * 
 * @author alalwww
 * 
 */
public class TooltipPlusSettings
{
    private static final String SETTINGS_FILENAME = "TooltipPlus.cfg";
    private static final String CONFIG_COMMENT = String
            .format("Please read the readme.txt if you want to edit.%n");

    private static final String ILLEGAL_SETTINGS = "Illegal setting value. key=%s, value=%s, newValue=%s, rule=%s";

    private final File configFile;
    private final Properties properties;

    protected final Logger log;
    public final Env env;

    protected boolean enabled = true;
    protected Position position = Position.TOP_RIGHT;

    protected int updateDurationMilliSec = 100;
    protected long updateDuration;

    protected int hOffset = 2;
    protected int vOffset = 2;

    protected boolean enableArrowCountTip = true;
    protected boolean enableDurabilityTip = false;
    protected boolean enableEnchantmentTip = true;
    protected boolean enableIdTip = false;

    protected Color color = ColorUtils.parseColorRGB("#FFFFFF");

    protected boolean forceUpdate;
    protected volatile boolean settingsChanged;

    // -----------------------------------------------------

    /**
     * Constructor.
     */
    public TooltipPlusSettings()
    {
        this.log = TooltipPlus.log;
        env = TooltipPlus.env;
        properties = new Properties();
        configFile = new File(SettingsHelper.getConfigDir(), SETTINGS_FILENAME);
        initialize();
        save();
    }

    // -----------------------------------------------------

    protected void initialize()
    {
        SettingsHelper.load(properties, configFile);
        setEnabled(getValue(properties, ENABLED, enabled));
        setPosition(getValue(properties, POSITION, position.intValue));
        setUpdateDuration(getValue(properties, UPDATE_DURATION, updateDurationMilliSec));
        setHorizontalOffset(getValue(properties, H_OFFSET, hOffset));
        setVerticalOffset(getValue(properties, V_OFFSET, vOffset));
        setEnableArrowCountTip(getValue(properties, SHOW_ARROW, enableArrowCountTip));
        setEnableDurabilityTip(getValue(properties, SHOW_DURABILITY, enableDurabilityTip));
        setEnableEnchantmentTip(getValue(properties, SHOW_ENCHANTMENT, enableEnchantmentTip));
        setEnableIdTip(getValue(properties, SHOW_ID, enableIdTip));
        setColor(ColorUtils.parseColorRGB(getValue(properties, COLOR, ColorUtils.toString(color))));
    }

    protected void save()
    {
        if (!settingsChanged)
        {
            return;
        }

        settingsChanged = false;
        properties.setProperty(ENABLED, Boolean.toString(getEnabled()));
        properties.setProperty(POSITION, Integer.toString(getPosition().intValue));
        properties.setProperty(UPDATE_DURATION, Integer.toString(updateDurationMilliSec));
        properties.setProperty(H_OFFSET, Integer.toString(getHorizontalOffset()));
        properties.setProperty(V_OFFSET, Integer.toString(getVerticalOffset()));
        properties.setProperty(SHOW_ARROW, Boolean.toString(showArrowCount()));
        properties.setProperty(SHOW_DURABILITY, Boolean.toString(showDurability()));
        properties.setProperty(SHOW_ENCHANTMENT, Boolean.toString(showEnchantment()));
        properties.setProperty(SHOW_ID, Boolean.toString(showID()));
        properties.setProperty(COLOR, ColorUtils.toString(getColor()));
        SettingsHelper.store(properties, configFile, CONFIG_COMMENT);
        log.info("settings saved.");

        if (env.isDebugEnabled())
        {
            log.info(toString());
        }
    }

    // -----------------------------------------------------

    public static final String ENABLED = "tooltipplus.enabled";

    public boolean getEnabled()
    {
        return enabled;
    }

    protected void setEnabled(boolean enabled)
    {
        if (this.enabled != enabled)
        {
            this.enabled = enabled;
            forceUpdate = true;
            settingsChanged = true;
        }
    }

    // -----------------------------------------------------

    public static final String POSITION = "tooltipplus.position";

    public Position getPosition()
    {
        return position;
    }

    protected void setPosition(int positionIntValue)
    {
        if (position == null || position.intValue != positionIntValue)
        {
            position = Position.parse(positionIntValue);

            if (position == null)
            {
                log.warning("illegal setting value. value=%d", positionIntValue);
                position = Position.TOP_RIGHT;
            }

            forceUpdate = true;
            settingsChanged = true;
        }
    }

    // -----------------------------------------------------

    public static final String UPDATE_DURATION = "tooltipplus.update_duration";
    public static final int UPDATE_DURATION_MIN = 10;
    public static final int UPDATE_DURATION_MAX = 10000;

    public long getUpdateDuration()
    {
        return updateDuration;
    }

    protected void setUpdateDuration(int durationMilliSec)
    {
        if (durationMilliSec < UPDATE_DURATION_MIN || durationMilliSec > UPDATE_DURATION_MAX)
        {
            log.warning(ILLEGAL_SETTINGS, UPDATE_DURATION, durationMilliSec, 100, "10-10000");
            settingsChanged = true;
            durationMilliSec = 100;
        }

        if (updateDurationMilliSec != durationMilliSec)
        {
            updateDurationMilliSec = durationMilliSec;
            updateDuration = (updateDurationMilliSec) * 1000000L;
            forceUpdate = true;
            settingsChanged = true;
        }
    }

    // -----------------------------------------------------

    public static final String H_OFFSET = "tooltipplus.offset.horizontal";
    public static final int H_OFFSET_MIN = 0;
    public static final int H_OFFSET_MAX = 50;

    public int getHorizontalOffset()
    {
        return hOffset;
    }

    protected void setHorizontalOffset(int h)
    {
        if (h < H_OFFSET_MIN || h > H_OFFSET_MAX)
        {
            log.warning(ILLEGAL_SETTINGS, UPDATE_DURATION, h, 2, "0-50");
            settingsChanged = true;
            h = 2;
        }

        if (hOffset != h)
        {
            hOffset = h;
            forceUpdate = true;
            settingsChanged = true;
        }
    }

    // -----------------------------------------------------

    public static final String V_OFFSET = "tooltipplus.offset.vertical";
    public static final int V_OFFSET_MIN = 0;
    public static final int V_OFFSET_MAX = 50;

    public int getVerticalOffset()
    {
        return vOffset;
    }

    protected void setVerticalOffset(int v)
    {
        if (v < V_OFFSET_MIN || v > V_OFFSET_MAX)
        {
            log.warning(ILLEGAL_SETTINGS, UPDATE_DURATION, v, 2, "0-50");
            settingsChanged = true;
            v = 2;
        }

        if (vOffset != v)
        {
            vOffset = v;
            forceUpdate = true;
            settingsChanged = true;
        }
    }

    // -----------------------------------------------------

    public static final String SHOW_ARROW = "tooltipplus.show.arrow_count";

    public boolean showArrowCount()
    {
        return enableArrowCountTip;
    }

    protected void setEnableArrowCountTip(boolean enableArrowCountTip)
    {
        if (this.enableArrowCountTip != enableArrowCountTip)
        {
            this.enableArrowCountTip = enableArrowCountTip;
            forceUpdate = true;
            settingsChanged = true;
        }
    }

    // -----------------------------------------------------

    public static final String SHOW_DURABILITY = "tooltipplus.show.durability";

    public boolean showDurability()
    {
        return enableDurabilityTip;
    }

    protected void setEnableDurabilityTip(boolean enableDurabilityTip)
    {
        if (this.enableDurabilityTip != enableDurabilityTip)
        {
            this.enableDurabilityTip = enableDurabilityTip;
            forceUpdate = true;
            settingsChanged = true;
        }
    }

    // -----------------------------------------------------

    public static final String SHOW_ENCHANTMENT = "tooltipplus.show.enchantment";

    public boolean showEnchantment()
    {
        return enableEnchantmentTip;
    }

    protected void setEnableEnchantmentTip(boolean enableEnchantmentTip)
    {
        if (this.enableEnchantmentTip != enableEnchantmentTip)
        {
            this.enableEnchantmentTip = enableEnchantmentTip;
            forceUpdate = true;
            settingsChanged = true;
        }
    }

    // -----------------------------------------------------

    public static final String SHOW_ID = "tooltipplus.show.id";

    public boolean showID()
    {
        return enableIdTip;
    }

    protected void setEnableIdTip(boolean enableIdTip)
    {
        if (this.enableIdTip != enableIdTip)
        {
            this.enableIdTip = enableIdTip;
            forceUpdate = true;
            settingsChanged = true;
        }
    }

    // -----------------------------------------------------

    public static final String COLOR = "tooltipplus.rgb.tipcolor";

    protected void setColor(Color color)
    {
        this.color = color;
        forceUpdate = true;
        settingsChanged = true;
    }

    public Color getColor()
    {
        return color;
    }

    // -----------------------------------------------------

    public boolean isForceUpdate()
    {
        if (forceUpdate)
        {
            forceUpdate = false;
            return true;
        }

        return false;
    }

    // -----------------------------------------------------

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("settings: ");
        sb.append(ENABLED).append("=").append(getEnabled()).append(", ");
        sb.append(POSITION).append("=").append(getPosition()).append(", ");
        sb.append(updateDuration).append("=").append(getUpdateDuration()).append(", ");
        sb.append(H_OFFSET).append("=").append(getHorizontalOffset()).append(", ");
        sb.append(V_OFFSET).append("=").append(getVerticalOffset()).append(", ");
        sb.append(SHOW_ARROW).append("=").append(showArrowCount()).append(", ");
        sb.append(SHOW_DURABILITY).append("=").append(showDurability()).append(", ");
        sb.append(SHOW_ENCHANTMENT).append("=").append(showEnchantment()).append(", ");
        sb.append(SHOW_ID).append("=").append(showID()).append(", ");
        sb.append(COLOR).append("=").append(ColorUtils.toString(getColor())).append(", ");
        return sb.toString();
    }
}
