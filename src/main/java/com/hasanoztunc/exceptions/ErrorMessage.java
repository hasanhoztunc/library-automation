package com.hasanoztunc.exceptions;

import com.hasanoztunc.enumerations.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private MessageType messageType;
    private String staticMessage;

    public String prepareErrorMessage() {
        StringBuilder builder = new StringBuilder();

        builder.append(messageType.getMessage());

        if (staticMessage != null) {
            builder.append(" : " + staticMessage);
        }

        return builder.toString();
    }
}
