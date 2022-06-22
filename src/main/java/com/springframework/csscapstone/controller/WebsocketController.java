package com.springframework.csscapstone.controller;

//@Tag(name = "Sharing")
//@RequestMapping("user")
//@RestController
//@RequiredArgsConstructor
//public class WebsocketController {
//    private final SimpMessagingTemplate template;
//
//    @PostMapping("/send")
//    public ResponseEntity<?> sendMessage(@RequestBody String textMessageDto) {
//        template.convertAndSend("/topic/message", textMessageDto);
//        return new ResponseEntity<>(OK);
//    }
//
//    @MessageMapping("/sendMessage")
//    public void receiveMessage(@Payload String textMessageDto) {
//        receive message from client
//    }
//
//    @SendTo("/topic/message")
//    public String broadcastMessage(@Payload String dto) {
//        return dto;
//    }
//}
//