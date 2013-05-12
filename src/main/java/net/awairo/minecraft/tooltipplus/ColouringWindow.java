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

import java.awt.Color;

import net.awairo.mcmod.common.ColorUtils;

import sharose.mods.guiapi.GuiApiFontHelper;
import sharose.mods.guiapi.GuiApiFontHelper.FontStates;
import sharose.mods.guiapi.GuiApiHelper;
import sharose.mods.guiapi.GuiModScreen;
import sharose.mods.guiapi.ModAction;
import sharose.mods.guiapi.SettingInt;
import sharose.mods.guiapi.WidgetSimplewindow;
import sharose.mods.guiapi.WidgetSinglecolumn;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.ColorSelector;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.ColorSpaceHSL;

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
        ModAction action = new ModAction(GuiModScreen.class, "show", new Class[] { Widget.class });
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
        byte br = ColorUtils.toByteColor(r.get());
        byte bg = ColorUtils.toByteColor(g.get());
        byte bb = ColorUtils.toByteColor(b.get());
        colorSelector.setColor(new de.matthiasmann.twl.Color(br, bg, bb, (byte) 0xff));
    }
}
