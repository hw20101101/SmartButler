package com.example.hw.smartbutler.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.hw.smartbutler.R;

/**
 * Created by HW on 12/07/2017.
 * 自定义的进度条弹窗
 */

public class ProgressDialog extends Dialog {

    //模板
    public ProgressDialog(@NonNull Context context, int layout, int style) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, layout, style, Gravity.CENTER);
    }

    //属性
    public ProgressDialog(@NonNull Context context, int width, int height, int layout, int style, int gravity, int animations) {
        super(context, style);

        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(animations);
    }

    //实例
    public ProgressDialog(@NonNull Context context, int width, int height, int layout, int style, int gravity) {
        this(context, width, height, layout, style, gravity, R.style.pop_anim_style);

    }

}
