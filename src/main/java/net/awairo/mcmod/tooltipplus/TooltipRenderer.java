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
        final int rgb = settings.getColor().getRGB();

        for (final Tooltip t : tips)
        {
            game.fontRenderer.drawStringWithShadow(t.tooltip, t.point.x, t.point.y, rgb);
        }
    }
}
