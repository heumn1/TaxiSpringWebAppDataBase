package ru.heumn.taxi;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private String type;
    private String content;
    private String sender;
}