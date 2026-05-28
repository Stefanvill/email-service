package se.iths.stefan.emailservice.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import se.iths.stefan.emailservice.config.RabbitConfig;
import se.iths.stefan.emailservice.model.Order;
import se.iths.stefan.springmessenger.messaging.EmailSender;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class MessageSubscriber {

    private final EmailSender sender;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void subscribe(String message) {
        try {
            Order order = objectMapper.readValue(message, Order.class);

            System.out.println("Received message: " + order);

            String mail = "Hejsan " + order.getCustomerName()
                    + ", tusen tack för din order. "
                    + "Du har beställt " + order.getOrderItems()
                    + ". Totalpris är: " + order.getTotalPrice();

            sender.send(mail, order.getCustomerName());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse message", e);
        }
    }
}