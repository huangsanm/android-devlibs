package com.huashengmi.devlibs.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;

import com.huashengmi.devlibs.R;

/**
 * User: huangsanm@foxmail.com
 * Date: 2016-03-16
 * Time: 15:50
 */
public class SpannableUtils {
	/**
	 * 建立一串
	 * @param context
	 * @param strs
	 * @param color
	 * @return
     */

	public static SpannableString createOne(Context context, String prev, Object middel, String last){
		prev = (prev==null?"":prev);
		String mstr = (middel == null?"":middel.toString());
		last = (last == null)?"":last;
		return SpannableUtils.getSpannableStringExclusive(context, prev+mstr+last, prev.length(),
				prev.length()+mstr.length(), R.color.colorAccent);
	}
	/**
	 * 显示金额
	 * @param context
	 * @param amount 按分
	 * @return
     */
	public static SpannableString createOneForMoney(Context context, Integer amount){
		String str = (amount/100.0)+"";
		return createOne(context, "￥", str, "元");
	}

    /**
     * 文字样式处理
     *
     * @param text
     * @param start
     * @param end
     * @param color
     * @return
     */
    public SpannableString setSpannableText(Context context, String text, int start, int end, int color) {
        int textColor = context.getResources().getColor(color);
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(textColor), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return ss;
    }

    /**
     * 对文本的指定长度设置颜色，后续文本追加文本不受影响
     * @param context
     * @param text
     * @param start
     * @param end
     * @param color
     * @return
     */
    public static SpannableString getSpannableStringExclusive(Context context, String text, int start, int end, int color) {
        int textColor = context.getResources().getColor(color);
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(textColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
    
    /**
     * 对文本的指定长度设置样式，后续文本追加文本不受影响
     * @param context
     * @param text
     * @param start
     * @param end
     * @param style
     * @return
     */
    public static SpannableString getSpannableStringExclusiveByStyle(Context context, String text, int start, int end, int style) {
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new TextAppearanceSpan(context, style), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
    
    /**
     * 对文本的指定长度设置颜色，后续文本追加文本也使用相应的颜色
     * @param context
     * @param text
     * @param start
     * @param end
     * @param color
     * @return
     */
    public static SpannableString getSpannableStringInclusive(Context context, String text, int start, int end, int color) {
        int textColor = context.getResources().getColor(color);
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(textColor), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return ss;
    }
}
