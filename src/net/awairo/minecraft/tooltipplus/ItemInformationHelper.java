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

import java.text.MessageFormat;
import java.util.List;

import net.awairo.minecraft.tooltipplus.common.Log;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * ItemInformationHelper.
 * 
 * @author alalwww
 */
class ItemInformationHelper
{
    String stackCountTip = "{0}x ";
    String allStackCountTip = " [{0}]";
    String durabilityTip = "{0}/{1}";
    String enchantmentTip = "[{0}]";
    String enchantmentSeparator = " / ";
    String idTip = "ID: {0}";
    String idAndMetadataTip = "{0}:{1}";

    private final TooltipPlusSettings settings;

    ItemInformationHelper(TooltipPlusSettings settings)
    {
        this.settings = settings;
    }

    /**
     * アイテム名とかのツールチップ取得.
     * 
     * @param game
     *            Minecraft
     * @param itemStack
     *            アイテムスタック
     * @return ツールチップ文字列
     */
    String getItemTip(ItemStack itemStack)
    {
        String prefix = getItemTipPrefix(itemStack);
        String suffix = getItemTipSuffix(itemStack);
        String itemName = itemStack.getItem().getItemDisplayName(itemStack);
        return prefix + itemName + suffix;
    }

    /**
     * アイテムにエンチャントが施されている場合 true.
     * 
     * @param itemStack
     *            アイテムスタック
     * @return エンチャントが施されている場合 true.
     */
    boolean hasEnchantment(ItemStack itemStack)
    {
        if (itemStack.getItem().isItemTool(itemStack))
        {
            return itemStack.isItemEnchanted();
        }

        return false;
    }

    /**
     * アイテムが耐久度を持っている場合 true.
     * 
     * @param itemStack
     *            アイテムスタック
     * @return 耐久度を持っている場合 true
     */
    boolean hasDurability(ItemStack itemStack)
    {
        // TODO: 拡張ブロックIDを考慮
        return itemStack.itemID > 255 && itemStack.isItemDamaged();
    }

    /**
     * エンチャント情報のツールチップ取得.
     * 
     * @param itemStack
     *            アイテムスタック
     * @return ツールチップ文字列
     */
    String getEnchantmentTip(ItemStack itemStack)
    {
        List<String> nameAndInfos = (List<String>) itemStack.getTooltip(Minecraft.getMinecraft().thePlayer, false);
        int size = nameAndInfos.size();

        if (size <= 1)
        {
            return null;
        }

        String first = nameAndInfos.get(1); // 0 is itemName

        if (size == 2)
        {
            return MessageFormat.format(enchantmentTip, first);
        }

        StringBuilder sb = new StringBuilder(first);

        for (int i = 2; i < size; i++)
        {
            sb.append(enchantmentSeparator).append(nameAndInfos.get(i));
        }

        return MessageFormat.format(enchantmentTip, sb.toString());
    }

    /**
     * 耐久度情報のツールチップ取得.
     * 
     * @param itemStack
     *            アイテムスタック
     * @return ツールチップ文字列
     */
    String getItemDurabilityTip(ItemStack itemStack)
    {
        int maxDurability = itemStack.getItem().getMaxDamage() + 1;
        int nowDurability = maxDurability - itemStack.getItemDamage();
        return MessageFormat.format(durabilityTip, nowDurability, maxDurability);
    }

    /**
     * アイテムID(ブロックID)のツールチップ取得.
     * 
     * @param itemStack
     *            アイテムスタック
     * @return アイテムID:ダメージ または アイテムID 文字列
     */
    String getIdTip(ItemStack itemStack)
    {
        if (itemStack.getItem().getHasSubtypes()) // getHas-じゃなく、そのままhasSubtypes() でいいんじゃね(w
        {
            return MessageFormat.format(idAndMetadataTip, itemStack.itemID, itemStack.getItemDamage());
        }

        return MessageFormat.format(idTip, itemStack.itemID);
    }

    /**
     * アイテム名の前につける情報取得. スタック数とか.
     */
    private String getItemTipPrefix(ItemStack itemStack)
    {
        String ret;

        if (settings.showArrowCount() && isBow(itemStack))
        {
            ret = "";
        }
        else if (itemStack.stackSize > 1)
        {
            ret = MessageFormat.format(stackCountTip, itemStack.stackSize);
        }
        else
        {
            ret = "";
        }

        if (ret.length() > 0)
        {
            Log.finest(ret);
        }

        return ret;
    }

    /**
     * アイテム名の後ろにつける情報取得. 総スタック数とか矢の数.
     */
    private String getItemTipSuffix(ItemStack itemStack)
    {
        String ret;

        if (settings.showArrowCount() && isBow(itemStack))
        {
            ret = MessageFormat.format(allStackCountTip, getArrowCount());
        }
        else
        {
            if (!hasDurability(itemStack))
            {
                int allItemCount = countItemFromMainInventory(itemStack.getItem(), itemStack.getItemDamage());

                if (itemStack.stackSize != allItemCount)
                {
                    ret = MessageFormat.format(allStackCountTip, allItemCount);
                }
                else
                {
                    ret = "";
                }
            }
            else
            {
                ret = "";
            }
        }

        if (ret.length() > 0)
        {
            Log.finest(ret);
        }

        return ret;
    }

    /**
     * アイテムが弓の場合 true.
     */
    private boolean isBow(ItemStack itemStack)
    {
        return itemStack.getItem().shiftedIndex == Item.bow.shiftedIndex;
    }

    /**
     * 矢の数取得.
     */
    private int getArrowCount()
    {
        return countItemFromMainInventory(Item.arrow, 0);
    }

    /**
     * アイテムの数取得.
     */
    private int countItemFromMainInventory(Item target, int itemDamage)
    {
        Minecraft game = Minecraft.getMinecraft();

        if (game.thePlayer == null || game.thePlayer.inventory == null)
        {
            return 0;
        }

        ItemStack[] mainInventory = game.thePlayer.inventory.mainInventory;
        int count = 0;

        for (ItemStack is : mainInventory)
        {
            if (is == null)
            {
                continue;
            }

            if (target.shiftedIndex != is.getItem().shiftedIndex)
            {
                continue;
            }

            if (itemDamage != is.getItemDamage())
            {
                continue;
            }

            count += is.stackSize;
        }

        return count;
    }
}
