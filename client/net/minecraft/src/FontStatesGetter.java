package net.minecraft.src;

import net.minecraft.src.GuiApiFontHelper.FontStates;

/**
 * GuiApiFontHelper#FontStates を取得するためのクラス.
 *
 * <p>
 * GuiApiFontHelper#FontStates のアクセス修飾子がデフォルトなので、net.minecraft.src
 * 以外のパッケージから参照できないため。
 * </p>
 *
 * @author alalwww
 */
public class FontStatesGetter
{
    public static FontStates disabled = FontStates.disabled;
    public static FontStates error = FontStates.error;
    public static FontStates hover = FontStates.hover;
    public static FontStates nomal = FontStates.normal;
    public static FontStates textSelection = FontStates.textSelection;
    public static FontStates warning = FontStates.warning;

    private FontStatesGetter()
    {
    }
}
