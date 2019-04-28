package com.business.common.utils;

import java.util.UUID;

public class KeyGenerator {

    public static String getKey(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-","");
    }
}
