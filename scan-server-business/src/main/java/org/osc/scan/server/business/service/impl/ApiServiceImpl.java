package org.osc.scan.server.business.service.impl;

import org.osc.scan.server.business.dao.TaskDao;
import org.osc.scan.server.business.pojo.bo.UpdateTaskBo;
import org.osc.scan.server.business.pojo.dto.TaskDto;
import org.osc.scan.server.business.service.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : xyih
 * @date : 2021/5/6
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public String getParserNodeByTaskId(Integer taskId) {
        return taskDao.getParserNodeByTaskId(taskId);
    }

    @Override
    public void updateParentTask(UpdateTaskBo updateTaskBo) {
        try {
            TaskDto taskDto = new TaskDto();
            BeanUtils.copyProperties(updateTaskBo, taskDto);
            taskDao.updateByPrimaryKeySelective(taskDto);
        } catch (Exception ex) {
            throw new RuntimeException("更新[task]异常!", ex);
        }
    }
}
