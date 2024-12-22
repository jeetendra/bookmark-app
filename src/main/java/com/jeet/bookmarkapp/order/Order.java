package com.jeet.bookmarkapp.order;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Order {
    private UUID id;
    private String item;
}
