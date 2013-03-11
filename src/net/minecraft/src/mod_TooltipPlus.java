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
package net.minecraft.src;

import net.awairo.minecraft.tooltipplus.TooltipUpdater;
import net.awairo.minecraft.tooltipplus.TooltipPlusVersion;
import net.minecraft.client.Minecraft;

/**
 * Tooltip Plus loader.
 * 
 * @author alalwww
 */
public class mod_TooltipPlus extends BaseMod
{
    private static final boolean FMLInstalled;
    static
    {
        boolean isfml = false;
        for (Class<?> ifClazz : BaseMod.class.getInterfaces())
        {
            if (ifClazz.getSimpleName().equals("BaseModProxy"))
            {
                isfml = true;
                break;
            }
        }

        FMLInstalled = isfml;
    }

    private TooltipUpdater updater;

    TooltipPlusVersion version = new TooltipPlusVersion();

    @Override
    public String getVersion()
    {
        return version.toString();
    }

    @Override
    public void load()
    {
        if (FMLInstalled)
        {
            return;
        }

        updater = new TooltipUpdater();
        ModLoader.setInGameHook(this, true, false);
    }

    @Override
    public boolean onTickInGame(float time, Minecraft minecraftInstance)
    {
        if (FMLInstalled)
        {
            return false;
        }

        updater.update();
        return true;
    }
}
