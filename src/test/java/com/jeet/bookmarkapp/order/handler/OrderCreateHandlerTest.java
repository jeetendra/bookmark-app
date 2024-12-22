package com.jeet.bookmarkapp.order.handler;

import com.jeet.bookmarkapp.order.message.OrderCreated;
import com.jeet.bookmarkapp.order.service.OrderService;
import com.jeet.bookmarkapp.util.OrderEventData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderCreateHandlerTest {

    private OrderHandler orderCreateHandler;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        orderCreateHandler = new OrderHandler(orderService);
    }

    @Test
    void listen_Success() {
        orderCreateHandler.listen(OrderEventData.createOrder(UUID.randomUUID(), UUID.randomUUID().toString()));
        verify(orderService, times(1));
    }

    @Test
    void listen_ThrowsException() throws Exception {
        OrderCreated orderCreated = OrderEventData.createOrder(UUID.randomUUID(), UUID.randomUUID().toString());
        doThrow(new RuntimeException("Service failure")).when(orderService).process(orderCreated);

        Assertions.assertThrows(Exception.class, () -> orderCreateHandler.listen(orderCreated));
    }
}