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
