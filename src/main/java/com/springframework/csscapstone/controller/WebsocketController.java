package com.springframework.csscapstone.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Sharing")
@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class WebsocketController {
    private final SimpMessagingTemplate template;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody String textMessageDto) {
        template.convertAndSend("/topic/message", textMessageDto);
        return new ResponseEntity<>(OK);
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload String textMessageDto) {
        //receive message from client
    }

    @SendTo("/topic/message")
    public String broadcastMessage(@Payload String dto) {
        return dto;
    }
}
