package org.osc.scan.server.business.util;


import java.util.HashMap;

public class Constint {

    public static final  HashMap<String, String> SCAN_FILE_LANGUAGE = new HashMap<>();

    static {
        SCAN_FILE_LANGUAGE.put(".cpp","cpp");
        SCAN_FILE_LANGUAGE.put(".c","cpp");
        SCAN_FILE_LANGUAGE.put(".h","cpp");
        SCAN_FILE_LANGUAGE.put(".hh","cpp");
        SCAN_FILE_LANGUAGE.put(".H","cpp");
        SCAN_FILE_LANGUAGE.put(".hp","cpp");
        SCAN_FILE_LANGUAGE.put(".hxx","cpp");
        SCAN_FILE_LANGUAGE.put(".hpp","cpp");
        SCAN_FILE_LANGUAGE.put(".HPP","cpp");
        SCAN_FILE_LANGUAGE.put(".h++","cpp");
        SCAN_FILE_LANGUAGE.put(".cc","cpp");
        SCAN_FILE_LANGUAGE.put(".cxx","cpp");
        SCAN_FILE_LANGUAGE.put(".cp","cpp");
        SCAN_FILE_LANGUAGE.put(".CPP","cpp");
        SCAN_FILE_LANGUAGE.put(".c++","cpp");
        SCAN_FILE_LANGUAGE.put(".C","cpp");
        SCAN_FILE_LANGUAGE.put(".java","java");
        SCAN_FILE_LANGUAGE.put(".php","php");
        SCAN_FILE_LANGUAGE.put(".py","python");
        SCAN_FILE_LANGUAGE.put(".js","js");
        SCAN_FILE_LANGUAGE.put(".es","js");
        SCAN_FILE_LANGUAGE.put("es6","js");
        SCAN_FILE_LANGUAGE.put(".go","go");
        SCAN_FILE_LANGUAGE.put(".m","oc");
        SCAN_FILE_LANGUAGE.put(".mm","oc");
    }
}
