package blog.be.controller;

import blog.be.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(value = "http://localhost:3000",allowCredentials = "true")
@Controller
public class NotificationController {
    @MessageMapping("/news")
    @SendTo("/topic/messages")
    public Message send(final Message message) throws Exception {
        return message;
    }

    @MessageMapping("/notification")
    @SendTo("/topic/notification")
    public String postSendNotification(String message) throws Exception{
        return message;
    }
}
