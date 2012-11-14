package net.awairo.tooltipplus;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;

/**
 * TooltipPlus loader.
 *
 * @author alalwww
 */
@Mod(modid = "TooltipPlus", name = "Tooltip Plus")
public class Loader
{
    @SidedProxy(clientSide = "net.awairo.tooltipplus.TickProxy")
    public static ITickHandler tickHandler;

    static TooltipPlus mod;

    @PreInit
    public void preInitializeHandler(FMLPreInitializationEvent event)
    {
        Version.setVersion(event.getModMetadata().modId, event.getVersionProperties());
        event.getModMetadata().version = Version.getVersionString();
        System.out.println(Version.getVersionString());
    }

    @Init
    public void initializeHandler(FMLInitializationEvent event)
    {
        mod = new TooltipPlus();
        TickRegistry.registerTickHandler(tickHandler, Side.CLIENT);
    }
}
