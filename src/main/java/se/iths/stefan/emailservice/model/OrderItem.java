package se.iths.stefan.emailservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItem {
    private long id;
    private String name;
    private Double price;
    private int quantity;
}


