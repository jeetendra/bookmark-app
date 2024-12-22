package com.jeet.bookmarkapp.order.service;

import com.jeet.bookmarkapp.order.message.OrderCreated;
import com.jeet.bookmarkapp.order.message.OrderDispatched;
import org.apache.kafka.common.protocol.types.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderService orderService;
    private KafkaTemplate<String, Object> kafkaTemplate;

    @BeforeEach
    public void setup() {
        kafkaTemplate = mock(KafkaTemplate.class);

        orderService = new OrderService(kafkaTemplate);
    }

    @Test
    public void process_Success() throws Exception {

        Mockito.when(kafkaTemplate.send(Mockito.anyString(), any())).thenReturn(mock(CompletableFuture.class));
        OrderCreated orderCreated = OrderCreated.builder().id(UUID.randomUUID()).item(UUID.randomUUID().toString()).build();
        orderService.process(orderCreated);
        Mockito.verify(kafkaTemplate, Mockito.times(1)).send(Mockito.eq("order.dispatched"), any(OrderDispatched.class));
    }

//    @Test
//    public void process_ThrowsException() throws Exception {
//
//        doThrow(ExecutionException.class).when(kafkaTemplate).send(Mockito.anyString(), any());
//
//        OrderCreated orderCreated = OrderCreated.builder().id(UUID.randomUUID()).item(UUID.randomUUID().toString()).build();
//
//        Exception exception = assertThrows(Exception.class, () -> orderService.process(orderCreated));
//        verify(kafkaTemplate, Mockito.times(1)).send(Mockito.eq("order.dispatched"), any(OrderDispatched.class));
//        assertInstanceOf(ExecutionException.class, exception);
//    }


}