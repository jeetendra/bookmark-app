package com.jeet.bookmarkapp.util;

import com.jeet.bookmarkapp.order.message.OrderCreated;

import java.util.UUID;

public class OrderEventData {
    public static OrderCreated createOrder(UUID id, String item) {
        return OrderCreated.builder().id(id).item(item).build();
    }
}
