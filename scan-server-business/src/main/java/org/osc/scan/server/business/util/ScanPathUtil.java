package org.osc.scan.server.business.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


@Slf4j
public class ScanPathUtil {
    /**
     * cpp js oc 语言处理
     * @param langSet
     * @return
     */
    public static Set<String> getPatterLangNames(Set<String> langSet) {
        Set<String> patterSet = new HashSet<>();
        langSet.stream().forEach(lang -> {
            String langLower = lang.toLowerCase().trim();
            if ("cpp".equals(langLower)) {
                langLower = "c/c++";
            } else if ("js".equals(langLower)) {
                langLower = "javascript";
            } else if ("oc".equals(langLower)) {
                langLower = "object-c";
            }
            patterSet.add(langLower);
        });
        return patterSet;
    }

    public static String getLangFromFileName(String fileName) {
        if(StringUtils.isEmpty(fileName)){
            return null;
        }else{
            String suffix = getFileNameSuffix(fileName);
            if(!StringUtils.isEmpty(suffix)){
                return Constint.SCAN_FILE_LANGUAGE.get(suffix);
            }
        }
        return null;
    }

    public static String getFileNameSuffix(String fileName){
        if(!StringUtils.isEmpty(fileName)){
            Integer index = fileName.indexOf(".");
            if(index == -1){
                return null;
            }else{
                return fileName.substring(index,fileName.length());
            }
        }
        return null;
    }

    public static HashMap<String, String> getDiffFileDict(Set<String> langSet, String[] diffFiles) {
        HashMap<String,String>  diffFileDict = new HashMap<>();
        for(String lang : langSet){
            List<String> langDiffFiles = getCodeFileForEagle(diffFiles, lang);
            diffFileDict.put(lang, StringUtils.join(langDiffFiles, ","));
        }
        return diffFileDict;
    }

    public static List<String> getCodeFileForEagle(String[] diffFiles,String lang){
        List<String> langFile = new ArrayList<>();
        for (int i = 0; i < diffFiles.length; i++) {
            String language = getLangFromFileName(diffFiles[i].toString());
            if(lang.equals("c/c++") && "cpp".equals(language)){
                langFile.add(diffFiles[i].toString());
                continue;
            }
            if(lang.equals("javascript") && "js".equals(language)){
                langFile.add(diffFiles[i].toString());
                continue;
            }
            if(!StringUtils.isEmpty(language) && lang.equals(language)){
                langFile.add(diffFiles[i].toString());
            }
        }
        return langFile;
    }
}
