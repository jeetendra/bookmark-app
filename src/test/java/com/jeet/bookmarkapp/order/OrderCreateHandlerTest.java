package com.jeet.bookmarkapp.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class OrderCreateHandlerTest {

    private OrderCreateHandler orderCreateHandler;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = Mockito.mock(OrderService.class);
        orderCreateHandler = new OrderCreateHandler(orderService);
    }

    @Test
    void listen() {
        orderCreateHandler.listen("message");
        Mockito.verify(orderService, Mockito.times(1));
    }
}