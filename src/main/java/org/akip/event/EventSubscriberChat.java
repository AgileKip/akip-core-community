package org.akip.event;

import org.springframework.context.ApplicationEvent;

public class EventSubscriberChat extends ApplicationEvent {

    // private final ChatDTO chat;
    // passar no parametro ChatDTO chat
    public EventSubscriberChat(Object source) {
        super(source);
        // this.chat = chat;
    }
    //    public ChatDTO getChat() {
    //        return chat;
    //    }
}
