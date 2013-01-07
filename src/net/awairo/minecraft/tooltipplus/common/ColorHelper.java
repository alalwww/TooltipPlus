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
package net.awairo.minecraft.tooltipplus.common;

import java.awt.Color;
import java.util.regex.Pattern;

/**
 * RGB形式またはRGBA形式の文字列を Color にパースします.
 * 
 * R、G、B、A は それぞれ 1または2桁の 0～F の16進数文字列です。
 * 
 * RGB形式の例) #RGB, 0xRGB, #RRGGBB, 0xRRGGBB, RGB, RRGGBB
 * 
 * RGBA形式の例) #ARGB, 0xARGB, #AARRGGBB, 0xAARRGGBB, ARGB, AARRGGBB
 * 
 * @author alalwww
 * @version 1.0.0
 */
public class ColorHelper
{
    private static final Pattern COLOR_PATTERN = Pattern.compile("^#?(([0-9a-fA-F]){3,4}|([0-9a-fA-F]{2}){3,4})$");
    private static final Pattern HEX_PATTERN = Pattern.compile("^[0-9a-fA-F]{1,2}$");

    /**
     * byte color to int color.
     * 
     * @param b
     *            byte color
     * @return int color
     */
    public static Integer toIntColor(byte b)
    {
        return Integer.valueOf((int) b & 0xFF);
    }

    /**
     * int color to byte color.
     * 
     * @param i
     *            int color
     * @return byte color
     */
    public static byte toByteColor(int i)
    {
        return (byte) ((i >>> 0) & 0xFF);
    }

    /**
     * to color.
     * 
     * @param colorString
     *            RGB or RGBA string
     * @return color or null
     */
    public static Color parseColor(String colorString)
    {
        if (colorString == null)
        {
            return null;
        }

        colorString = removeHeaderChar(colorString);
        int length = colorString.length();
        return parseColorInternal(colorString, (length == 4 || length == 8));
    }

    /**
     * to color.
     * 
     * @param rgbString
     *            RGB string
     * @return color or null
     */
    public static Color parseColorRGB(String rgbString)
    {
        return parseColorInternal(rgbString, false);
    }

    /**
     * to color.
     * 
     * @param rgbaString
     *            RGBA string
     * @return color or null
     */
    public static Color parseColorRGBA(String rgbaString)
    {
        return parseColorInternal(rgbaString, true);
    }

    private static String removeHeaderChar(String s)
    {
        if (s.startsWith("#"))
        {
            return s.substring(1, s.length());
        }

        if (s.startsWith("0x"))
        {
            return s.substring(2, s.length());
        }

        return s;
    }

    private static Color parseColorInternal(String colorString, boolean hasalpha)
    {
        if (colorString == null || !COLOR_PATTERN.matcher(colorString).matches())
        {
            return null;
        }

        colorString = removeHeaderChar(colorString);
        int loopCount = hasalpha ? 4 : 3;
        int length = colorString.length();
        int delta;

        if (length == loopCount)
        {
            delta = 1;
        }
        else if (length == loopCount * 2)
        {
            delta = 2;
        }
        else
        {
            return null;
        }

        int[] color = new int[loopCount];
        int cursor = 0;

        for (int i = 0; i < loopCount; i++)
        {
            int endIndex = cursor + delta;
            String s = colorString.substring(cursor, endIndex);
            cursor = endIndex;

            if (s.length() == 1)
            {
                s = s + s;
            }

            if (HEX_PATTERN.matcher(s).matches())
            {
                color[i] = Integer.parseInt(s, 16);
            }
            else
            {
                Log.warning("illegal color value. (%s) replace to FF", s);
                color[i] = 255;
            }
        }

        Color ret;

        if (hasalpha)
        {
            ret = new Color(color[1], color[2], color[3], color[0]);
        }
        else
        {
            ret = new Color(color[0], color[1], color[2]);
        }

        Log.finer("color parsed. (%s)", ret);
        return ret;
    }

    public static String toString(Color color)
    {
        return toString(color, false);
    }

    public static String toString(Color color, boolean hasalpha)
    {
        StringBuilder sb = new StringBuilder(hasalpha ? 8 : 6);

        if (hasalpha)
        {
            sb.append(Integer.toString(color.getAlpha(), 16).toUpperCase());
        }

        sb.append(Integer.toString(color.getRed(), 16).toUpperCase());
        sb.append(Integer.toString(color.getGreen(), 16).toUpperCase());
        sb.append(Integer.toString(color.getBlue(), 16).toUpperCase());
        return sb.toString();
    }
}
