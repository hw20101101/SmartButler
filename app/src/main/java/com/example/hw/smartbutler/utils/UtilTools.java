package com.example.hw.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by HW on 09/07/2017.
 */

public class UtilTools {

    //设置字体
    public static void setFont(Context mContext, TextView textView){
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(font);
    }

    //保存图片
    public static void saveImageToShareUtils(Context mContext, ImageView imageView){
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        //1.将 bitmap 压缩成 字节数组输出流
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);

        //2.将 字节数组输出流 转成 String
        byte[] bytes = stream.toByteArray();
        String imgStr = new String(Base64.encodeToString(bytes, Base64.DEFAULT));

        //3.将 String 保存到 ShareUtils
        ShareUtils.putString(mContext, StaticClass.USER_PFOFIRE_IMAGE, imgStr);
    }

    //获取图片
    public static Bitmap getImageFromShareUtils(Context mContext){
        //1.从 ShareUtils 中获取 String
        String imgStr = ShareUtils.getString(mContext, StaticClass.USER_PFOFIRE_IMAGE, "");

        if (imgStr.equals("")) {
            return null;
        }

        //2.将 String 转成 字节数组输出流
        byte[] bytes = Base64.decode(imgStr, Base64.DEFAULT);
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

        //3.将 字节数组输出流 转成 图片
        return BitmapFactory.decodeStream(stream);
    }
}
