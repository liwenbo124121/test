package org.osc.scan.server.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.osc.scan.server.business.pojo.dto.RuleSetDto;
import org.springframework.stereotype.Repository;

/**
 * @author : xyih
 * @date : 2021/4/17
 */
@Repository
@Mapper
public interface RuleSetDao {

    /**
     * 根据模块Id查询相关规则集
     * 
     * @param moduleId
     * @return
     */
    List<RuleSetDto> getRuleSetsByModuleId(Integer moduleId);
}
