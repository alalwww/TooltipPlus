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
