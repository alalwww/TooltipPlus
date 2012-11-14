package net.awairo.tooltipplus;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickProxy implements ITickHandler
{
    private final Minecraft game = Minecraft.getMinecraft();

    private final EnumSet<TickType> tickSet = EnumSet.of(TickType.RENDER);

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        // nothing
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        if (game.theWorld == null)
        {
            return;
        }

        if (game.mcProfiler.profilingEnabled)
        {
            game.mcProfiler.startSection("AwA mods");
            game.mcProfiler.startSection("Tooltip Plus");
            Loader.mod.update();
            game.mcProfiler.endSection();
            game.mcProfiler.endSection();
        }
        else
        {
            Loader.mod.update();
        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return tickSet;
    }

    @Override
    public String getLabel()
    {
        return "TooltipPlusTicker";
    }
}
