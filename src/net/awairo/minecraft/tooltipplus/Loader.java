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

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * TooltipPlus loader.
 * 
 * @author alalwww
 */
@Mod(modid = "net.awairo.minecraft.tooltipplus", name = "Tooltip Plus")
public class Loader
{
    @SidedProxy(clientSide = "net.awairo.minecraft.tooltipplus.TickProxy")
    public static ITickHandler tickHandler;

    static TooltipPlus mod;

    @PreInit
    public void preInitializeHandler(FMLPreInitializationEvent event)
    {
        Version.setVersion(event.getModMetadata().modId, event.getVersionProperties());
        event.getModMetadata().version = Version.getVersionString();
        System.out.println(event.getModMetadata().name + " v" + Version.getVersionString());
    }

    @Init
    public void initializeHandler(FMLInitializationEvent event)
    {
        mod = new TooltipPlus();
        TickRegistry.registerTickHandler(tickHandler, Side.CLIENT);
    }
}
