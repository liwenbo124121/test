package org.osc.scan.server.business.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.osc.scan.server.business.pojo.dto.ToolDto;
import org.springframework.stereotype.Repository;

/**
 * @author : xyih
 * @date : 2021/4/17
 */
@Repository
@Mapper
public interface ToolDao {

    /**
     * 根据langId查询模块中的工具
     * 
     * @param langId
     * @return
     */
    ArrayList<ToolDto> findToolsByLangId(@Param("langId") Long langId);
}