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

import net.awairo.minecraft.common.Logger;
import net.awairo.minecraft.common.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;

/**
 * Tooltip Plus.
 * 
 * @author alalwww
 */
public class TooltipUpdater
{
    private final Logger log = Logger.getLogger(TooltipPlus.class);

    private static final int ITEM_NONE_ID = -1;
    private static final int ITEM_NONE_DAMAGE = 0;
    private static final int ITEM_NONE_STACKSIZE = 0;

    private static final String GUI_API_CLASSNAME = "sharose.mods.guiapi.GuiAPI";

    private final Minecraft game;

    private final TooltipPlusSettings settings;
    private final ItemInformationHelper helper;
    private final TooltipRenderer renderer;
    private final TooltipPool tips;

    private long lastUpdateTime;
    private int lastHadItemId;
    private int lastHadItemDamage;
    private int lastHadItemStackSize;

    /**
     * Constructor.
     */
    public TooltipUpdater()
    {
        game = Minecraft.getMinecraft();

        if (ReflectionHelper.findClass(GUI_API_CLASSNAME))
            settings = new TooltipPlusSettingsForGuiAPI();
        else
            settings = new TooltipPlusSettings();

        helper = new ItemInformationHelper(settings);
        renderer = new TooltipRenderer(settings);
        tips = new TooltipPool();
        lastUpdateTime = System.nanoTime();

        log.info("initialize completed.");
    }

    /**
     * update
     * 
     * @param renderPertialTicks
     */
    public void update()
    {
        if (!settings.getEnabled() || game.currentScreen != null)
        {
            return;
        }

        long currentTime = System.nanoTime();
        ItemStack currentItem = getCurrentHaving();

        if (isUpdatePhase(currentTime, currentItem))
        {
            lastUpdateTime = currentTime;
            saveCurrentItemInfo(currentItem);
            updateTips(currentItem);

            if (settings.trace)
            {
                for (Tooltip t : tips)
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

        Position pos = settings.getPosition();
        ScaledResolution sr = new ScaledResolution(game.gameSettings, game.displayWidth, game.displayHeight);
        int w = sr.getScaledWidth();
        int h = sr.getScaledHeight();
        int hOffset = settings.getHorizontalOffset();
        int vOffset = settings.getVerticalOffset();
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
