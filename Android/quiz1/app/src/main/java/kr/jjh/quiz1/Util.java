package kr.jjh.quiz1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class Util {
    public static Drawable getDrawable(Context mContext, String name) {
        Resources res = mContext.getResources();
        int resourceId = res.getIdentifier(name, "drawable", mContext.getPackageName());
        return res.getDrawable(resourceId);
    }
}
