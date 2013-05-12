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
