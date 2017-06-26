package com.leptonmaps.wirelinefeasibility.enums;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public enum LayerType {
    POINT("point"),
    POLYGON("polygon"),
    POLYLINE("line"),
    CIRCLE("circle");

    private String value;

    private LayerType(String type) {
        value = type;
    }

    public String getValue() {
        return value;
    }

    public static LayerType getEnumByString(String code){
        for(LayerType e : values()){
            if(code.equalsIgnoreCase(e.value))
                return e;
        }
        return null;
    }
}
