package com.jeet.bookmarkapp.order.message;

import lombok.*;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDispatched {
    private UUID orderId;
}
