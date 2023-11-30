package org.osc.scan.server.business.service;

import org.osc.scan.server.business.pojo.bo.UpdateTaskBo;

/**
 * @author : xyih
 * @date : 2021/5/6
 */
public interface ApiService {

    /**
     * get parser node from task by task id
     * 
     * @param taskId
     * @return
     */
    String getParserNodeByTaskId(Integer taskId);

    /**
     * update parent task
     * 
     * @param updateTaskBo
     */
    void updateParentTask(UpdateTaskBo updateTaskBo);
}
