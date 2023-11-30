package org.osc.scan.server.business.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : xyih
 * @date : 2021/4/17
 */
@Repository
@Mapper
public interface UserDao {

    /**
     * 主键查询用户名
     * 
     * @param userId
     * @return
     */
    String getNameById(Integer userId);
}
