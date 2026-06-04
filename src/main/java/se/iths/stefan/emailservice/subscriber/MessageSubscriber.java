package se.iths.stefan.emailservice.subscriber;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import se.iths.stefan.emailservice.config.RabbitConfig;
import se.iths.stefan.emailservice.model.Order;
import se.iths.stefan.springmessenger.messaging.EmailSender;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class MessageSubscriber {

    private static final Logger log = LoggerFactory.getLogger(MessageSubscriber.class);

    private final ObjectMapper objectMapper;
    private final EmailSender sender;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void subscribe(String message) {
        try {
            Order order = objectMapper.readValue(message, Order.class);

            log.info("Received message: {}", order);

            String mail = "Hejsan " + order.getCustomerName()
                    + ", tusen tack för din order. "
                    + "Du har beställt " + order.getItems()
                    + ". Totalpris är: " + order.getTotalPrice();

            log.info(mail);
            sender.send(mail, order.getCustomerName());
        } catch (Exception e) {
            log.error("Failed to parse message", e);
        }
    }
}