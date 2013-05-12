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

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * Tooltip draw position.
 * 
 * @author alalwww
 * 
 */
public enum Position
{
    /**
     * top left position.
     */
    TOP_LEFT(0, "Top Left"),

    /**
     * top right position.
     */
    TOP_RIGHT(1, "Top Right"),

    /**
     * bottom left position.
     */
    BOTTOM_LEFT(2, "Bottom Left"),

    /**
     * bottom right position.
     */
    BOTTOM_RIGHT(3, "Bottom Right");

    public final int intValue;
    public final String label;

    private static String[] labels = null;

    private Position(int intValue, String label)
    {
        this.intValue = intValue;
        this.label = label;
    }

    public void computePoint(Tooltip tip, int w, int h, int hOffset, int vOffset, int line)
    {
        final Minecraft game = Minecraft.getMinecraft();
        final FontRenderer fr = game.fontRenderer;

        // compute point x
        switch (this)
        {
            case TOP_LEFT:
            case BOTTOM_LEFT:
                tip.point.x = hOffset;
                break;

            case TOP_RIGHT:
            case BOTTOM_RIGHT:
                tip.point.x = w - hOffset - fr.getStringWidth(tip.tooltip);
                break;

            default:
                tip.point.x = 0;
        }

        // compute point y
        switch (this)
        {
            case TOP_LEFT:
            case TOP_RIGHT:
                tip.point.y = vOffset + fr.FONT_HEIGHT * (line - 1);
                break;

            case BOTTOM_LEFT:
            case BOTTOM_RIGHT:
                tip.point.y = h - (vOffset + fr.FONT_HEIGHT) * line;
                break;

            default:
                tip.point.y = 0;
        }
    }

    /**
     * ラベル配列.
     * GuiAPI用.
     */
    public static String[] labels()
    {
        if (labels == null)
        {
            final Position[] values = Position.values();
            final String[] ret = new String[values.length];

            for (int i = 0; i < ret.length; i++)
            {
                ret[i] = values[i].label;
            }

            labels = ret;
        }

        return labels;
    }

    @Nullable
    public static Position parse(int intValue)
    {
        for (final Position p : values())
        {
            if (p.intValue == intValue)
            {
                return p;
            }
        }

        return null;
    }
}
