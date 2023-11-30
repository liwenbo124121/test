package org.osc.scan.server.business.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.osc.scan.server.business.pojo.dto.SubTaskDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : zbz
 * @date : 2021/4/16
 */
@Repository
@Mapper
public interface SubTaskDao {

    /**
     * 主键查询
     * 
     * @param subTaskId
     * @return
     */
    SubTaskDto find(@Param("subTaskId") Integer subTaskId);

    /**
     * 主键加锁查询
     *
     * @param subTaskId
     * @return
     */
    SubTaskDto finForUpdate(@Param("subTaskId") Integer subTaskId);

    /**
     * 更新subtask
     * 
     * @param subTaskDto
     */
    void updateSubTask(SubTaskDto subTaskDto);

    /**
     * 更新子任务状态
     * 
     * @param subTaskId
     *            主键id
     * @param status
     *            {@link org.osc.scan.server.business.metadata.ESubtaskStatus}
     */
    void updateStatusBySubTaskId(@Param("subTaskId") Integer subTaskId, @Param("status") Integer status);

    void updateStatusErrDescByTaskId(@Param("taskId") Integer taskId, @Param("errDesc") String errDesc,
        @Param("status") Integer status);

    List<SubTaskDto> selectByTaskId(@Param("taskId") Integer taskId);

}
