package net.awairo.minecraft.tooltipplus;

import java.awt.Color;

import net.awairo.minecraft.tooltipplus.common.ColorHelper;
import net.minecraft.src.FontStatesGetter;
import net.minecraft.src.GuiApiFontHelper;
import net.minecraft.src.GuiApiHelper;
import net.minecraft.src.GuiModScreen;
import net.minecraft.src.ModAction;
import net.minecraft.src.SettingInt;
import net.minecraft.src.WidgetSimplewindow;
import net.minecraft.src.WidgetSinglecolumn;
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
        r.set(ColorHelper.toIntColor(colorSelector.getColor().getR()));
        g.set(ColorHelper.toIntColor(colorSelector.getColor().getG()));
        b.set(ColorHelper.toIntColor(colorSelector.getColor().getB()));
    }

    Color getColor()
    {
        return new Color(r.get(), g.get(), b.get());
    }

    void updateLabelColors()
    {
        colorFontHelper.setColor(FontStatesGetter.nomal, colorSelector.getColor());
    }

    void reset()
    {
        byte br = ColorHelper.toByteColor(r.get());
        byte bg = ColorHelper.toByteColor(g.get());
        byte bb = ColorHelper.toByteColor(b.get());
        colorSelector.setColor(new de.matthiasmann.twl.Color(br, bg, bb, (byte) 0xff));
    }
}
