package org.osc.scan.server.business.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.osc.scan.server.business.dao.RuleSetDao;
import org.osc.scan.server.business.dao.TaskDao;
import org.osc.scan.server.business.pojo.dto.RuleSetDto;
import org.osc.scan.server.business.pojo.dto.TaskDto;
import org.osc.scan.server.business.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : zbz
 * @date : 2021/4/18
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private RuleSetDao ruleSetDao;

    @Override
    @Transactional
    public void updateRuleSet(Integer taskId, Integer moduleId) {
        if (null == moduleId) {
            return;
        }
        // 1.根据moduleId查询规则集
        List<RuleSetDto> ruleSetDtos = ruleSetDao.getRuleSetsByModuleId(moduleId);
        // 2.更新task表字段[rule_set_ids,rule_set_names]
        if (ruleSetDtos.size() > 0) {
            Set<Object> ruleIds =
                ruleSetDtos.stream().collect(HashSet::new, (s, dto) -> s.add(dto.getRsId()), HashSet::addAll),
                ruleNames =
                    ruleSetDtos.stream().collect(HashSet::new, (s, dto) -> s.add(dto.getRsName()), HashSet::addAll);
            TaskDto taskDto = new TaskDto();
            taskDto.setTaskId(taskId);
            taskDto.setRuleSetIds(StringUtils.join(ruleIds, ","));
            taskDto.setRuleSetNames(StringUtils.join(ruleNames, ","));
            taskDao.updateTaskRules(taskDto);
        }
    }
}
