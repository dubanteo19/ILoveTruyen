package com.example.ilovetruyen.ui;

import com.example.ilovetruyen.model.Status;

import java.util.HashMap;

public class StatusHelper {
    public static final HashMap<Status,String> statusMap = new HashMap<>();
    static{
        statusMap.put(Status.FINISHED,"Hoàn thành");
        statusMap.put(Status.UPDATING,"Đang tiến hành");
        statusMap.put(Status.INACTIVE,"Không hoạt động");
    }
    public static String getStatusName (Status status){
        return statusMap.get(status);
    }
}
