package com.POM.MatNam.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMessage {
    private MessageType type;
    private String content;
    private String sender;
}
