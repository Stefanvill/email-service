package se.iths.stefan.emailservice.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Message implements Serializable {
    private LocalDateTime orderDate;
    private String customerName;
    private List<Object> orderItems;
    private int quantity;
    private double totalPrice;
}
