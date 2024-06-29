package com.example.ilovetruyen.effect;

import android.animation.ObjectAnimator;
import android.view.View;
/*
* class này define hiệu ứng blinking, áp dụng cho các hot lable*/

public class BlinkingEffect {
    public static void applyBlinkingEffect(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        animator.setDuration(2000); // thời gian nhấp nháy
        animator.setRepeatCount(ObjectAnimator.INFINITE); // số lần lặp lại
        animator.setRepeatMode(ObjectAnimator.REVERSE); // chế độ lặp lại
        animator.start();
    }
}
