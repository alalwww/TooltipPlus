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

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.FingerprintWarning;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import net.awairo.mcmod.common.Env;
import net.awairo.mcmod.common.IAwAMod;
import net.awairo.mcmod.common.handler.FMLEventHandlerLogic;
import net.awairo.mcmod.common.log.Logger;
import net.awairo.mcmod.common.util.ReflectionHelper;

/**
 * TooltipPlus loader.
 * 
 * @author alalwww
 */
@Mod(modid = Metadata.MOD_ID, certificateFingerprint = IAwAMod.FINGERPRINT)
public class TooltipPlus implements IAwAMod
{
    private static final String GUI_API_CLASSNAME = "sharose.mods.guiapi.GuiAPI";

    static Env env;
    static Logger log;

    /**
     * Constructor.
     */
    public TooltipPlus()
    {
        env = new Env(this);
        log = Logger.getLogger(env);
    }

    @Override
    public Env getEnv()
    {
        return env;
    }

    @Init
    protected void initializeHandler(FMLInitializationEvent event)
    {
        final TooltipPlusSettings settings = getSettings();
        TickRegistry.registerTickHandler(new TooltipUpdater(settings), Side.CLIENT);
    }

    @FingerprintWarning
    protected void fingerprintWarning(FMLFingerprintViolationEvent event)
    {
        FMLEventHandlerLogic.handleViolationEvent(this, event);
    }

    private TooltipPlusSettings getSettings()
    {
        if (ReflectionHelper.findClass(GUI_API_CLASSNAME))
            return new TooltipPlusSettingsForGuiAPI();

        return new TooltipPlusSettings();
    }
}
