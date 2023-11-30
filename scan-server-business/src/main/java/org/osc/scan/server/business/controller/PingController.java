package org.osc.scan.server.business.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : zbz
 * @date : 2021/6/9
 */
@RequestMapping("/ping")
@RestController
public class PingController {

    @RequestMapping
    public String ping() {
        return "pong";
    }

}
