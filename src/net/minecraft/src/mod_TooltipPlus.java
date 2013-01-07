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

import net.awairo.minecraft.tooltipplus.TooltipPlus;
import net.awairo.minecraft.tooltipplus.Version;
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

    private TooltipPlus mod;

    @Override
    public String getVersion()
    {
        Version.initializeFromModLoader();
        return Version.getVersionString();
    }

    @Override
    public void load()
    {
        if (FMLInstalled)
        {
            return;
        }

        mod = new TooltipPlus();
        ModLoader.setInGameHook(this, true, false);
    }

    @Override
    public boolean onTickInGame(float time, Minecraft minecraftInstance)
    {
        if (FMLInstalled)
        {
            return false;
        }

        mod.update();
        return true;
    }
}
