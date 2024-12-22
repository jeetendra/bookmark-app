package com.jeet.bookmarkapp.util;

import com.jeet.bookmarkapp.order.Order;

import java.util.UUID;

public class OrderEventData {
    public static Order createOrder(UUID id, String item ) {
        return Order.builder().id(id).item(item).build();
    }
}
