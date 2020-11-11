package com.POM.MatNam.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.POM.MatNam.chat.model.RequestMessage;
import com.POM.MatNam.chat.model.ResponseMessage;

@Controller
public class ChatController {
    
    private int userCount;
    public ChatController() {
        userCount = 0;
    }
    
    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ResponseMessage sendMessage(@Payload RequestMessage chatMessage) {
        ResponseMessage res = new ResponseMessage(chatMessage.getType(), chatMessage.getContent(), chatMessage.getSender(),userCount);
        return res;
    }

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public ResponseMessage addUser(@Payload RequestMessage chatMessage,@RequestHeader(value = "nickname", required = true) String nickname){
        userCount++;
        ResponseMessage res = new ResponseMessage(chatMessage.getType(), chatMessage.getContent(), chatMessage.getSender(), userCount);
        return res;
    }
    
    @MessageMapping("/leaveUser")
    @SendTo("/topic/public")
    public ResponseMessage leaveUser(@Payload RequestMessage chatMessage) {
        userCount--;
        ResponseMessage res = new ResponseMessage(chatMessage.getType(), chatMessage.getContent(), chatMessage.getSender(), userCount);
        return res;
    }
}

