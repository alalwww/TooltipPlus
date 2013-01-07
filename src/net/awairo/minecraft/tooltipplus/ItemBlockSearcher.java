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

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.google.common.collect.Maps;

public class ItemBlockSearcher
{
    private Map<String, Integer> dictionary = Maps.newConcurrentMap();

    public <I extends Item> I getItemByName(String name)
    {
        String key = "item." + name;

        Integer index = dictionary.get(key);
        if (index != null)
        {
            return (I) Item.itemsList[index];
        }

        for (Item item : Item.itemsList)
        {
            if (item != null && item.getItemName().equals(key))
            {
                Integer value = item.shiftedIndex - 256;
                dictionary.put(key, value);
                return (I) item;
            }
        }

        return null;
    }

    public <B extends Block> B getBlockByName(String name)
    {
        String key = "tile." + name;

        Integer index = dictionary.get(key);
        if (index != null)
        {
            return (B) Block.blocksList[index];
        }

        for (Block block : Block.blocksList)
        {
            if (block != null && block.getBlockName().equals(key))
            {
                dictionary.put(key, block.blockID);
                return (B) block;
            }
        }

        return null;
    }

    public void clearDictionary()
    {
        dictionary.clear();
    }
}
