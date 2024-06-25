package com.example.ilovetruyen.util;

import com.example.ilovetruyen.R;

import java.util.HashMap;

public class GradientHelper {
    public static final HashMap<Integer, Integer> colorMap = new HashMap<>();

    static {
        colorMap.put(1, R.drawable.gradient_1);
        colorMap.put(2, R.drawable.gradient_2);
        colorMap.put(3, R.drawable.gradient_3);
        colorMap.put(4, R.drawable.gradient_4);
        colorMap.put(5, R.drawable.gradient_5);
        colorMap.put(6, R.drawable.gradient_6);
        colorMap.put(7, R.drawable.gradient_7);
        colorMap.put(8, R.drawable.gradient_8);
        colorMap.put(9, R.drawable.gradient_9);
        colorMap.put(10, R.drawable.gradient_10);
    }

    public static Integer getColor(Integer id) {
        if (!colorMap.containsKey(id)) return colorMap.get(1);
        return colorMap.get(id);
    }
}
