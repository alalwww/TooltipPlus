package net.awairo.tooltipplus;

import net.minecraft.src.Item;

import com.google.common.base.Ticker;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.TickRegistry;

/**
 * TooltipPlus loader.
 *
 * @author alalwww
 */
@Mod(modid = "TooltipPlus", name = "Tooltip Plus", version = "2.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class TooltipPlusLoader
{
    @Instance("TooltipPlus")
    public static TooltipPlusLoader loader;

    @SidedProxy(clientSide = "net.awairo.tooltipplus.TooltipPlusTicker")
    public static TooltipPlusTicker ticker;

    TooltipPlus mod;

    @Mod.PreInit
    public void preInitializeHandler(FMLPreInitializationEvent event)
    {
        Version.setVersion(event.getModMetadata().modId, event.getVersionProperties());
        System.out.println(Version.getVersion());
    }

    @Mod.Init
    public void initializeHandler(FMLInitializationEvent event)
    {
        mod = new TooltipPlus();
        TickRegistry.registerTickHandler(ticker, Side.CLIENT);
    }
}
