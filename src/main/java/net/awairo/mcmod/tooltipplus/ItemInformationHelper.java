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

import java.text.MessageFormat;
import java.util.List;

import cpw.mods.fml.common.Mod.Instance;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.awairo.mcmod.common.Logger;

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
    private final Logger log;

    @Instance(Metadata.MOD_ID)
    public TooltipPlus mod;

    ItemInformationHelper(TooltipPlusSettings settings)
    {
        this.settings = settings;
        log = settings.log;
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
        final String prefix = getItemTipPrefix(itemStack);
        final String suffix = getItemTipSuffix(itemStack);
        final String itemName = itemStack.getItem().getItemDisplayName(itemStack);
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
        @SuppressWarnings("unchecked")
        final List<String> nameAndInfos = itemStack.getTooltip(Minecraft.getMinecraft().thePlayer, false);
        final int size = nameAndInfos.size();

        if (size <= 1)
        {
            return null;
        }

        final String first = nameAndInfos.get(1); // 0 is itemName

        if (size == 2)
        {
            return MessageFormat.format(enchantmentTip, first);
        }

        final StringBuilder sb = new StringBuilder(first);

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
        final int maxDurability = itemStack.getItem().getMaxDamage() + 1;
        final int nowDurability = maxDurability - itemStack.getItemDamage();
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
            log.trace(ret);
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
                final int allItemCount = countItemFromMainInventory(itemStack.getItem(),
                        itemStack.getItemDamage());

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

        if (ret.length() > 0 && settings.env.isTraceEnabled())
        {
            log.trace(ret);
        }

        return ret;
    }

    /**
     * アイテムが弓の場合 true.
     */
    private boolean isBow(ItemStack itemStack)
    {
        return itemStack.getItem().itemID == Item.bow.itemID;
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
        final Minecraft game = Minecraft.getMinecraft();

        if (game.thePlayer == null || game.thePlayer.inventory == null)
        {
            return 0;
        }

        final ItemStack[] mainInventory = game.thePlayer.inventory.mainInventory;
        int count = 0;

        for (final ItemStack is : mainInventory)
        {
            if (is == null)
            {
                continue;
            }

            if (target.itemID != is.getItem().itemID)
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
