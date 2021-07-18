package com.example.amqpproducer.controller;

import com.example.amqpproducer.service.RabbitProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于发送消息
 */
@RestController
public class SendMsgController {

    @Autowired
    public RabbitProducerService service;

    @GetMapping("/fanout/{msg}")
    public String fanout(@PathVariable("msg") String msg) {

        service.fanout(msg);
        return "fanout - sent: " + msg;
    }

    @GetMapping("/direct/{msg}")
    public String direct(@PathVariable("msg") String msg,
                         @RequestParam(value = "id", required = false) Integer id) {

        service.direct(msg, id);
        return id + ".direct - sent: " + msg;
    }

    @GetMapping("/topic/{msg}")
    public String topic(@PathVariable("msg") String msg,
                        @RequestParam("key") String key) {
        service.topic(msg, key);
        return "topic - sent: " + msg;
    }
}
