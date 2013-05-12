/*
 * TooltipPlus
 * (c) 2012 alalwww
 * https://github.com/alalwww
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License 1.0, or MMPL.
 * Please check the contents of the license located in http://www.mod-buildcraft.com/MMPL-1.0.txt
 *
 * この MOD は、Minecraft Mod Public License (MMPL) 1.0 の条件のもとに配布されています。
 * ライセンスの内容は次のサイトを確認してください。 http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package net.awairo.mcmod.tooltipplus;

import java.awt.Color;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.ColorSelector;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.ColorSpaceHSL;
import sharose.mods.guiapi.GuiApiFontHelper;
import sharose.mods.guiapi.GuiApiFontHelper.FontStates;
import sharose.mods.guiapi.GuiApiHelper;
import sharose.mods.guiapi.GuiModScreen;
import sharose.mods.guiapi.ModAction;
import sharose.mods.guiapi.SettingInt;
import sharose.mods.guiapi.WidgetSimplewindow;
import sharose.mods.guiapi.WidgetSinglecolumn;

import net.awairo.mcmod.common.ColorUtils;

/**
 * ColouringWindow.
 * 
 * @author alalwww
 */
class ColouringWindow
{
    final SettingInt r;
    final SettingInt g;
    final SettingInt b;

    final WidgetSinglecolumn widgetSingleColumn;
    final GuiApiFontHelper colorFontHelper;
    final Label colorLabel;
    final ColorSelector colorSelector;
    final WidgetSimplewindow screenColoringWindow;
    final Button openButton;
    final Button closeButton;

    /**
     * Constructor.
     */
    ColouringWindow(SettingInt red, SettingInt green, SettingInt blue)
    {
        this.r = red;
        this.g = green;
        this.b = blue;
        widgetSingleColumn = new WidgetSinglecolumn();
        widgetSingleColumn.childDefaultWidth = 300;
        colorLabel = new Label("TOOL TIP COLOR SETTING");
        widgetSingleColumn.add(colorLabel);
        colorFontHelper = new GuiApiFontHelper();
        colorFontHelper.setFont(colorLabel);
        colorSelector = new ColorSelector(new ColorSpaceHSL());
        colorSelector.setShowAlphaAdjuster(false);
        colorSelector.setShowHexEditField(false);
        colorSelector.setShowNativeAdjuster(false);
        colorSelector.setShowPreview(false);
        colorSelector.setShowRGBAdjuster(true);
        colorSelector.addCallback(new ModAction(this, "updateLabelColors"));
        widgetSingleColumn.add(colorSelector);
        widgetSingleColumn.heightOverrideExceptions.put(colorSelector, 0);
        screenColoringWindow = new WidgetSimplewindow(widgetSingleColumn, "Tooltip text color setting");
        closeButton = screenColoringWindow.backButton;
        closeButton.addCallback(new ModAction(this, "updateColor"));
        final ModAction action = new ModAction(GuiModScreen.class, "show", new Class[] { Widget.class });
        action.setDefaultArguments(screenColoringWindow);
        openButton = GuiApiHelper.makeButton("-> Open Color Serector", action, true);
        openButton.addCallback(new ModAction(this, "reset"));
    }

    void updateColor()
    {
        r.set(ColorUtils.toIntColor(colorSelector.getColor().getR()));
        g.set(ColorUtils.toIntColor(colorSelector.getColor().getG()));
        b.set(ColorUtils.toIntColor(colorSelector.getColor().getB()));
    }

    Color getColor()
    {
        return new Color(r.get(), g.get(), b.get());
    }

    void updateLabelColors()
    {
        colorFontHelper.setColor(FontStates.normal, colorSelector.getColor());
    }

    void reset()
    {
        final byte br = ColorUtils.toByteColor(r.get());
        final byte bg = ColorUtils.toByteColor(g.get());
        final byte bb = ColorUtils.toByteColor(b.get());
        colorSelector.setColor(new de.matthiasmann.twl.Color(br, bg, bb, (byte) 0xff));
    }
}
