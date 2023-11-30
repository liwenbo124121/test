package org.osc.scan.server.business.metadata;

import org.apache.commons.lang3.StringUtils;

public enum StartType {
    I("I", "increase"), F("F","full");

    public String key;
    public String name;

    StartType(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public static String getName(Integer key) {
        StartType[] values = StartType.values();
        for (StartType type : values) {
            if (type.key.equals(key)) {
                return type.name;
            }
        }
        return StringUtils.EMPTY;
    }

    public static String getKey(String name) {
        StartType[] values = StartType.values();
        for (StartType type : values) {
            if (type.name.equals(name)) {
                return type.key;
            }
        }
        return "";
    }
}
