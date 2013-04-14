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

import net.minecraft.client.Minecraft;

/**
 * tool tip renderer.
 * 
 * @author alalwww
 */
class TooltipRenderer
{
    private final TooltipPlusSettings settings;

    TooltipRenderer(TooltipPlusSettings settings)
    {
        this.settings = settings;
    }

    void renderTips(Minecraft game, TooltipPool tips)
    {
        int rgb = settings.getColor().getRGB();

        for (Tooltip t : tips)
        {
            game.fontRenderer.drawStringWithShadow(t.tooltip, t.point.x, t.point.y, rgb);
        }
    }
}
