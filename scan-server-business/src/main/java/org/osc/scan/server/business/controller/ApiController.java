package org.osc.scan.server.business.controller;

import java.util.HashMap;
import java.util.Map;

import org.osc.common.util.JsonUtil;
import org.osc.scan.server.business.pojo.bo.UpdateTaskBo;
import org.osc.scan.server.business.pojo.vo.UpdateParentTaskVo;
import org.osc.scan.server.business.service.ApiService;
import org.osc.scan.server.business.util.ResponseInfoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : xyih
 * @date : 2021/5/6
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/getParserNode")
    public Map getParserNode(@RequestParam("task_id") Integer taskId) {
        log.info("get parserNode by task_id={}", taskId);
        Map result = new HashMap();
        if (null == taskId) {
            log.error("task_id is none.");
            result.put("succ", "err");
            return result;
        }

        result.put("parserNode", apiService.getParserNodeByTaskId(taskId));
        log.info("result={}", JsonUtil.obj2String(result));
        return result;
    }

    @PostMapping("/parent/task/update")
    public ResponseInfoUtil.ResponseInfo<Map> updateParentTask(@RequestBody UpdateParentTaskVo updateParentTaskVo) {
        log.info("update parent task. params={}", JsonUtil.obj2String(updateParentTaskVo));
        Integer taskId = updateParentTaskVo.getTaskId();
        if (null == taskId) {
            log.error("task_id is none.Nothing updated.");
            return ResponseInfoUtil.fail("task_id is none.Nothing updated.");
        }

        // params
        UpdateTaskBo updateTaskBo = new UpdateTaskBo();
        BeanUtils.copyProperties(updateParentTaskVo, updateTaskBo);
        // update
        Map result = new HashMap();
        try {
            apiService.updateParentTask(updateTaskBo);
            result.put("update", "succ");
            result.put("task_id", taskId);
        } catch (Exception e) {
            result.put("update", "fail");
            result.put("task_id", taskId);
        }
        log.info("result={}", JsonUtil.obj2String(result));
        return ResponseInfoUtil.success(result);
    }

}
