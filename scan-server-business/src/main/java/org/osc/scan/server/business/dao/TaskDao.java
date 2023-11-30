package org.osc.scan.server.business.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.osc.scan.server.business.pojo.dto.TaskDto;
import org.springframework.stereotype.Repository;

/**
 * @author : zbz
 * @date : 2021/4/16
 */
@Repository
@Mapper
public interface TaskDao {
    /**
     * 主键查询
     *
     * @param taskId
     * @return
     */
    TaskDto find(Integer taskId);

    /**
     * 查询子任务未全部成功合计
     *
     * @param taskId
     * @return
     */
    int unSuccTaskCount(@Param("taskId") Integer taskId, @Param("scanTypeId") Integer scanTypeId);

    /**
     * 更新任务规则集
     *
     * @param taskDto
     */
    void updateTaskRules(TaskDto taskDto);

    /**
     * 根据task_id查询parse_node
     * 
     * @param taskId
     * @return
     */
    String getParserNodeByTaskId(@Param("taskId") Integer taskId);

    /**
     * 根据主键更新
     * 
     * @param taskDto
     * @return
     */
    int updateByPrimaryKeySelective(TaskDto taskDto);
}
