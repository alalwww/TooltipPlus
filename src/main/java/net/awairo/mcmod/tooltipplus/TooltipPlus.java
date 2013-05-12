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
package net.awairo.mcmod.tooltipplus;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import net.awairo.mcmod.common.Env;
import net.awairo.mcmod.common.IAwAMod;
import net.awairo.mcmod.common.Logger;
import net.awairo.mcmod.common.ReflectionHelper;

/**
 * TooltipPlus loader.
 * 
 * @author alalwww
 */
@Mod(modid = Metadata.MOD_ID, name = Metadata.MOD_NAME, modExclusionList = "+net.awairo.minecraft.common")
public class TooltipPlus implements IAwAMod
{
    private static final String GUI_API_CLASSNAME = "sharose.mods.guiapi.GuiAPI";

    static Env env;
    static Logger log;

    public TooltipPlus()
    {
        env = new Env(this);
        log = Logger.getLogger(env);
    }

    @Init
    public void initializeHandler(FMLInitializationEvent event)
    {
        TooltipPlusSettings settings;

        if (ReflectionHelper.findClass(GUI_API_CLASSNAME))
            settings = new TooltipPlusSettingsForGuiAPI();
        else
            settings = new TooltipPlusSettings();

        TickRegistry.registerTickHandler(new TooltipUpdater(settings), Side.CLIENT);
    }

    @Override
    public Env getEnv()
    {
        return env;
    }
}
