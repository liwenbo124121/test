package org.osc.scan.server.business.metadata;

/**
 * @author : zbz
 * @date : 2021/4/18
 */
public class TopicConstant {

    /**
     * 通知parser合并扫描
     */
    public final static String PARSER_START_MERGE_RESOLVE = "parserStartMergeResolve";

    /**
     * 触发server 缺陷/安全 扫描
     */
    public final static String SCAN_SERVER_SEND_TOPIC = "SCAN_SERVER_SEND_TOPIC";

    /**
     * 通知portal扫描
     */
    public final static String SCAN_TASK_CALLBACK_TOPIC = "SCAN_TASK_CALLBACK_TOPIC";

    /**
     * parser解析完成回调
     */
    public final static String PARSER_RESOLVE_COMPLETE = "parserResolveComplete";

    /**
     * task update topic
     */
    public final static String SCAN_TASK_UPDATE_TOPIC = "SCAN_TASK_UPDATE_TOPIC";

}
