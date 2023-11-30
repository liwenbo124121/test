package org.osc.scan.server.business.service;

/**
 * @author : zbz
 * @date : 2021/4/18
 */
public interface TaskService {

    /**
     * 更新task的规则集
     * 
     * @param taskId
     *            任务id
     * @param moduleId
     *            模块id
     */
    void updateRuleSet(Integer taskId, Integer moduleId);
}
