package com.jeet.bookmarkapp.order.message;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderCreated {
    private UUID id;
    private String item;
}
