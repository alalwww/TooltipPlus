package net.awairo.minecraft.tooltipplus;

import java.awt.Point;

/**
 * tool tip model.
 *
 * @author alalwww
 */
class Tooltip
{
    final Point point;
    String tooltip;

    Tooltip()
    {
        point = new Point();
    }

    @Override
    public String toString()
    {
        return String.format("%s (x=%s, y=%s)", tooltip, point.x, point.y);
    }
}
