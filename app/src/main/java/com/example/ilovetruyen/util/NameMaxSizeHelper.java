package com.example.ilovetruyen.util;

public class NameMaxSizeHelper {
    public  static String truncateName(String name,int maxLength){
        if(name == null || name.length()< maxLength){
            return name;
        }
        return name.substring(0,maxLength)+"...";
    }
    public  static String truncateName(String name){
        int maxLength = 40;
        if(name == null || name.length()< maxLength){
            return name;
        }
        return name.substring(0,maxLength)+"...";
    }
}
