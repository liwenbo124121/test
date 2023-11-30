package org.osc.scan.server.business.dao;

import org.apache.ibatis.annotations.Mapper;
import org.osc.scan.server.business.pojo.dto.ModuleDto;
import org.springframework.stereotype.Repository;

/**
 * @author : zbz
 * @date : 2021/4/16
 */
@Repository
@Mapper
public interface ModuleDao {

    /**
     * 查询module信息
     * 
     * @param moduleId
     * @return
     */
    ModuleDto find(Integer moduleId);
}
