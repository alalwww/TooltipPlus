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

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * tool tip model instance cache.
 * 
 * @author alalwww
 */
class TooltipPool implements Iterable<Tooltip>
{
    private final List<Tooltip> tips = Lists.newArrayListWithExpectedSize(4);
    private Iterator<Tooltip> iterator;
    private int tipsCurrentIndex = 0;
    private int cursor = 0;

    TooltipPool()
    {
        iterator = new Iterator<Tooltip>()
        {
            @Override
            public boolean hasNext()
            {
                if (cursor < tipsCurrentIndex && tips.size() > cursor)
                {
                    return true;
                }

                cursor = 0;
                return false;
            }

            @Override
            public Tooltip next()
            {
                return tips.get(cursor++);
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }

    void reset()
    {
        tipsCurrentIndex = 0;
        cursor = 0;
    }

    Tooltip getTooltip()
    {
        if (tips.size() > tipsCurrentIndex)
        {
            return tips.get(tipsCurrentIndex++);
        }

        tips.add(new Tooltip());
        return getTooltip();
    }

    @Override
    public Iterator<Tooltip> iterator()
    {
        return iterator;
    }
}
