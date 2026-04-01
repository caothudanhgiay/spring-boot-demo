
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.DMUserRequest;
import com.example.demo.service.DMMessageProducer;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/messages")
public class DMMessageController {

    @Autowired
    private DMMessageProducer producer;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String text) {
        producer.sendMessage(text);
        return "Message đã được gửi vào RabbitMQ!";
    }

    @PostMapping("send-json")
    public String sendJson(@RequestBody DMUserRequest data) {
        producer.sendObject(data);
        return "JSON đã được gửi vào RabbitMQ!";
    }

}