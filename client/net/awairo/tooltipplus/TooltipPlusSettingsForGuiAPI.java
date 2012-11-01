package net.awairo.tooltipplus;

import java.awt.Color;

import net.awairo.tooltipplus.common.Log;
import net.minecraft.src.ModSettingScreen;
import net.minecraft.src.ModSettings;
import net.minecraft.src.SettingBoolean;
import net.minecraft.src.SettingInt;
import net.minecraft.src.SettingMulti;
import net.minecraft.src.WidgetClassicTwocolumn;
import net.minecraft.src.WidgetSimplewindow;

/**
 * TooltipPlus settings with GuiAPI.
 *
 * @author alalwww
 *
 */
public class TooltipPlusSettingsForGuiAPI extends TooltipPlusSettings
{
    private WidgetSimplewindow window;
    private SettingBoolean gaEnabled;
    private SettingMulti gaPosition;
    private SettingInt gaUpdateDuration;

    private SettingInt gaHorizontalOffset;
    private SettingInt gaVerticalOffset;

    private SettingBoolean gaShowArrowCount;
    private SettingBoolean gaShowDurability;
    private SettingBoolean gaShowEnchantment;
    private SettingBoolean gaShowID;

    private ColouringWindow colouringWindow;

    @Override
    public void initialize()
    {
        super.initialize();
        WidgetClassicTwocolumn widgetColumn = new WidgetClassicTwocolumn();
        window = new WidgetSimplewindow(widgetColumn, "Tooltip plus setting");
        ModSettingScreen mss = new ModSettingScreen(window, window.titleWidget.getText());
        mss.widgetColumn = widgetColumn;
        ModSettings ms = new ModSettings("TooltipPlus");
        gaEnabled = ms.addSetting(mss, "Enabled", ENABLED, true);
        gaEnabled.set(enabled);
        gaPosition = ms.addSetting(mss, "Position", POSITION, Position.TOP_RIGHT.intValue, Position.labels());
        gaPosition.set(position.intValue);
        gaUpdateDuration = ms.addSetting(mss, "Update duration", UPDATE_DURATION, 100, 10, 10000);
        gaUpdateDuration.set(updateDurationMilliSec);
        gaHorizontalOffset = ms.addSetting(mss, "Horizontal offset", H_OFFSET, 2, 0, 20);
        gaHorizontalOffset.set(hOffset);
        gaVerticalOffset = ms.addSetting(mss, "Vertical offset", V_OFFSET, 2, 0, 20);
        gaVerticalOffset.set(vOffset);
        gaShowArrowCount = ms.addSetting(mss, "Show arrow count", SHOW_ARROW, true);
        gaShowArrowCount.set(enableArrowCountTip);
        gaShowDurability = ms.addSetting(mss, "Show tool durability", SHOW_DURABILITY, false);
        gaShowDurability.set(enableDurabilityTip);
        gaShowEnchantment = ms.addSetting(mss, "Show enchantment", SHOW_ENCHANTMENT, true);
        gaShowEnchantment.set(enableEnchantmentTip);
        gaShowID = ms.addSetting(mss, "Show ID", SHOW_ID, false);
        gaShowID.set(enableIdTip);
        SettingInt r = ms.addSetting(mss, "Red", COLOR + "_Red", 255, 0, 255);
        SettingInt g = ms.addSetting(mss, "Green", COLOR + "_Green", 255, 0, 255);
        SettingInt b = ms.addSetting(mss, "Blue", COLOR + "_Blue", 255, 0, 255);
        r.set(color.getRed());
        g.set(color.getGreen());
        b.set(color.getBlue());
        colouringWindow = new ColouringWindow(r, g, b);
        mss.append(colouringWindow.openButton);
        window.backButton.addCallback(new WindowCloseHandler());
        Log.info("GuiAPI initialized.");
    }

    @Override
    public boolean getEnabled()
    {
        return gaEnabled.get();
    }

    @Override
    public int getHorizontalOffset()
    {
        return gaHorizontalOffset.get();
    }

    @Override
    public int getVerticalOffset()
    {
        return gaVerticalOffset.get();
    }

    @Override
    public boolean showArrowCount()
    {
        return gaShowArrowCount.get();
    }

    @Override
    public boolean showDurability()
    {
        return gaShowDurability.get();
    }

    @Override
    public boolean showEnchantment()
    {
        return gaShowEnchantment.get();
    }

    @Override
    public boolean showID()
    {
        return gaShowID.get();
    }

    private class WindowCloseHandler implements Runnable
    {
        @Override
        public void run()
        {
            setEnabled(getEnabled());
            setPosition(gaPosition.get());
            setUpdateDuration(gaUpdateDuration.get());
            setHorizontalOffset(getHorizontalOffset());
            setVerticalOffset(getVerticalOffset());
            setEnableArrowCountTip(showArrowCount());
            setEnableDurabilityTip(showDurability());
            setEnableEnchantmentTip(showEnchantment());
            setEnableIdTip(showID());

            if (isColorChanged())
            {
                setColor(colouringWindow.getColor());
            }

            save();
        }

        private boolean isColorChanged()
        {
            Color c = getColor();

            if (c.getRed() != colouringWindow.r.get())
            {
                return true;
            }

            if (c.getGreen() != colouringWindow.g.get())
            {
                return true;
            }

            if (c.getBlue() != colouringWindow.b.get())
            {
                return true;
            }

            return false;
        }
    }
}
