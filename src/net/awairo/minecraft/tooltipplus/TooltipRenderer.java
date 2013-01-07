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
