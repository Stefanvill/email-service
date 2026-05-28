package se.iths.stefan.emailservice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Order {
    private LocalDateTime orderDate;
    private String customerName;
    private List<OrderItem> orderItems;
    private double totalPrice;
}
