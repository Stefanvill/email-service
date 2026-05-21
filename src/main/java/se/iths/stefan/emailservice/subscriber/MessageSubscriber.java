package se.iths.stefan.emailservice.subscriber;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import se.iths.stefan.emailservice.config.RabbitConfig;
import se.iths.stefan.emailservice.model.Message;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MessageSubscriber {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void subscribe(Message message, LocalDateTime orderDate, String customerName
            , List<Object> orderItems, int quantity, double totalPrice) {
        System.out.println("Recieved message: ");
    }
}

