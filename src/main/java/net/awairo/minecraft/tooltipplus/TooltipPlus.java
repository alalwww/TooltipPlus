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

import java.util.EnumSet;

import net.awairo.minecraft.common.Logger;
import net.awairo.minecraft.common.Version;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * TooltipPlus loader.
 * 
 * @author alalwww
 */
@Mod(modid = Metadata.MOD_ID, name = Metadata.MOD_NAME)
public class TooltipPlus implements ITickHandler
{
    private final Logger log = Logger.getLogger(getClass());
    private final Minecraft game = Minecraft.getMinecraft();

    private TooltipUpdater updater;

    private Version version;

    @PreInit
    public void preInitializeHandler(FMLPreInitializationEvent event)
    {
        version = new Version(Metadata.MOD_ID, event.getVersionProperties());
        event.getModMetadata().version = version.toString();
        log.info(String.format("%s v%s", event.getModMetadata().name, version));
    }

    @Init
    public void initializeHandler(FMLInitializationEvent event)
    {
        updater = new TooltipUpdater();
        TickRegistry.registerTickHandler(this, Side.CLIENT);
    }

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
            updater.update();
            game.mcProfiler.endSection();
            game.mcProfiler.endSection();
        }
        else
        {
            updater.update();
        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.RENDER);
    }

    @Override
    public String getLabel()
    {
        return "TooltipPlusTicker";
    }

    public Version version()
    {
        return version;
    }
}
