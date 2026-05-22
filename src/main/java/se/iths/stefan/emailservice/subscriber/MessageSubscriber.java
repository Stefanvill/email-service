package se.iths.stefan.emailservice.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import se.iths.stefan.emailservice.config.RabbitConfig;
import se.iths.stefan.springmessenger.messaging.EmailSender;
import se.iths.stefan.springmessenger.model.Message;

@Component
@RequiredArgsConstructor
public class MessageSubscriber {
    //
    private final EmailSender sender;


    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void subscribe(Message message) {
        System.out.println("Recieved message: ");
        sender.send(message);
    }
}

