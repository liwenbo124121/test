package org.osc.scan.server.business.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : xyih
 * @date : 2021/5/10
 */
@Mapper
public interface LangToolDao {

    /**
     * 根据 langId toolId 判断当前是否是默认工具
     * 
     * @param langId
     * @param toolId
     * @return
     */
    Integer getLangToolDefault(@Param("langId") Long langId, @Param("toolId") Integer toolId);

    String getLangNameById(@Param("langId") Long langId);
}
