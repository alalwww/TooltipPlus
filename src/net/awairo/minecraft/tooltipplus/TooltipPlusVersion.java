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

import java.util.Properties;

import net.awairo.minecraft.common.Version;

/**
 * Version class.
 * 
 * @author alalwww
 */
public final class TooltipPlusVersion extends Version
{
    public TooltipPlusVersion(String pref, Properties prop)
    {
        super(pref, prop);
    }

    public TooltipPlusVersion()
    {
        super();
    }
}
