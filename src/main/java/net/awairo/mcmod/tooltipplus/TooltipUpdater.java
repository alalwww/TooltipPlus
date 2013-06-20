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

import java.util.EnumSet;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;

import net.awairo.mcmod.common.log.Logger;

/**
 * TooltipUpdater.
 * 
 * @author alalwww
 */
public class TooltipUpdater implements ITickHandler
{
    private static final int ITEM_NONE_ID = -1;
    private static final int ITEM_NONE_DAMAGE = 0;
    private static final int ITEM_NONE_STACKSIZE = 0;

    private final Minecraft game;

    private final TooltipPlusSettings settings;
    private final ItemInformationHelper helper;
    private final TooltipRenderer renderer;
    private final TooltipPool tips;

    private long lastUpdateTime;
    private int lastHadItemId;
    private int lastHadItemDamage;
    private int lastHadItemStackSize;

    private final Logger log;

    /**
     * Constructor.
     */
    public TooltipUpdater(TooltipPlusSettings settings)
    {
        game = Minecraft.getMinecraft();

        this.settings = settings;
        log = Logger.getLogger(settings.env);

        helper = new ItemInformationHelper(settings);
        renderer = new TooltipRenderer(settings);
        tips = new TooltipPool();

        lastUpdateTime = System.nanoTime();

        log.info("initialize completed.");
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        // nothing
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        if (game.theWorld == null)
        {
            return;
        }

        if (game.mcProfiler.profilingEnabled)
        {
            game.mcProfiler.startSection("AwA mods");
            game.mcProfiler.startSection("Tooltip Plus");
            update();
            game.mcProfiler.endSection();
            game.mcProfiler.endSection();
        }
        else
        {
            update();
        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.RENDER);
    }

    @Override
    public String getLabel()
    {
        return "TooltipPlusTicker";
    }

    /**
     * update
     * 
     * @param renderPertialTicks
     */
    private void update()
    {
        if (!settings.getEnabled() || game.currentScreen != null)
        {
            return;
        }

        final long currentTime = System.nanoTime();
        final ItemStack currentItem = getCurrentHaving();

        if (isUpdatePhase(currentTime, currentItem))
        {
            lastUpdateTime = currentTime;
            saveCurrentItemInfo(currentItem);
            updateTips(currentItem);

            if (settings.env.isTraceEnabled())
            {
                for (final Tooltip t : tips)
                {
                    log.trace(t.toString());
                }
            }
        }

        renderer.renderTips(game, tips);
    }

    private ItemStack getCurrentHaving()
    {
        if (game.thePlayer == null || game.thePlayer.inventory == null)
        {
            return null;
        }

        return game.thePlayer.inventory.getCurrentItem();
    }

    private boolean isUpdatePhase(long time, ItemStack is)
    {
        if (settings.isForceUpdate())
        {
            return true;
        }

        if (is == null)
        {
            if (lastHadItemId != ITEM_NONE_ID)
            {
                return true;
            }

            if (lastHadItemDamage != ITEM_NONE_DAMAGE)
            {
                return true;
            }

            if (lastHadItemStackSize != ITEM_NONE_STACKSIZE)
            {
                return true;
            }
        }
        else
        {
            if (lastHadItemId != is.itemID)
            {
                return true;
            }

            if (lastHadItemDamage != is.getItemDamage())
            {
                return true;
            }

            if (lastHadItemStackSize != is.stackSize)
            {
                return true;
            }
        }

        if (time - lastUpdateTime > settings.getUpdateDuration())
        {
            return true;
        }

        return false;
    }

    private void saveCurrentItemInfo(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            lastHadItemId = itemStack.itemID;
            lastHadItemDamage = itemStack.getItemDamage();
            lastHadItemStackSize = itemStack.stackSize;
        }
        else
        {
            lastHadItemId = ITEM_NONE_ID;
            lastHadItemDamage = ITEM_NONE_DAMAGE;
            lastHadItemStackSize = ITEM_NONE_STACKSIZE;
        }
    }

    private void updateTips(ItemStack itemStack)
    {
        tips.reset();

        if (itemStack == null)
        {
            return;
        }

        final Position pos = settings.getPosition();
        final ScaledResolution sr = new ScaledResolution(game.gameSettings, game.displayWidth,
                game.displayHeight);
        final int w = sr.getScaledWidth();
        final int h = sr.getScaledHeight();
        final int hOffset = settings.getHorizontalOffset();
        final int vOffset = settings.getVerticalOffset();
        int line = 1;
        Tooltip tip = tips.getTooltip();
        tip.tooltip = helper.getItemTip(itemStack);
        pos.computePoint(tip, w, h, hOffset, vOffset, line++);

        // enchantment
        if (settings.showEnchantment() && helper.hasEnchantment(itemStack))
        {
            tip = tips.getTooltip();
            tip.tooltip = helper.getEnchantmentTip(itemStack);
            pos.computePoint(tip, w, h, hOffset, vOffset, line++);
        }

        // durability
        if (settings.showDurability() && helper.hasDurability(itemStack))
        {
            tip = tips.getTooltip();
            tip.tooltip = helper.getItemDurabilityTip(itemStack);
            pos.computePoint(tip, w, h, hOffset, vOffset, line++);
        }

        // id
        if (settings.showID())
        {
            tip = tips.getTooltip();
            tip.tooltip = helper.getIdTip(itemStack);
            pos.computePoint(tip, w, h, hOffset, vOffset, line++);
        }
    }
}
