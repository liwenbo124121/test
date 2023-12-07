package org.osc.scan.server.client.fegin;

import org.osc.scan.server.client.request.TestRequest;
import org.osc.scan.server.client.response.TestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author bz.zhang
 * @date 2021/2/18 15:37
 */
@FeignClient(value = "server", contextId = "serverFeign")
public interface PortalFeign {

    /**
     * hello world
     *
     * @param testRequest
     *            入参
     * @return 出参
     */
    @PostMapping(value = "test")
    TestResponse test(@RequestBody TestRequest testRequest);

}
@FeignClient(value = "server", contextId = "serverFeign")
public interface PortalFeign {

    /**
     * hello world
     *
     * @param testRequest
     *            入参
     * @return 出参
     */
    @PostMapping(value = "test")
    TestResponse test(@RequestBody TestRequest testRequest);

}
@FeignClient(value = "server", contextId = "serverFeign")
public interface PortalFeign {

    /**
     * hello world
     *
     * @param testRequest
     *            入参
     * @return 出参
     */
    @PostMapping(value = "test")
    TestResponse test(@RequestBody TestRequest testRequest);

}
@FeignClient(value = "server", contextId = "serverFeign")
public interface PortalFeign {

    /**
     * hello world
     *
     * @param testRequest
     *            入参
     * @return 出参
     */
    @PostMapping(value = "test")
    TestResponse test(@RequestBody TestRequest testRequest);

}